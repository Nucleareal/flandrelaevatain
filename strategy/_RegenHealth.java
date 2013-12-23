package flandrelaevatain.strategy;

import javax.xml.ws.handler.PortInfo;

import flandrelaevatain.item.UpgradeCore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class _RegenHealth extends _Default
{
	@Override
	public String getStateCode()
	{
		return "101111010";
	}

	@Override
	public String getStateName()
	{
		return "Regenerator";
	}

	@Override
	public void onUpdate(World world, ItemStack ist, Entity entity, int how, boolean isCurrent)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer enp = (EntityPlayer)entity;
			if(enp.capabilities.isCreativeMode) return;

			enp.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
			enp.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
			enp.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
			enp.getFoodStats().addStats((ItemFood)Item.beefCooked);
		}
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Destroy.getPower();
	}
}
