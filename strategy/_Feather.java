package flandrelaevatain.strategy;

import flandrelaevatain.item.UpgradeCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class _Feather extends _Default
{
	@Override
	public String getStateCode()
	{
		return "555020444";
	}

	@Override
	public String getStateName()
	{
		return "Feather";
	}

	@Override
	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) return;
		if(entity.isInWater()) return;

		if(!entity.onGround && isCurrent && entity.motionY < 0)
		{
			entity.addVelocity(0D, -entity.motionY, 0D);
			entity.addVelocity(0D, -1D/8D, 0D);
			entity.fallDistance = 0F;
		}
	}

	public int getFormState()
	{
		return Feather;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Collapse.getPower();
	}
}
