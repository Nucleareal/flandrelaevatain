package flandrelaevatain.strategy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.item.UpgradeCore;
import flandrelaevatain.strategy.packet.PacketSmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class _Smelter extends _Default
{
	@Override
	public String getStateCode()
	{
		return "666616666";
	}

	@Override
	public String getStateName()
	{
		return "Smelter";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean onItemUse(World world, EntityPlayer player, ItemStack ist, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		PacketSmelter ps = new PacketSmelter();
		ps.x = x;
		ps.y = y;
		ps.z = z;
		ps.side = side;
		ps.pName = player.username;
		ps.send();

		return true;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Bane.getPower();
	}
}
