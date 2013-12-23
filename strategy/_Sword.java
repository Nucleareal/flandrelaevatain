package flandrelaevatain.strategy;

import flandrelaevatain.item.UpgradeCore;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class _Sword extends _Default implements ISpellCard
{
	@Override
	public boolean canHarvestBlock(Block block)
	{
		return false;
	}

	@Override
	public float getStrVsBlock(ItemStack ist, Block block)
	{
		return 0F;
	}

	@Override
	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
	{
		damageItem(ist, from);
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack ist, Block block, int meta)
	{
		return getStrVsBlock(ist, block);
	}

	@Override
	public boolean onBlockDestroyed(World world, int x, int y, int z, int m, ItemStack ist, EntityLivingBase by)
	{
		return false;
	}

	public int getFormState()
	{
		return Sword;
	}

	@Override
	public String getStateCode()
	{
		return "050050020";
	}

	@Override
	public String getStateName()
	{
		return "Sword";
	}

	@Override
	public int getWeaponDamage()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Abandoned.getPower();
	}
}
