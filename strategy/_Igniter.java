package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _Igniter extends _Default
{
	@Override
	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		boolean result = new ItemStack(Item.flintAndSteel).getItem().onItemUse(new ItemStack(Item.flintAndSteel), player, world, x, y, z, side, fx, fy, fz);

		if(result) damageItem(ist, player);

		return result;
	}

	@Override
	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
	{
		return false;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Abandoned.getPower();
	}

	public int getFormState()
	{
		return Igniter;
	}

	@Override
	public String getStateCode()
	{
		return "011100011";
	}

	@Override
	public String getStateName()
	{
		return "Igniter";
	}
}
