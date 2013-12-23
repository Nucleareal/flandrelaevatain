package flandrelaevatain.strategy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import flandrelaevatain.item.UpgradeCore;
import flandrelaevatain.strategy.packet.PacketChunkEater;

public class _ChunkEater extends _Default
{
	@Override
	@SideOnly(Side.CLIENT)
	public boolean onBlockDestroyed(World world, int m, int x, int y, int z, ItemStack ist, EntityLivingBase by)
	{
		damageItem(ist, by);

		PacketChunkEater packet = new PacketChunkEater();

		Minecraft minecraft = ModLoader.getMinecraftInstance();

		packet.playerName = ((EntityPlayer)by).username;
		packet.x = x;
		packet.y = y;
		packet.z = z;
		packet.px = (int)Math.floor(by.posX);
		packet.py = (int)Math.floor(by.posY);
		packet.pz = (int)Math.floor(by.posZ);

        minecraft.getNetHandler().addToSendQueue(packet);

		return true;
	}

	@Override
	public String getStateCode()
	{
		return "101444676";
	}

	@Override
	public String getStateName()
	{
		return "Chunk Eater";
	}

	@Override
	public int getTextColor()
	{
		return 0xFF0000;
	}

	@Override
	public int getRequireCoreLevel()
	{
		return UpgradeCore.Elimination.getPower();
	}
}
