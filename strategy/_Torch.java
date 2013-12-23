package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _Torch extends _Default
{
	@Override
	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		boolean result = new ItemStack(Block.torchWood).getItem().onItemUse(new ItemStack(Block.torchWood), player, world, x, y, z, side, fx, fy, fz);

		if(result) damageItem(ist, player);

		return result;
	}

	@Override
	public boolean hitEntity(ItemStack ist, EntityLivingBase to, EntityLivingBase from)
	{
		if(rand.nextInt(4) == 0) to.setFire(20);

		return false;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Abandoned.getPower();
	}

	public int getFormState()
	{
		return Torch;
	}

	@Override
	public String getStateCode()
	{
		return "010020020";
	}

	@Override
	public String getStateName()
	{
		return "Lighter";
	}
}
