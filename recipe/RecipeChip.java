package flandrelaevatain.recipe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.src.nucleareal.CraftingResult;
import net.minecraft.src.nucleareal.NCore;
import net.minecraft.src.nucleareal.RecipeBase;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.FPR2;
import flandrelaevatain.FlandreLaevatain;
import flandrelaevatain.ReflectionLaevatainHelper;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;
import flandrelaevatain.item.ItemCore;
import flandrelaevatain.item.ItemCoreChip;

public class RecipeChip extends RecipeBase implements ICraftingHandler
{
	@Override
	public void registerAllRecipes()
	{
		GameRegistry.addRecipe(this);
	}

	//1, 3, 7, 15, 31

	private static int[] sizes = {1, 3, 7, 15, 31};
	public static Map<String, Integer> map;
	static
	{
		map = new HashMap<String, Integer>();
		map.put(EnumToolMaterial.WOOD.toString(), 0);
		map.put(EnumToolMaterial.STONE.toString(), 1);
		map.put(EnumToolMaterial.IRON.toString(), 2);
		map.put(EnumToolMaterial.GOLD.toString(), 3);
		map.put(EnumToolMaterial.EMERALD.toString(), 4);
	}

	@Override
	public CraftingResult getResult(InventoryCrafting ic)
	{
		/*Class<? extends ItemTool>[] czs = new Class[]{ItemAxe.class, ItemPickaxe.class, ItemSpade.class};
		boolean[] hasTool = new boolean[czs.length];
		boolean hasCore = false;

		int rank = Integer.MAX_VALUE;

		for(int i = 0; i < ic.getSizeInventory(); i++)
		{
			ItemStack ist = ic.getStackInSlot(i);
			if(ist != null)
			{
				Item item = ist.getItem();
				for(int j = 0; j < czs.length; j++)
				{
					if(item.getClass().equals(czs[j]))
					{
						hasTool[j] = true;
						ItemTool it = (ItemTool)item;
						String name = it.getToolMaterialName();
						rank = map.containsKey(name) ? map.get(name).intValue() < rank ? map.get(name).intValue() : rank : rank;
					}
				}
				if(item instanceof ItemCore)
				{
					hasCore = ist.getItemDamage() == FPR2.CorePregnant.getItemDamage();
				}
			}
		}

		if(rank == Integer.MAX_VALUE) return Failed;

		boolean hasToolResult = true;
		for(int i = 0; i < czs.length; i++)
		{
			hasToolResult &= hasTool[i];
		}

		ItemStack ist0 = FPR2.Chip.copy();
		ist0.stackSize = sizes[rank];
		new InventoryPickaxe(ist0, true).setInventorySlotContents(1, FPR2.Flandre.copy());

		return CraftingResult.of(hasCore && hasToolResult, ist0);*/
		return Failed;
	}

	private EnumToolMaterial getMaterialFromReflection(ItemTool it)
	{
		String fieldName = ReflectionLaevatainHelper.ToolMaterial.getFieldName();
		try
		{
			Class clazz = ItemTool.class;
			Field field = clazz.getField(fieldName);
			field.setAccessible(true);
			EnumToolMaterial material = (EnumToolMaterial)field.get(it);
			return material;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
	{
		/*if(item != null && item.getItem() instanceof ItemCoreChip)
		{
			FlandreLaevatain.proxy.onCrafting(player, item, craftMatrix);
		}*/
		if(item != null && item.getItem() instanceof ItemCore && item.getItemDamage() == FPR2.CorePlate.getItemDamage())
		{
			System.out.println("Chip");
			FlandreLaevatain.proxy.onCrafting(player, item, craftMatrix);
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{
	}
}
