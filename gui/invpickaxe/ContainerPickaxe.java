package flandrelaevatain.gui.invpickaxe;

import flandrelaevatain.item.IPowerCore;
import flandrelaevatain.item.ISpellCardInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPickaxe extends Container
{
	public InventoryPickaxe inv;
	public InventoryPlayer inp;

	//96, 51

	public ContainerPickaxe(InventoryPickaxe inv, InventoryPlayer inp)
	{
		this.inv = inv;
		this.inp = inp;

		int id = 0;
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				addSlotToContainer(new SlotPickaxe(inv, id++, 96 + 24*x, 51 + 24*y));
			}
		}
		addSlotToContainer(new Slot(inv, id++,  36, 166));
		addSlotToContainer(new Slot(inv, id++, 204, 166));

		id = 0;
		for(int y = 0; y < 4; y++)
		{
			int sx = y == 0 ? 1 : 9000;
			int sy = y == 0 ? 1 : 9000;

			for(int x = 0; x < 9; x++)
			{
				addSlotToContainer(new Slot(inp, id++, sx+18*x, 196+sy));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack slotClick(int id, int button, int shift, EntityPlayer player)
    {
		if((id - 11) == player.inventory.currentItem) return null;

		if(id < 0 || 9 <= id || button < 0 || 1 < button)
		{
			onClicked(id == 9, id == 10, id, button);
			return null;
		} else
		if(button == 0)
		{
			inv.declease(id);
		} else
		if(button == 1)
		{
			inv.inclease(id);
		}
		return null;
    }

	private void onClicked(boolean isSpellTrance, boolean isCorePower, int id, int button)
	{
		if(isSpellTrance)
		{
			eject();
		}
		else
		if(isCorePower)
		{
			ejectPower();
		}
		else
		{
			if(button == 0)
			{
				insert(id - 11);
			}
			else
			{
				addPowerCore(id - 11);
			}
		}
	}

	private void ejectPower()
	{
		ItemStack ist = inv.getStackInSlot(1);
		if(ist != null)
		{
			inp.addItemStackToInventory(ist);
			inv.setInventorySlotContents(1, null);
		}
	}

	private void addPowerCore(int i)
	{
		if(i < 0 || inv.getStackInSlot(1) != null) return;

		ItemStack ist = inp.getStackInSlot(i);
		if(ist != null && ist.getItem() instanceof IPowerCore)
		{
			inv.setInventorySlotContents(1, ist);
			inp.setInventorySlotContents(i, null);
		}
	}

	private void insert(int i)
	{
		if(i < 0 || inv.getStackInSlot(0) != null) return;

		ItemStack ist = inp.getStackInSlot(i);
		if(ist != null && ist.getItem() instanceof ISpellCardInfo)
		{
			inv.setInventorySlotContents(0, ist);
			inp.setInventorySlotContents(i, null);
		}
	}

	private void eject()
	{
		ItemStack ist = inv.getStackInSlot(0);
		if(ist != null)
		{
			inp.addItemStackToInventory(ist);
			inv.setInventorySlotContents(0, null);
		}
	}
}
