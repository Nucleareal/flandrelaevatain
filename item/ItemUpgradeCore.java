package flandrelaevatain.item;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUpgradeCore extends ItemMultiParts implements IPowerCore, IFuelHandler
{
	public ItemUpgradeCore(int i)
	{
		super(i);
	}

	@Override
	protected int getMaxIconAmount()
	{
		return UpgradeCore.size();
	}

	@Override
	protected void register(IconRegister r)
	{
		for (int i = 0; i < UpgradeCore.size(); i++)
		{
			MultiIcon[i] = r.registerIcon(getIconString() + "_"
					+ UpgradeCore.of(i).name());
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack ist, int pass)
	{
		return super.hasEffect(ist)
				|| UpgradeCore.of(ist.getItemDamage()).hasEffect();
	}

	@Override
	protected int getMaxDamageAmount()
	{
		return getMaxIconAmount();
	}

	@Override
	public boolean canDecreaseDamage(ItemStack ist)
	{
		return UpgradeCore.of(ist.getItemDamage()).canDecreaseDamage(ist);
	}

	@Override
	public int getPowerRank(ItemStack ist)
	{
		return UpgradeCore.of(ist.getItemDamage()).getPower();
	}

	@Override
	public boolean canUpgrade(ItemStack ist)
	{
		return UpgradeCore.of(ist.getItemDamage()).canUpgrade(ist);
	}

	public boolean doesContainerItemLeaveCraftingGrid(ItemStack ist)
	{
		return ist.getItemDamage() != UpgradeCore.Flandre.getPower();
	}

	@Override
	public ItemStack getContainerItemStack(ItemStack ist)
	{
		if(ist.getItemDamage() == UpgradeCore.Flandre.getPower())
		{
			return ist;
		}
		return super.getContainerItemStack(ist);
	}

	@Override
	public int getBurnTime(ItemStack ist)
	{
		if(ist.getItemDamage() == UpgradeCore.Flandre.getPower())
		{
			return 100;
		}
		return 0;
	}
}
