package flandrelaevatain.strategy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.item.UpgradeCore;
import flandrelaevatain.strategy.packet.PacketChunkEater;
import flandrelaevatain.strategy.packet.PacketMineSmelter;

public class _MineSmelter extends _Default
{
	@Override
	@SideOnly(Side.CLIENT)
	public boolean onBlockDestroyed(World world, int m, int x, int y, int z, ItemStack ist, EntityLivingBase by)
	{
		return false;
	}

	@Override
	public boolean onBlockStartBreak(World world, EntityPlayer player, ItemStack ist, int x, int y, int z)
	{
		damageItem(ist, player);

		PacketMineSmelter packet = new PacketMineSmelter();
		packet.setXYZAndName(x, y, z, player.username);

		packet.send();

		return true;
	}

	@Override
	public String getStateCode()
	{
		return "111161111";
	}

	@Override
	public String getStateName()
	{
		return "Melt Chain";
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Collapse.getPower();
	}
}
