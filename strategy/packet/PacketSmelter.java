package flandrelaevatain.strategy.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.src.nucleareal.DataContainer;

public class PacketSmelter extends PacketBase
{
	public int x;
	public int y;
	public int z;
	public int side;
	public String pName;

	@Override
	public void readPacketData(DataInput con) throws IOException
	{
		x = con.readInt();
		y = con.readInt();
		z = con.readInt();
		side = con.readInt();
		pName = con.readUTF();
	}

	@Override
	public void writePacketData(DataOutput con) throws IOException
	{
		con.writeInt(x);
		con.writeInt(y);
		con.writeInt(z);
		con.writeInt(side);
		con.writeUTF(pName);
	}

	@Override
	public void processPacket(NetHandler nethandler)
	{
		DataContainer con = DataContainer.fetch(pName);

		int id = con.world.getBlockId(x, y, z);
		int meta = con.world.getBlockMetadata(x, y, z);

		if(id == 0) return;

		List<ItemStack> list = harvestBlock(con.world, con.player, x, y, z, Block.blocksList[id], meta);

		con.world.playAuxSFX(2001, x, y, z, Block.blocksList[id].blockID + (con.world.getBlockMetadata(x, y, z) << 12));
		con.world.setBlockToAir(x, y, z);

		List<ItemStack> result = new LinkedList<ItemStack>();

		for(ItemStack ist : list)
		{
			ItemStack ist0 = FurnaceRecipes.smelting().getSmeltingResult(ist);
			if(ist0 == null)
			{
				result.add(ist.copy());
			}
			else
			{
				result.add(ist0.copy());
			}
		}

		for(ItemStack ist : result)
		{
			spawnItemStackToAxisBoth(con, ist, x, y, z);
		}
	}
}
