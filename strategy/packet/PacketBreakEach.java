package flandrelaevatain.strategy.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.src.nucleareal.DataContainer;
import net.minecraft.src.nucleareal.Direction;
import net.minecraft.src.nucleareal.Position;
import net.minecraft.world.World;
import flandrelaevatain.PositionMarker;
import flandrelaevatain.PositionWithDepth;

public abstract class PacketBreakEach extends PacketBase
{
	public int x;
	public int y;
	public int z;
	public String pName;

	public PacketBreakEach setXYZAndName(int x, int y, int z, String pName)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.pName = pName;
		return this;
	}

	@Override
	public void readPacketData(DataInput con) throws IOException
	{
		x = con.readInt();
		y = con.readInt();
		z = con.readInt();
		pName = con.readUTF();
		readPacketWith(con);
	}

	protected abstract void readPacketWith(DataInput con);

	@Override
	public void writePacketData(DataOutput con) throws IOException
	{
		con.writeInt(x);
		con.writeInt(y);
		con.writeInt(z);
		con.writeUTF(pName);
		writePacketWith(con);
	}

	protected abstract void writePacketWith(DataOutput con);

	@Override
	public void processPacket(NetHandler nethandler)
	{
		DataContainer con = DataContainer.fetch(pName);
		queue = new LinkedList<PositionWithDepth>();

		if(con.world.isAirBlock(x, y, z)) return;

		queue.add(PositionWithDepth.of(x, y, z, 0));

		int id = con.world.getBlockId(x, y, z);
		int meta = con.world.getBlockMetadata(x, y, z);

		EnumSet<Direction> set = Direction.defaultDirectionSet();

		List<ItemStack> list = new LinkedList<ItemStack>();

		PositionMarker.get().init();

		while(!queue.isEmpty())
		{
			PositionWithDepth posW = queue.poll();

			System.out.println("Poll");

			if(posW.depth >= 8) { System.out.println("Reached Max Depth"); continue; }

			int lx = posW.pos.X();
			int ly = posW.pos.Y();
			int lz = posW.pos.Z();

			if(con.world.isAirBlock(x, y, z) || con.world.isAirBlock(lx, ly, lz)) { System.out.println("Airhead"); continue; }

			if(isSameBlock(con, x, y, z, lx, ly, lz))
			{
				System.out.println("Same");

				PositionMarker.get().addMark(posW.pos, Boolean.valueOf(true));

				ContinueInfo ci = forEach(con.world, con.player, lx, ly, lz, Block.blocksList[con.world.getBlockId(lx, ly, lz)], con.world.getBlockMetadata(lx, ly, lz), con.world.getBlockTileEntity(lx, ly, lz) != null);
				if(!ci.isContinue) break;

				if(ci.list != null)
				{
					if(lx != x || ly != y || lz != z)
					{
						con.world.setBlockToAir(lx, ly, lz);
						con.world.setBlock(lx, ly, lz, 0, 0, 0x7);
					}

					for(ItemStack ist : ci.list)
					{
						if(canSpawnItem(con.world, con.player, lx, ly, lz, ist))
						{
							spawnItemStackToAxisBoth(con, ist.copy(), lx, ly, lz);
						}
						else
						{
							list.add(ist.copy());
						}
					}
				}

				for(Direction dir : set)
				{
					PositionWithDepth next = PositionWithDepth.of(posW.pos.moveWith(dir), posW.depth+1);

					if(!con.world.isAirBlock(next.pos.X(), next.pos.Y(), next.pos.Z()) && !PositionMarker.get().getMarked(next.pos))
					{
						System.out.println("Next");
						queue.add(next);
					}
				}
			}
		}

		con.world.setBlockToAir(x, y, z);
		con.world.setBlock(x, y, z, 0, 0, 0x7);

		onMethodCallBack(con.world, con.player, x, y, z, list);
	}

	protected abstract void onMethodCallBack(World world, EntityPlayer player, int x, int y, int z, List<ItemStack> list);

	protected abstract boolean canSpawnItem(World world, EntityPlayer player, int x, int y, int z, ItemStack ist);

	private boolean isSameBlock(DataContainer con, int x, int y, int z, int lx, int ly, int lz)
	{
		/*List<ItemStack> ist0 = harvestBlock(con.world, con.player, x, y, z, Block.blocksList[con.world.getBlockId(x, y, z)], con.world.getBlockMetadata(x, y, z));
		List<ItemStack> ist1 = harvestBlock(con.world, con.player, lx, ly, lz, Block.blocksList[con.world.getBlockId(lx, ly, lz)], con.world.getBlockMetadata(lx, ly, lz));
		return isSameList(ist0, ist1);*/

		return con.world.getBlockId(x, y, z) == con.world.getBlockId(lx, ly, lz) &&
				con.world.getBlockMetadata(x, y, z) == con.world.getBlockMetadata(lx, ly, lz);
	}

	private boolean isSameList(List<ItemStack> ist0, List<ItemStack> ist1)
	{
		if(ist0 == null && ist1 == null) return true;
		if(ist0 == null || ist1 == null) return false;
		if(ist0.size() == 0 && ist1.size() == 0) return true;
		if(ist0.size() == 0 || ist1.size() == 0) return false;
		return ist0.get(0).isItemEqual(ist1.get(0));
	}

	private Queue<PositionWithDepth> queue;

	protected abstract ContinueInfo forEach(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, boolean hasTile);

	protected ContinueInfo of(List<ItemStack> list)
	{
		ContinueInfo ci = new ContinueInfo();
		ci.isContinue = true;
		ci.list = list;
		return ci;
	}

	protected static ContinueInfo failed;
	static
	{
		failed = new ContinueInfo();
		failed.list = null;
		failed.isContinue = false;
	}

	protected static class ContinueInfo
	{
		public List<ItemStack> list;
		public boolean isContinue;
	}
}
