package flandrelaevatain.item;

import net.minecraft.item.ItemStack;

public interface IPowerCore
{
	boolean canDecreaseDamage(ItemStack ist);

	int getPowerRank(ItemStack ist);

	boolean canUpgrade(ItemStack ist);
}
