package flandrelaevatain.gui.upgrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerUpgreader extends Container
{
	private TileUpgrader tile;
	private InventoryPlayer inp;

	private int lastProcessCurrent;
	private int lastProcessTotal;
	private boolean isProcessing;
	private int coreIndex;

	//36, 49
	//46, 107

	//118, 20

	public ContainerUpgreader(TileUpgrader tile, InventoryPlayer inp)
	{
		this.tile = tile;
		this.inp = inp;

		int id = 0;

		for(int j = 0; j < 10; j++)
			for(int i = 0; i < 3; i++)
				addSlotToContainer(new Slot(tile, id++, 36+18*j, 49+18*i));

		addSlotToContainer(new Slot(tile, id++, 117, 20));
		addSlotToContainer(new Slot(tile, id++, 191, 20));

		id = 0;
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inp, id++, 45+18*j, 107+18*i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotId)
	{
		ItemStack ist = null;
		Slot slot = (Slot)inventorySlots.get(slotId);

		if(slot != null && slot.getHasStack())
		{
			ItemStack sub = slot.getStack();
			ist = sub.copy();
			if(isUpgradeInventory(slotId))
			{
				if(tile.canExtractItem(slotId, ist, -1) && mergeItemStack(sub, tile.getSizeInventory(), inventorySlots.size(), true))
				{
				}
				else
				{
					return null;
				}
			}
			else
			{
				boolean canMerge = false;
				for(int i = 0; i < tile.getSizeInventory(); i++)
				{
					if(tile.canInsertItem(i, sub, -1) && mergeItemStack(sub, i, i+1, false))
					{
						canMerge = true;
					}
				}
				if(!canMerge) return null;
			}

			if (sub.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
		}
		return ist;
	}

	private boolean isUpgradeInventory(int slotId)
	{
		return 0 <= slotId && slotId < tile.getSizeInventory();
	}

	@Override
	public ItemStack slotClick(int id, int button, int shift, EntityPlayer player)
    {
		if(id < tile.getSizeInventory() && !tile.canExtractItem(id, null, -1))
		{
			return null;
		}
		return super.slotClick(id, button, shift, player);
    }

	/*
	 * public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
	 * */

	/*public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotId)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			//スロット番号がCore未満
			if (slotId < tile.getArraySize())
			{
				//アイテムの移動(プレイヤーへ)
				if (!this.mergeItemStack(itemstack1, tile.getSizeInventory(), tile.getSizeInventory()+inp.getSizeInventory(), true))
				{
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			//スロット番号がCore
			else if(slotId == tile.getArraySize() && !tile.isProcessing())
			{
				if (!this.mergeItemStack(itemstack1, tile.getSizeInventory(), tile.getSizeInventory()+inp.getSizeInventory(), true))
				{
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			//スロット番号がプレイヤー
			else if (slotId >= tile.getSizeInventory())
			{
				if(mergeItemStack(itemstack1, 0, tile.getSizeInventory()-1, false))
				{
					return null;
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}*/

	/*@Override
	public ItemStack slotClick(int id, int button, int shift, EntityPlayer player)
    {
		return null;
    }*/

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, tile.ProgressBarCurrent);
		par1ICrafting.sendProgressBarUpdate(this, 1, tile.ProgressBarTotal);
	}

	// 更新を送る
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if (lastProcessCurrent != tile.ProgressBarCurrent)
			{
				icrafting.sendProgressBarUpdate(this, 0, tile.ProgressBarCurrent);
			}
			if (lastProcessTotal != tile.ProgressBarTotal)
			{
				icrafting.sendProgressBarUpdate(this, 1, tile.ProgressBarTotal);
			}
		}
		lastProcessCurrent = tile.ProgressBarCurrent;
		lastProcessTotal = tile.ProgressBarTotal;
	}

	// 更新する
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		super.updateProgressBar(par1, par2);
		if (par1 == 0)
		{
			tile.ProgressBarCurrent = par2;
		}
		if (par1 == 1)
		{
			tile.ProgressBarTotal = par2;
		}
	}
}
