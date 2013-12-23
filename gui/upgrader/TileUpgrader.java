package flandrelaevatain.gui.upgrader;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.nucleareal.UtilNumber;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import flandrelaevatain.FPR2;
import flandrelaevatain.block.ILightValueProvider;
import flandrelaevatain.gui.upgrader.packet.UpgraderPacketHandler;
import flandrelaevatain.item.IPowerCore;

public class TileUpgrader extends TileEntity implements ISidedInventory, ILightValueProvider
{
	private ItemStack core;
	private ItemStack result;
	private ItemStack[][] contents;

	public TileUpgrader()
	{
		contents = new ItemStack[5][6];
	}

	//private static final long stack = 64;
	//private static final long lc = 27*2*stack;
	private static final long[] upgradeTable;
	private static final long[] burnTileTable;

	static
	{
		//ceil (x^x)^1.75
		upgradeTable = new long[]{ 1, 12, 320, 16384, 1306134, 148111278 };

		//2^​((6-​x)*​2)
		burnTileTable = new long[6];
		for(int i = 1; i <= burnTileTable.length; i++)
		{
			burnTileTable[i-1] = UtilNumber.getPower(2, (6-i)*2);
		}
	}

	//Upgrade中のコア
	private int coreIndex;

	//消費したアイテム数 -> Progress(Total) / upgradeTable[coreIndex]
	private long progressTotal;
	//現在の燃焼タイム -> Progress(Current) / burnTileTable[coreIndex]
	private long progressCurrent;

	//処理中かどうか
	private boolean isProcessing;

	public int ProgressBarCurrent;
	public int ProgressBarTotal;



	public void updateEntity()
	{
		super.updateEntity();
		if(isProcessing)
		{
			if(getUpgradeIndex() != coreIndex)
			{
				isProcessing = false;
				return;
			}

			if(worldObj.isRemote)
			{
				ProgressBarCurrent = (int)(64F*getCurrentProgress());
				ProgressBarTotal = (int)(64F*getTotalProgress());
			}

			boolean consumed = false;

			int i = 0;
			while(canConsumeItems())
			{
				consumed = true;
				progressTotal++;

				if(progressTotal >= upgradeTable[coreIndex])
				{
					onUpgradeFinished();
					isProcessing = false;
					break;
				}
			}

			if(!consumed && progressCurrent == 0 && progressTotal == 0)
			{
				isProcessing = false;
				return;
			}

			if(!consumed && ++progressCurrent >= burnTileTable[coreIndex])
			{
				progressCurrent = burnTileTable[coreIndex];

				if(progressTotal > 0)
				{
					progressTotal--;
				}
				else
				{
					progressCurrent = 0;
					progressTotal = 0;
					coreIndex = -1;
					isProcessing = false;
				}
			}
			else
			if(consumed)
			{
				progressCurrent = 0;
			}
		}
		else
		{
			coreIndex = getUpgradeIndex();
			if(coreIndex != -1)
			{
				progressCurrent = 0;
				progressTotal = 0;
				isProcessing = true;
			}
		}
	}

	private void onUpgradeFinished()
	{
		ItemStack upgrade = null;
		switch(coreIndex)
		{
			case 0: upgrade = FPR2.Abandoned; break;
			case 1: upgrade = FPR2.Bane; break;
			case 2: upgrade = FPR2.Collapse; break;
			case 3: upgrade = FPR2.Destroy; break;
			case 4: upgrade = FPR2.Elimination; break;
			case 5: upgrade = FPR2.Flandre; break;
		}
		if(upgrade != null)
		{
			setInventorySlotContents(getArraySize()+1, upgrade.copy());
			setInventorySlotContents(getArraySize(), null);
		}
	}

	private int getUpgradeIndex()
	{
		ItemStack ist = getStackInSlot(getArraySize());
		if(ist != null && ist.getItem() instanceof IPowerCore)
		{
			IPowerCore pc = ((IPowerCore)ist.getItem());

			if(!pc.canUpgrade(ist)) return -1;

			return pc.getPowerRank(ist) + 1;
		}
		if(ist != null && ist.isItemEqual(FPR2.CorePregnant))
		{
			return 0;
		}
		return -1;
	}

	private int[] getPartIndex(int totalIndex)
	{
		int part = totalIndex/6;
		int index = totalIndex%6;
		return new int[]{part, index};
	}

	private int getPart(int totalIndex)
	{
		return totalIndex/6;
	}

	private int getIndex(int totalIndex)
	{
		return totalIndex%6;
	}

	@Override
	public int getSizeInventory()
	{
		return getArraySize()+2;
	}

	public int getArraySize()
	{
		return contents.length*contents[0].length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(i < getArraySize())
		{
			return contents[getPart(i)][getIndex(i)];
		}
		else
		{
			i -= getArraySize();
			switch(i)
			{
				case 0: return core;
				case 1: return result;
			}
			return null;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int amount)
	{
		ItemStack ist = getStackInSlot(i);

		if (ist != null)
        {
            ItemStack itemstack;

            if (ist.stackSize <= amount)
            {
                itemstack = ist;
                setInventorySlotContents(i, null);
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = ist.splitStack(amount);
                if (ist.stackSize == 0)
                {
                	setInventorySlotContents(i, null);
                }
                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		ItemStack ist = getStackInSlot(i);
		if (ist != null)
        {
            ItemStack itemstack = ist;
            setInventorySlotContents(i, null);
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(i < getArraySize())
		{
			contents[getPart(i)][getIndex(i)] = itemstack;
		}
		else
		{
			i -= getArraySize();
			switch(i)
			{
				case 0: core = itemstack; break;
				case 1: result = itemstack; break;
			}
		}
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
	}

	@Override
	public String getInvName()
	{
		return "";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return (Integer.MAX_VALUE-1);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack ist)
	{
		if(ist == null) return false;

		if(i < getArraySize())
		{
			return getValidForPart(ist, getPart(i));
		}
		else
		{
			i -= getArraySize();
			switch(i)
			{
				case 0: return ist.getItem() instanceof IPowerCore;
				case 1: return false;
			}
		}
		return false;
	}

	private boolean getValidForPart(ItemStack ist, int part)
	{
		switch(part)
		{
			case 0: return ist.itemID == Item.diamond.itemID  || ist.getDisplayName().toLowerCase().contains("diamond");
			case 1: return ist.itemID == Item.feather.itemID  || ist.getDisplayName().toLowerCase().contains("feather");
			case 2: return ist.itemID == Item.redstone.itemID || ist.getDisplayName().toLowerCase().contains("redstone");
			case 3: return ist.itemID == Item.sugar.itemID    || ist.getDisplayName().toLowerCase().contains("sugar");
			case 4: return ist.isItemEqual(FPR2.CorePregnant);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(i);

		int p = 0;

		switch(dir)
		{
			case NORTH: p = 0; break;
			case EAST : p = 1; break;
			case SOUTH: p = 2; break;
			case WEST : p = 3; break;
			case UP   : p = 4; break;
			case DOWN : return new int[]{getArraySize()+1};
			case UNKNOWN: return new int[]{};
		}
		int[] res = new int[6];
		for(int j = 0; j < 6; j++)
		{
			res[j] = p*6 + j;
		}
		return res;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		return isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		if(i < getArraySize()) return true;

		i -= getArraySize();
		switch(i)
		{
			case 0: return !isProcessing;
			case 1: return true;
		}
		return false;
	}

	public float getCurrentProgress()
	{
		return isProcessing ? ((float)progressCurrent)/burnTileTable[coreIndex] : 0F;
	}

	public float getTotalProgress()
	{
		return isProcessing ? ((float)progressTotal)/upgradeTable[coreIndex] : 0F;
	}

	public float getCurrentProgressForDisplay()
	{
		return ((float)ProgressBarCurrent)/64F;
	}

	public float getTotalProgressForDisplay()
	{
		return ((float)ProgressBarTotal)/64F;
	}

	public boolean isProcessing()
	{
		return isProcessing;
	}

	private boolean canConsumeItems()
	{
		boolean canConsume = true;
		boolean partConsume = false;

		int[] indexes = new int[5];

		for(int i = 0; i < 5; i++)
		{
			partConsume = false;
			for(int j = 0; j < 6; j++)
			{
				if(contents[i][j] != null && getValidForPart(contents[i][j], i) && contents[i][j].stackSize > 0)
				{
					indexes[i] = j;
					partConsume = true;
					break;
				}
			}
			canConsume = canConsume && partConsume;
			if(!canConsume) return false;
		}

		if(canConsume)
		{
			for(int i = 0; i < 5; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					if(contents[i][j] != null && getValidForPart(contents[i][j], i) && contents[i][j].stackSize > 0)
					{
						decrStackSize(i*6+j, 1);
						break;
					}
				}
			}
		}

		return canConsume;
	}

	public void readFromNBT(NBTTagCompound rootNBT)
    {
        super.readFromNBT(rootNBT);
        NBTTagList nbttaglist = rootNBT.getTagList("Items");
        contents = new ItemStack[5][6];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound tag = (NBTTagCompound)nbttaglist.tagAt(i);
            int r1 = tag.getInteger("Slot|I");
            int j1 = tag.getInteger("Slot|J");
            if (r1 >= 0 && r1 < contents.length && j1 >= 0 && j1 < contents.length)
            {
                contents[r1][j1] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        NBTTagCompound ctag = (NBTTagCompound)rootNBT.getTag("CurrentCore");
        if(ctag != null)
        {
        	core = ItemStack.loadItemStackFromNBT(ctag);
        }
        NBTTagCompound restag = (NBTTagCompound)rootNBT.getTag("ProcessResult");
        if(restag != null)
        {
        	result = ItemStack.loadItemStackFromNBT(restag);
        }

        //  NBTTagCompound
        //  -> NBTTagList
        // 			->NBTTagCompound
        //  		->NBTTagList
        //					->NBTTagCompound(ItemStack)
        progressCurrent = rootNBT.getLong("Current");
        progressTotal = rootNBT.getLong("Total");
        coreIndex = rootNBT.getInteger("CoreIndex");
        isProcessing = rootNBT.getBoolean("Processing");

        ProgressBarCurrent = rootNBT.getInteger("ClientCurrentProgress");
        ProgressBarTotal = rootNBT.getInteger("ClientTotalProgress");
    }

    public void writeToNBT(NBTTagCompound rootNBT) // Compound
    {
        super.writeToNBT(rootNBT);
        NBTTagList listRoot = new NBTTagList(); // Compound -> List

        for (int i = 0; i < getArraySize(); ++i)
        {
        	NBTTagCompound nbttag = new NBTTagCompound(); // Compound -> List -> Compound
        	if (contents[getPart(i)][getIndex(i)] != null)
            {
                nbttag.setInteger("Slot|I", getPart(i));
                nbttag.setInteger("Slot|J", getIndex(i));
                contents[getPart(i)][getIndex(i)].writeToNBT(nbttag);
            }
        	listRoot.appendTag(nbttag);
        }
        rootNBT.setTag("Items", listRoot); //Compound -> List

        if(core != null)
        {
	        NBTTagCompound ctag = new NBTTagCompound();
	        core.writeToNBT(ctag);
	        rootNBT.setTag("CurrentCore", ctag);
        }
        if(result != null)
        {
        	NBTTagCompound restag = new NBTTagCompound();
        	result.writeToNBT(restag);
        	rootNBT.setTag("ProcessResult", restag);
        }

        rootNBT.setLong("Current", progressCurrent);
        rootNBT.setLong("Total", progressTotal);
        rootNBT.setInteger("CoreIndex", coreIndex);
        rootNBT.setBoolean("Processing", isProcessing);

        rootNBT.setInteger("ClientCurrentProgress", ProgressBarCurrent);
        rootNBT.setInteger("ClientTotalProgress", ProgressBarTotal);
    }

    public void readToPacket(ByteArrayDataInput data)
    {
		for(int i = 0; i < getSizeInventory(); i++)
		{
			int id = data.readInt();
			int size = data.readInt();
			int meta = data.readInt();
			if(id > 0 && size > 0)
			{
				setInventorySlotContents(i, new ItemStack(id, size, meta));
			}
			else
			{
				setInventorySlotContents(i, null);
			}
		}
		progressCurrent = data.readLong();
		progressTotal = data.readLong();
		coreIndex = data.readInt();
		isProcessing = data.readBoolean();
	}

	public void writeToPacket(DataOutputStream dos)
	{
		try
		{
			for(int i = 0; i < getSizeInventory(); i++)
			{
				ItemStack ist = getStackInSlot(i);
				int id = -1;
				int size = -1;
				int meta = -1;
				if(ist != null)
				{
					id = ist.itemID;
					size = ist.stackSize;
					meta = ist.getItemDamage();
				}
				dos.writeInt(id);
				dos.writeByte(size);
				dos.writeInt(meta);
			}

			dos.writeLong(progressCurrent);
			dos.writeLong(progressTotal);
			dos.writeInt(coreIndex);
			dos.writeBoolean(isProcessing);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return UpgraderPacketHandler.getPacket(this);
	}

	public int getLightValue()
	{
		return isProcessing ? getCoreLightValue() : 0;
	}

	private int getCoreLightValue()
	{
		return 15*(int)(coreIndex/5F);
	}

	public void dropAllItems(World world, int x, int y, int z)
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack ist = getStackInSlot(i);
			if(ist != null)
			{
				float fx = world.rand.nextFloat() * .8F + .1F;
				float fz = world.rand.nextFloat() * .8F + .1F;
				EntityItem ei = null;

				for(float f = world.rand.nextFloat() * .8F + .1F; ist.stackSize > 0; world.spawnEntityInWorld(ei))
				{
					int x1 = world.rand.nextInt(21) + 10;
					if(x1 > ist.stackSize)
					{
						x1 = ist.stackSize;
						ist.stackSize -= x1;
						ei = new EntityItem(world, x+fx, y+fz, z+f, new ItemStack(ist.itemID, x1, ist.getItemDamage()));
						float mv = .05F;
						ei.motionX = world.rand.nextGaussian() * mv;
						ei.motionY = world.rand.nextGaussian() * mv + .2F;
						ei.motionZ = world.rand.nextGaussian() * mv;
						if(ist.hasTagCompound())
						{
							ei.getEntityItem().setTagCompound((NBTTagCompound)ist.getTagCompound().copy());
						}
					}
					if(ei == null)
					{
						break;
					}
				}
			}
		}
	}
}
