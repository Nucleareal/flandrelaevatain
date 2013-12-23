package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.item.UpgradeCore;
import flandrelaevatain.strategy.packet.PacketMineSmelter;
import flandrelaevatain.strategy.packet.PacketMiner;

public class _Miner extends _Default
{
	@Override
	@SideOnly(Side.CLIENT)
	public boolean onBlockDestroyed(World world, int m, int x, int y, int z, ItemStack ist, EntityLivingBase by)
	{
		return false;
	}

	@Override
	public boolean onBlockStartBreak(World worldObj, EntityPlayer player, ItemStack ist, int x, int y, int z)
	{
		damageItem(ist, player);

		PacketMiner packet = new PacketMiner();
		packet.setXYZAndName(x, y, z, player.username);

		packet.send();

		return true;
	}

	@Override
	public String getStateCode()
	{
		return "666606666";
	}

	@Override
	public String getStateName()
	{
		return "Miner";
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Abandoned.getPower();
	}
}
