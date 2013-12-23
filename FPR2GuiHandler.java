package flandrelaevatain;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class FPR2GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(_map.containsKey(Integer.valueOf(ID)))
		{
			return _map.get(Integer.valueOf(ID)).getServerGuiElement(ID, player, world, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(_map.containsKey(Integer.valueOf(ID)))
		{
			return _map.get(Integer.valueOf(ID)).getClientGuiElement(ID, player, world, x, y, z);
		}
		return null;
	}

	private static Map<Integer, IGuiHandler> _map;
	static
	{
		_map = new HashMap<Integer, IGuiHandler>();
	}

	public static void registerHandler(int ID, IGuiHandler handler)
	{
		_map.put(Integer.valueOf(ID), handler);
	}
}
