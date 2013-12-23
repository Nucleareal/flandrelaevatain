package flandrelaevatain;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.ICraftingHandler;

public class FPR2CraftingObserver implements ICraftingHandler
{
	private FPR2CraftingObserver()
	{
	}
	private static final FPR2CraftingObserver instance = new FPR2CraftingObserver();
	public static FPR2CraftingObserver get()
	{
		return instance;
	}

	/*  */

	private static List<ICraftingHandler> list;
	static
	{
		list = new LinkedList<ICraftingHandler>();
	}

	public static void register(ICraftingHandler handler)
	{
		list.add(handler);
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
	{
		for(ICraftingHandler handler : list)
		{
			handler.onCrafting(player, item, craftMatrix);
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{
		for(ICraftingHandler handler : list)
		{
			handler.onSmelting(player, item);
		}
	}
}
