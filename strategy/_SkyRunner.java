package flandrelaevatain.strategy;

import flandrelaevatain.item.UpgradeCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.nucleareal.UtilItemStack;
import net.minecraft.world.World;

public class _SkyRunner extends _Default
{
	@Override
	public String getStateCode()
	{
		return "020555000";
	}

	@Override
	public String getStateName()
	{
		return "Sky Runner";
	}

	@Override
	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer enp = (EntityPlayer)entity;
			if(enp.capabilities.isCreativeMode || enp.isInWater()) return;
		}

		NBTTagCompound tag = UtilItemStack.getNBTFrom(ist);

		double prevMotionY = entity.motionY;
		if(tag.hasKey("SkyRunner:Param"))
		{
			prevMotionY = tag.getDouble("SkyRunner:Param");
		}

		if(!entity.onGround && isCurrent && entity.motionY < 0)
		{
			entity.onGround = true;
			double mX = entity.motionX;
			double mZ = entity.motionZ;
			entity.addVelocity(-mX, -entity.motionY, -mZ);
			entity.addVelocity(mX*1.5, -1D/4D, mZ*1.5);
		}
		else
		if(entity.motionY > 0)
		{
			if(prevMotionY < 0) world.playSound(entity.posX, entity.posY, entity.posZ, "mob.enderdragon.wings", 2.5F, 0.8F + rand.nextFloat() * 0.3F, false);

			entity.addVelocity(0D, 1D/24D, 0D);
		}

		entity.fallDistance = 0F;

		tag.setDouble("SkyRunner:Param", entity.motionY);
	}

	public int getFormState()
	{
		return Feather;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Elimination.getPower();
	}
}
