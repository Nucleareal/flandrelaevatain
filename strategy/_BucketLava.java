package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;

public class _BucketLava extends _BucketEmpty
{
	public int getFormState()
	{
		return BucketLava;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Destroy.getPower();
	}

	@Override
	public String getStateCode()
	{
		return "000717070";
	}

	@Override
	public String getStateName()
	{
		return "Lava Provider";
	}

	@Override
	protected int getPlaceBlockID()
	{
		return Block.lavaMoving.blockID;
	}
}
