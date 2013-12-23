package flandrelaevatain.gui.mover;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MoverGuiHandler implements IGuiHandler
{
	public static ContainerMover containerServer;
	public static ContainerMover containerClient;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		containerServer = new ContainerMover((TileMover)world.getBlockTileEntity(x, y, z), player.inventory);
		return containerServer;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		containerClient = new ContainerMover((TileMover)world.getBlockTileEntity(x, y, z), player.inventory);
		return new GuiMover((TileMover)world.getBlockTileEntity(x, y, z), player.inventory);
	}
}
