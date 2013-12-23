package flandrelaevatain.gui.mover;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.registry.LanguageRegistry;
import flandrelaevatain.block.ILightValueProvider;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.gui.mover.packet.MoverPacketHandler;
import flandrelaevatain.gui.mover.packet.PacketMover;
import flandrelaevatain.item.ISpellCardInfo;
import flandrelaevatain.strategy.ISpellCard;
import flandrelaevatain.strategy.Strategy;

public class TileMover extends TileEntity implements ISidedInventory, ILightValueProvider
{
	private ItemStack from;
	private ItemStack to;

	public TileMover()
	{
		flags = new boolean[16];
	}

	public void updateEntity()
	{
	}

	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		i = MathHelper.clamp_int(i, 0, getSizeInventory()-1);
		switch(i)
		{
			case 0: return from;
			case 1: return to;
		}
		return null;
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

	private boolean[] flags;

	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		reMatch();
		if(reciver != null)
		{
			flags = new boolean[16];
			for(int i = 0; i < 16; i++)
			{
				flags[i] = !spellcards[i].equals(LanguageRegistry.instance().getStringLocalization(Strategy.get().getDefaultStrategy().getStateName()));
			}
			reciver.onProfileChanged(spellcards, flags);
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
	public void setInventorySlotContents(int i, ItemStack ist)
	{
		i = MathHelper.clamp_int(i, 0, getSizeInventory()-1);
		switch(i)
		{
			case 0: from = ist; break;
			case 1: to = ist; break;
		}
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
		return ist != null && ist.getItem() instanceof ISpellCardInfo && 0 <= i && i < getSizeInventory();
	}

	private int[] verticalAxis = { 0 };
	private int[] horizonalAxis = { 1 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if(side == 0 || side == 1) return verticalAxis;
		return horizonalAxis;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack ist, int j)
	{
		return isItemValidForSlot(i, ist);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack ist, int j)
	{
		return true;
	}

	public void readToPacket(ByteArrayDataInput data)
    {
		boolean hasFromTag = data.readBoolean();
		boolean hasToTag = data.readBoolean();

		if(hasFromTag)
		{
			try
			{
				NBTBase nbt = NBTBase.readNamedTag(data);
				from = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbt);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		if(hasToTag)
		{
			try
			{
				NBTBase nbt = NBTBase.readNamedTag(data);
				to = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbt);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void writeToPacket(DataOutputStream dos)
	{
		try
		{
			boolean hasFromTag = from != null;
			boolean hasToTag   =   to != null;

			dos.writeBoolean(hasFromTag);
			dos.writeBoolean(hasToTag);

			if(hasFromTag)
			{
				NBTTagCompound fromTag = new NBTTagCompound();
				from.writeToNBT(fromTag);
				NBTBase.writeNamedTag(fromTag, dos);
			}

			if(hasToTag)
			{
				NBTTagCompound toTag = new NBTTagCompound();
				to.writeToNBT(toTag);
				NBTBase.writeNamedTag(toTag, dos);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return MoverPacketHandler.getPacket(this);
	}

	@Override
	public int getLightValue()
	{
		return 0;
	}

	private static String[] NullMoverStrings;
	static
	{
		NullMoverStrings = new String[16];

		for(int i = 0; i < 16; i++)
		{
			NullMoverStrings[i] = Strategy.get().getDefaultStrategy().getStateName();
		}
	}

	private String[] spellcards;

	private void reMatch()
	{
		if(from != null)
		{
			spellcards = toDisplayArray(new InventoryPickaxe(from, false).matchAll());
		}
		else
		{
			spellcards = NullMoverStrings;
		}
	}

	public String[] getMatchedStrings()
	{
		if(spellcards != null)
		{
			return spellcards;
		}
		else
		{
			if(from == null)
			{
				return NullMoverStrings;
			}
			else
			{
				reMatch();
				return spellcards;
			}
		}
	}

	private String[] toDisplayArray(List<ISpellCard> list)
	{
		String[] arr = new String[16];
		Iterator<ISpellCard> it = list.iterator();
		for(int i = 0; i < 16; i++)
		{
			arr[i] = LanguageRegistry.instance().getStringLocalization((it.hasNext() ? it.next() : Strategy.get().getDefaultStrategy()).getStateName());
		}
		return arr;
	}

	private IProfileManager reciver;

	public void setGui(IProfileManager manager)
	{
		reciver = manager;
	}

	public boolean [] getFlags()
	{
		return flags;
	}

	private boolean[] storing = new boolean[16];

	public void store(int i, boolean b)
	{
		storing[i] = b;
	}

	public void dispatch()
	{
		PacketMover packet = new PacketMover();
		for(int i = 0; i < 16; i++)
		{
			packet.isMove[i] = storing[i];
		}
		//packet.send();
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
		//packet.processPacket(null);

		//Minecraft.getMinecraft().getNetHandler().addToSendQueue(MoverPacketHandler.getPacket(this));
	}
}
