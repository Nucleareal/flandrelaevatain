package flandrelaevatain.gui.invpickaxe;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class SlotPickaxe extends Slot
{
	public SlotPickaxe(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

    @Override
    public boolean isItemValid(ItemStack ist)
    {
        return false;
    }

    @Override
    public ItemStack getStack()
    {
        return null;
    }

    @Override
    public void putStack(ItemStack ist)
    {
    }

    @Override
    public void onSlotChanged()
    {
    }

    @Override
    public int getSlotStackLimit()
    {
        return 0;
    }

    @Override
    public Icon getBackgroundIconIndex()
    {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i)
    {
        return null;
    }
}
