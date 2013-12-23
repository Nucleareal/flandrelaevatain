package flandrelaevatain.gui.upgrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class UpgraderGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerUpgreader((TileUpgrader)world.getBlockTileEntity(x, y, z), player.inventory);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiUpgrader((TileUpgrader)world.getBlockTileEntity(x, y, z), player.inventory);
	}

}
