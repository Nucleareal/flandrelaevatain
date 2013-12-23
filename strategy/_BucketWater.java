package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _BucketWater extends _BucketEmpty
{
	public int getFormState()
	{
		return BucketWater;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Bane.getPower();
	}

	@Override
	public String getStateCode()
	{
		return "000767070";
	}

	@Override
	public String getStateName()
	{
		return "Water Provider";
	}

	@Override
	protected int getPlaceBlockID()
	{
		return Block.waterMoving.blockID;
	}
}
