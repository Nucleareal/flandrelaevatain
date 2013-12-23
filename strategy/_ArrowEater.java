package flandrelaevatain.strategy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.src.nucleareal.UtilWorld;
import net.minecraft.src.nucleareal.UtilWorld.EntityHandler;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _ArrowEater extends _Default
{
	@Override
	public String getStateCode()
	{
		return "000722000";
	}

	@Override
	public String getStateName()
	{
		return "Arrow Eater";
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Collapse.getPower();
	}

	@Override
	public void onUpdate(World world, final ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		final EntityPlayer player = (EntityPlayer)entity;
		final World www = world;

		UtilWorld.foreachEntities(world, player, 8D, new EntityHandler()
		{
			@Override
			public boolean onEntity(Entity e)
			{
				if(e instanceof EntityArrow)
				{
					EntityArrow ea = (EntityArrow)e;
					ea.motionX = -ea.motionX;
					ea.motionY = -ea.motionY;
					ea.motionZ = -ea.motionZ;
					//1倍返しだ
				}
				return false;
			}
		});
	}
}
