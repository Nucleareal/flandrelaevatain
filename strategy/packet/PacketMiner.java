package flandrelaevatain.strategy.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketMiner extends PacketBreakEach
{
	@Override
	protected void readPacketWith(DataInput con)
	{
	}

	@Override
	protected void writePacketWith(DataOutput con)
	{
	}

	@Override
	protected ContinueInfo forEach(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, boolean hasTile)
	{
		return of(harvestBlock(world, player, x, y, z, block, meta));
	}

	@Override
	protected void onMethodCallBack(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> list)
	{
		for(ItemStack ist : list)
		{
			if(!player.inventory.addItemStackToInventory(ist))
			{
				player.dropPlayerItem(ist);
			}
		}
	}

	@Override
	protected boolean canSpawnItem(World world, EntityPlayer player, int x, int y, int z, ItemStack ist)
	{
		return false;
	}
}
