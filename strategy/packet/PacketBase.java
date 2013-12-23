package flandrelaevatain.strategy.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.nucleareal.DataContainer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class PacketBase extends Packet
{
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

	public void send()
	{
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(this);
		processPacket(null);
	}

	protected List<ItemStack> harvestBlock(World world, EntityPlayer player, int x, int y, int z, Block block, int meta)
	{
		ArrayList<ItemStack> ists = new ArrayList<ItemStack>();
		ThreadLocal<EntityPlayer> harvesters = new ThreadLocal();

		//player.addExhaustion(0.025F);

		if(block == null) return null;

		harvesters.set(player);
		int fortune = EnchantmentHelper.getFortuneModifier(player);

		if (block.canSilkHarvest(world, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player))
        {
            ForgeEventFactory.fireBlockHarvesting(ists, world, block, x, y, z, meta, 0, 1F, true, player);
            ists.addAll(block.getBlockDropped(world, x, y, z, meta, fortune));
        }

		if(ists.size() > 0)
		System.out.println(String.format("%d items for xyz", ists.size()));

		return ists;
	}

	protected void spawnItemStackToAxisBoth(DataContainer server, ItemStack ist, double x, double y, double z)
	{
		spawnItemStack(DataContainer.fetchClient(), ist, x, y, z);
		spawnItemStack(server, ist, x, y, z);
	}

	protected void spawnItemStackToPlayerBoth(DataContainer server, ItemStack ist)
	{
		spawnItemStack(DataContainer.fetchClient(), ist, server.player.posX, server.player.posY, server.player.posZ);
		spawnItemStack(server, ist, server.player.posX, server.player.posY, server.player.posZ);
	}

	private void spawnItemStack(DataContainer con, ItemStack ist, double x, double y, double z)
	{
		float fx = con.world.rand.nextFloat() * .8F + .1F;
		float fz = con.world.rand.nextFloat() * .8F + .1F;
		EntityItem ei = null;

		for(float f = con.world.rand.nextFloat() * .8F + .1F; ist.stackSize > 0; con.world.spawnEntityInWorld(ei))
		{
			int x1 = con.world.rand.nextInt(21) + 10;
			if(x1 > ist.stackSize)
			{
				x1 = ist.stackSize;
				ist.stackSize -= x1;
				ei = new EntityItem(con.world, x+fx, y+fz, z+f, new ItemStack(ist.itemID, x1, ist.getItemDamage()));
				float mv = .05F;
				ei.motionX = con.world.rand.nextGaussian() * mv;
				ei.motionY = con.world.rand.nextGaussian() * mv + .2F;
				ei.motionZ = con.world.rand.nextGaussian() * mv;
				if(ist.hasTagCompound())
				{
					ei.getEntityItem().setTagCompound((NBTTagCompound)ist.getTagCompound().copy());
				}
			}
		}
	}
}
