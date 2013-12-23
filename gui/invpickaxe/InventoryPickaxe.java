package flandrelaevatain.gui.invpickaxe;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.src.nucleareal.UtilItemStack;
import net.minecraft.src.nucleareal.UtilMinecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import flandrelaevatain.item.IPowerCore;
import flandrelaevatain.item.ISpellCardInfo;
import flandrelaevatain.item.UpgradeCore;
import flandrelaevatain.strategy.ISpellCard;
import flandrelaevatain.strategy.Strategy;

public class InventoryPickaxe implements IInventory
{
	private NBTTagCompound nbt;
	private ItemStack ist;

	private static final int contentsLimit = 2;
	private ItemStack[] contents;

	public InventoryPickaxe(ItemStack ist, boolean isCreativeModify)
	{
		this.isCreativeItemMode = isCreativeModify;
		this.ist = ist;
		this.nbt = UtilItemStack.getNBTFrom(ist);
		profiles = new byte[16][9];
		contents = new ItemStack[contentsLimit];
		load(nbt);
	}

	@Override
	public int getSizeInventory()
	{
		return contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(i > 1) i -= 9;
		i = MathHelper.clamp_int(i, 0, 1);
		return contents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(i > 1) i -= 9;
		i = MathHelper.clamp_int(i, 0, 1);
		if (this.contents[i] != null)
        {
            ItemStack itemstack;

            if (this.contents[i].stackSize <= j)
            {
                itemstack = this.contents[i];
                this.contents[i] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.contents[i].splitStack(j);
                if (this.contents[i].stackSize == 0)
                {
                    this.contents[i] = null;
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
		if(i > 1) i -= 9;
		i = MathHelper.clamp_int(i, 0, 1);
		if (this.contents[i] != null)
        {
            ItemStack itemstack = this.contents[i];
            this.contents[i] = null;
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
		if(i > 1) i -= 9;
		i = MathHelper.clamp_int(i, 0, 1);
		this.contents[i] = itemstack;
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
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
		Strategy.get().match(profiles[profileIndex], getSupportCoreLevel());
		save();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{
		load(nbt);
	}

	@Override
	public void closeChest()
	{
		onInventoryChanged();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack ist)
	{
		i -= 9;
		i = MathHelper.clamp_int(i, 0, 1);

		switch(i)
		{
			case 0: return ist != null && (ist.getItem() instanceof ISpellCardInfo);
			case 1: return ist != null && (ist.getItem() instanceof IPowerCore);
		}
		return false;
	}

	private boolean isCreativeItemMode;

	public void save(NBTTagCompound nbt)
	{
		if(isCreativeItemMode)
			doSave(nbt);
		else
		{
			nbt = UtilItemStack.getNBTFrom(UtilMinecraft.getWorldAndPlayer("").getV2().getCurrentEquippedItem());
			doSave(nbt);
			nbt = UtilItemStack.getNBTFrom(UtilMinecraft.get().thePlayer.getCurrentEquippedItem());
			doSave(nbt);
		}
	}

	public void doSave(NBTTagCompound tags)
	{
		for(int i = 0; i < 16; i++)
			tags.setByteArray(getStateString(i), profiles[i]);

		tags.setInteger("ProfileIndex", profileIndex);
		write(tags);
	}

	private String getStateString(int i)
	{
		return String.format("SpellCardState[%02d]", i);
	}

	public void load(NBTTagCompound nbt)
	{
		profiles = new byte[16][9];

		for(int i = 0; i < 16; i++)
			if(nbt.hasKey(getStateString(i)))
				profiles[i] = nbt.getByteArray(getStateString(i));

		profileIndex = nbt.getInteger("ProfileIndex");

		read(nbt);
	}

	private void read(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = nbt.getTagList("Items");
        this.contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = -1;

            if(nbttagcompound1.hasKey("Slot"))
            {
            	j = nbttagcompound1.getInteger("Slot");
            }

            if (j >= 0 && j < this.contents.length)
            {
                this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	private void write(NBTTagCompound nbt)
	{
		NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.contents.length; ++i)
        {
            if (this.contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setInteger("Slot", i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbt.setTag("Items", nbttaglist);
	}

	private byte[][] profiles;

	private int profileIndex = 0;

	public void declease(int id)
	{
		profiles[profileIndex][id] = (byte)((9 + profiles[profileIndex][id] - 1) % 9);
		match();
	}

	public void inclease(int id)
	{
		profiles[profileIndex][id] = (byte)((9 + profiles[profileIndex][id] + 1) % 9);
		match();
	}

	public ISpellCard match()
	{
		return Strategy.get().match(profiles[profileIndex], getSupportCoreLevel());
	}

	public void onProfileChanged(int from, int to)
	{
		//Strategy.get().match(profiles[profileIndex], getSupportCoreLevel());
		Strategy.get().onProfileChanged(profiles[to], from, to, this);
		save();
	}

	public int getProfileElement(int i)
	{
		return profiles[profileIndex][i];
	}

	public void prevProfile()
	{
		int p = profileIndex;
		profileIndex = (16 + profileIndex - 1) % 16;
		onProfileChanged(p, profileIndex);
	}

	public void nextProfile()
	{
		int p = profileIndex;
		profileIndex = (16 + profileIndex + 1) % 16;
		onProfileChanged(p, profileIndex);
	}

	public String getMatchingName()
	{
		return match().getStateName();
	}

	public int getProfileIndex()
	{
		return profileIndex;
	}

	public void save()
	{
		save(nbt);
	}

	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		for(int i = 0; i < 16; i++)
		{
			Strategy.get().match(profiles[i], getSupportCoreLevel()).onUpdate(world, ist, entity, how, isCurrent);
		}
	}

	public int getFunctionNameColor()
	{
		return Strategy.get().getFunctionNameColor();
	}

	public List<ISpellCard> matchAll()
	{
		List<ISpellCard> list = new LinkedList<ISpellCard>();
		for(int i = 0; i < 16; i++)
		{
			ISpellCard id = Strategy.get().match(profiles[i], getSupportCoreLevel());
			if(!Strategy.get().getDefaultStrategy().getStateName().equals(id.getStateName()))
			{
				list.add(id);
			}
		}
		return list;
	}

	private void save(boolean toContainer)
	{
		if(PickaxeGuiHandler.con != null)
		{
			if(toContainer)
			{
				ItemStack ist = PickaxeGuiHandler.con.getSlot(0).getStack();
				if(ist != null)
				{
					save(UtilItemStack.getNBTFrom(ist));
				}
			}
			else
			{
				save();
			}
		}
	}

	public void copySpellFrom(InventoryPickaxe from, boolean isCopyFromChip)
	{
		byte[][] spell = from.profiles;
		int pIndex = from.profileIndex;

		if(isCopyFromChip)
		{
			//チップから移植、マッチするかどうかは別問題
			for(int j = 0; j < 16; j++)
			{
				for(int i = 0; i < 9; i++)
				{
					profiles[j][i] = spell[j][i];
				}
			}
		}
		else
		{
			String defStrategyName = Strategy.get().getDefaultStrategy().getStateName();
			String toName = Strategy.get().match(spell[pIndex], from.getSupportCoreLevel()).getStateName();

			for(int i = 0; i < 16; i++)
			{
				String nowName = Strategy.get().match(profiles[i], UpgradeCore.Flandre.getPower()).getStateName();

				if(nowName.equals(defStrategyName))
				{
					for(int j = 0; j < 9; j++)
					{
						profiles[i][j] = spell[pIndex][j];
					}
					break;
				}
				else
				if(nowName.equals(toName))
				{
					break;
				}
			}
		}
		matchAll();
	}

	public void dropAll()
	{
		//DataContainer.fetch("").player.dropPlayerItem(contents[0]);
	}

	public void writeSpell(int i, ISpellCard spell)
	{
		profileIndex = i;
		profileIndex %= 16;

		byte[] from = Strategy.spellToArray(spell);

		if(from == null) return;
		if(profiles == null)
		{
			profiles = new byte[16][9];
		}
		if(profiles[profileIndex] == null)
		{
			profiles[profileIndex] = new byte[9];
		}

		for(int j = 0; j < 9; j++)
		{
			profiles[profileIndex][j] = from[j];
		}
		onInventoryChanged();
	}

	public boolean hasEffect()
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(contents[i] != null && contents[i].hasEffect()) return true;
		}
		return false;
	}

	public boolean canDecreaseDamage(ItemStack ist)
	{
		return contents[1] != null && ((IPowerCore)contents[1].getItem()).canDecreaseDamage(contents[1]);
	}

	public int getSupportCoreLevel()
	{
		if(contents[1] == null) return -1;
		return UpgradeCore.of(contents[1].getItemDamage()).getPower();
	}

	public void dropInto(InventoryPlayer inp)
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(contents[i] != null)
			{
				inp.addItemStackToInventory(contents[i]);
			}
		}
	}

	public void addSpellCard(ISpellCard from)
	{
		if(from.getStateName().equals(Strategy.get().getDefaultStrategy().getStateName())) return;

		for(int i = 0; i < 16; i++)
		{
			profileIndex = i;
			ISpellCard card = match();

			if(card.getStateName().equals(from.getStateName())) break;

			if(card.getStateName().equals(Strategy.get().getDefaultStrategy().getStateName()))
			{
				writeSpell(i, from);
				break;
			}
		}
		save();
	}
}
