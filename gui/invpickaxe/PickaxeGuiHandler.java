package flandrelaevatain.gui.invpickaxe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class PickaxeGuiHandler implements IGuiHandler
{
	public static ContainerPickaxe con;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		con = new ContainerPickaxe(new InventoryPickaxe(player.getCurrentEquippedItem(), false), player.inventory);
		return con;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiPickaxe(new InventoryPickaxe(player.getCurrentEquippedItem(), false), player.inventory);
	}
}
