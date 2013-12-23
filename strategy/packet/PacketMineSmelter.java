package flandrelaevatain.strategy.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

public class PacketMineSmelter extends PacketBreakEach
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
		List<ItemStack> ists = harvestBlock(world, player, x, y, z, block, meta);

		if(ists == null) return of(null);

		List<ItemStack> results = new LinkedList<ItemStack>();

		for(ItemStack ist : ists)
		{
			ItemStack ist0 = FurnaceRecipes.smelting().getSmeltingResult(ist);
			if(ist0 != null)
			{
				results.add(ist0);
			}
			else
			{
				results.add(ist);
			}
		}
		return of(results);
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
