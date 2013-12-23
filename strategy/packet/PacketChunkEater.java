package flandrelaevatain.strategy.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import flandrelaevatain.item.ItemLaevatain;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.src.nucleareal.DataContainer;
import net.minecraft.src.nucleareal.UtilMinecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.ForgeEventFactory;

public class PacketChunkEater extends PacketBase
{
	public int x, y, z;
	public int px, py, pz;
	public String playerName;

	@Override
	public void readPacketData(DataInput con) throws IOException
	{
		x = con.readInt();
		y = con.readInt();
		z = con.readInt();
		px = con.readInt();
		py = con.readInt();
		pz = con.readInt();
		playerName = con.readUTF();
	}

	@Override
	public void writePacketData(DataOutput con) throws IOException
	{
		con.writeInt(x);
		con.writeInt(y);
		con.writeInt(z);
		con.writeInt(px);
		con.writeInt(py);
		con.writeInt(pz);
		con.writeUTF(playerName);
	}

	@Override
	public void processPacket(NetHandler nethandler)
	{
		DataContainer con;
		con = DataContainer.fetch(playerName);
		doProcessPacket(con);
		con = DataContainer.fetchClient();
		doProcessPacket(con);
	}

	private void doProcessPacket(DataContainer con)
	{
		int cx = (x >> 4) << 4;
		int cz = (z >> 4) << 4;

		ItemStack laevatain = con.player.getCurrentEquippedItem();

		List<List<ItemStack>> drops = new LinkedList<List<ItemStack>>();

		for(int dx = 0; dx < 16; dx++)
		{
			for(int dz = 0; dz < 16; dz++)
			{
				for(int dy = 1; dy < con.world.getActualHeight(); dy++)
				{
					int lx = cx + dx;
					int lz = cz + dz;

					int id = con.world.getBlockId(lx, dy, lz);
					int meta = con.world.getBlockMetadata(lx, dy, lz);

					if(Block.blocksList[id] != null && !con.world.blockHasTileEntity(lx, dy, lz))
					{
						drops.add(harvestBlock(con.world, con.player, lx, dy, lz, Block.blocksList[id], meta));
						con.world.setBlockToAir(lx, dy, lz);

						if(laevatain != null && laevatain.getItemDamage() <= laevatain.getMaxDamage())
						{
							((ItemLaevatain)laevatain.getItem()).damageItem(laevatain, con.player);
						}
					}
				}
			}
		}
		if(con.world.isAirBlock(px, py-2, pz))
		{
			con.world.setBlock(px, py-2, pz, Block.stone.blockID, 0, 7);
		}

		int cursor = 0;
		TileEntityChest tile;

		tile = newChest(cursor++, con.world, cx, cz);

		if(drops.size() > 0)
			System.out.println(String.format("%d iterable for chunk", drops.size()));

		for(List<ItemStack> list : drops)
		{
			for(ItemStack ist : list)
			{
				spawnItemStackToPlayerBoth(con, ist);

				/*EntityItem ei = new EntityItem(con.world, con.player.posX, con.player.posY, con.player.posZ, ist.copy());
				ei.setPosition(con.player.posX, con.player.posY, con.player.posZ);
				//ei.setEntityItemStack(ist.copy());
				con.world.spawnEntityInWorld(ei);*/

				/*ItemStack left = entryItemStack(tile, ist);
				if(left != null)
				{
					tile = newChest(cursor++, con.world, cx, cz);
					entryItemStack(tile, left);
				}*/
			}
		}
	}

	private int[] cpx = {0, 2, 4, 6, 8, 10, 12};
	private int cpy = 2;
	private int[] cpz = {0, 1, 3, 4, 6, 7, 9, 10, 12, 13};

	private TileEntityChest newChest(int cursor, World world, int x, int z)
	{
		int nx = x+cpx[cursor%cpx.length];
		int ny = 64+cpy*(cursor/(cpx.length*cpz.length));
		int nz = z+cpz[(cursor/cpx.length)%cpz.length];

		world.setBlock(nx, ny, nz, Block.chest.blockID, 0, 7);
        TileEntityChest tile = (TileEntityChest)world.getBlockTileEntity(nx, ny, nz);
		return tile;
	}

	public ItemStack entryItemStack(IInventory inv, ItemStack ist)
	{
		for(int i = 0; i < inv.getSizeInventory() && ist.stackSize > 0; i++)
		{
			ItemStack preg = inv.getStackInSlot(i);
			if(preg != null && ist.isItemEqual(preg) && preg.stackSize < preg.getItem().getItemStackLimit())
			{
				int amount = Math.min(preg.getItem().getItemStackLimit() - preg.stackSize, ist.stackSize);
				ist.stackSize -= amount;
				preg.stackSize += amount;
				inv.setInventorySlotContents(i, preg);
			} else
			if(preg == null)
			{
				int sub = ist.stackSize - ist.getItem().getItemStackLimit();
				int amount = Math.min(sub < 0 ? Integer.MAX_VALUE : sub, ist.stackSize);
				ist.stackSize -= amount;
				ItemStack pg = ist.copy();
				pg.stackSize = amount;
				inv.setInventorySlotContents(i, pg);
			}
		}

		return ist.stackSize <= 0 ? null : ist;
	}

	@Override
	public int getPacketSize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try
		{
			writePacketData(dos);
		}
		catch (IOException e)
		{
		}
		finally
		{
			try
			{
				dos.close();
			}
			catch (IOException e)
			{
			}
		}
		return baos.toByteArray().length;
	}

	@Override
	public boolean isRealPacket()
	{
		return false;
	}
}
