package flandrelaevatain.gui.mover;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import unyuho.common.gui.scrollbar.IScrollable;

public class ContainerMover extends Container implements IScrollable
{
	//14 226, 104
	public TileMover inv;
	public InventoryPlayer inp;

	public ContainerMover(TileMover tile, InventoryPlayer inp)
	{
		int id;

		id = 0;
		addSlotToContainer(new Slot(tile, id++,  14, 104));
		addSlotToContainer(new Slot(tile, id++, 226, 104));

		id = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inp, id++, 56+j*16, 130+i*16));
			}
		}

		this.inv = tile;
		this.inp = inp;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
	{
		ItemStack ist = null;
		Slot slot = (Slot)inventorySlots.get(slotId);

		if(slot != null && slot.getHasStack())
		{
			ItemStack sub = slot.getStack();
			ist = sub.copy();
			if(isMoverInventory(slotId))
			{
				if(inv.canExtractItem(slotId, ist, -1) && mergeItemStack(sub, inv.getSizeInventory(), inventorySlots.size(), true))
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
				for(int i = 0; i < inv.getSizeInventory(); i++)
				{
					if(inv.canInsertItem(i, sub, -1) && mergeItemStack(sub, i, i+1, false))
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

	private boolean isMoverInventory(int slotId)
	{
		return 0 <= slotId && slotId < inv.getSizeInventory();
	}

	@Override
	public ItemStack slotClick(int id, int button, int shift, EntityPlayer player)
    {
		if(id < inv.getSizeInventory() && !inv.canExtractItem(id, null, -1))
		{
			return null;
		}
		return super.slotClick(id, button, shift, player);
    }

	@Override
	public void scrollPerformed(int scrollID, int value)
	{
	}
}
