package flandrelaevatain.strategy;

import flandrelaevatain.item.UpgradeCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.nucleareal.UtilWorld;
import net.minecraft.src.nucleareal.UtilWorld.EntityHandler;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class _Laevatain extends _Sword implements ISpellCard
{
	public int getFormState()
	{
		return Laevatain;
	}

	@Override
	public String getStateCode()
	{
		return "010010020";
	}

	@Override
	public String getStateName()
	{
		return "Laevatain";
	}

	@Override
	public int getWeaponDamage()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public void onUpdate(World world, final ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		final EntityLivingBase elv = (EntityLivingBase)entity;
		final World www = world;

		UtilWorld.foreachEntities(world, elv, 8D, new EntityHandler()
		{
			@Override
			public boolean onEntity(Entity e)
			{
				if(e instanceof EntityMob || e instanceof IMob)
				{
					EntityLivingBase el = (EntityLivingBase)e;
					if(el.isEntityAlive())
					{
						damageItem(ist, elv);
						el.setHealth(0.0625F);
						www.createExplosion(elv, el.posX, el.posY, el.posZ, 0F, true);
						www.setLightValue(EnumSkyBlock.Block, (int)Math.floor(el.posX), (int)Math.floor(el.posY), (int)Math.floor(el.posZ), 15);
						el.attackEntityFrom(DamageSource.causeMobDamage(elv), 495F);
					}
				}
				if(e instanceof EntityDragonPart)
				{
					EntityDragonPart ep = (EntityDragonPart)e;
					if(ep.isEntityAlive())
					{
						damageItem(ist, elv);
						www.createExplosion(elv, ep.posX, ep.posY, ep.posZ, 0F, true);
						ep.attackEntityFrom(DamageSource.causeMobDamage(elv), 495F);
					}
				}
				return false;
			}
		});
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Destroy.getPower();
	}
}
