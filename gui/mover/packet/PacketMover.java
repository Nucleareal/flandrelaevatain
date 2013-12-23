package flandrelaevatain.gui.mover.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.src.nucleareal.UtilWorld;
import flandrelaevatain.FPR2;
import flandrelaevatain.gui.invpickaxe.ContainerPickaxe;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.gui.invpickaxe.PickaxeGuiHandler;
import flandrelaevatain.gui.mover.ContainerMover;
import flandrelaevatain.gui.mover.MoverGuiHandler;
import flandrelaevatain.strategy.ISpellCard;
import flandrelaevatain.strategy.packet.PacketBase;

public class PacketMover extends PacketBase
{
	public boolean[] isMove = new boolean[16];

	@Override
	public void readPacketData(DataInput con) throws IOException
	{
		for(int i = 0; i < isMove.length; i++)
		{
			isMove[i] = con.readBoolean();
		}
	}

	@Override
	public void writePacketData(DataOutput con) throws IOException
	{
		for(int i = 0; i < isMove.length; i++)
		{
			con.writeBoolean(isMove[i]);
		}
	}

	@Override
	public void processPacket(NetHandler nethandler)
	{
		ContainerMover con = MoverGuiHandler.containerServer;
		if(con == null)
		{
			con = MoverGuiHandler.containerClient;
		}
		if(con == null) { System.out.println("ファッ！？"); return; }

		UtilWorld.loggingIsServerWolrd(con.inv.worldObj);

		ItemStack ist0 = con.inv.getStackInSlot(0);
		ItemStack ist1 = con.inv.getStackInSlot(1);
		if(ist0 == null) {System.out.println("ぬるぬる"); ist0 = FPR2.Laevataine.copy();}
		if(ist1 == null) {System.out.println("ぬるぬる"); ist1 = FPR2.Laevataine.copy();}

		InventoryPickaxe invFrom = new InventoryPickaxe(ist0, true);
		InventoryPickaxe invTo   = new InventoryPickaxe(ist1, true);

		List<ISpellCard> list = invFrom.matchAll();

		for(int i = 0; i < 16; i++)
		{
			if(isMove[i])
			{
				invTo.addSpellCard(list.get(i));
			}
		}
		invFrom.save();
		invTo.save();
		invFrom.matchAll();
		invTo.matchAll();
	}
}
