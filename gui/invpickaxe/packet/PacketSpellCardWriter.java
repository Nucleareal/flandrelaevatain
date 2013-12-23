package flandrelaevatain.gui.invpickaxe.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.network.packet.NetHandler;
import net.minecraft.src.nucleareal.UtilWorld;
import flandrelaevatain.FPR2;
import flandrelaevatain.gui.invpickaxe.ContainerPickaxe;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.gui.invpickaxe.PickaxeGuiHandler;
import flandrelaevatain.strategy.packet.PacketBase;

public class PacketSpellCardWriter extends PacketBase
{
	public int id;

	@Override
	public void readPacketData(DataInput con) throws IOException
	{
		id = con.readInt();
	}

	@Override
	public void writePacketData(DataOutput con) throws IOException
	{
		con.writeInt(id);
	}

	@Override
	public void processPacket(NetHandler nethandler)
	{
		ContainerPickaxe con = PickaxeGuiHandler.con;
		if(con == null) { System.out.println("ファッ！？"); return; }

		UtilWorld.loggingIsServerWolrd(con.inp.player.worldObj);

		switch(id)
		{
			case 0: moveSpellCards(false, con); break;
			case 1: moveSpellCards( true, con); break;
		}
	}

	private void moveSpellCards(boolean isRev, ContainerPickaxe con)
	{
		InventoryPickaxe inx;

		if(con.inv.getStackInSlot(0) == null)
		{
			inx = new InventoryPickaxe(FPR2.Chip.copy(), true);
		}
		else
		{
			inx = new InventoryPickaxe(con.inv.getStackInSlot(0), !isRev);
		}

		if(!isRev)
		{
			//Pickaxe -> Chip
			inx.copySpellFrom(con.inv, false);
		}
		else
		{
			//Chip -> Pickaxe
			con.inv.copySpellFrom(inx, true);
		}
		con.inv.save();
		inx.save();

		con.inv.matchAll();
	}
}
