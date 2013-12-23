package flandrelaevatain.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.nucleareal.CraftingResult;
import net.minecraft.src.nucleareal.RecipeBase;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.FPR2;
import flandrelaevatain.gui.invpickaxe.InventoryPickaxe;

public class RecipeCore extends RecipeBase
{
	public void registerAllRecipes()
	{
		GameRegistry.addShapedRecipe(FPR2.CoreRaw, new Object[]
		{
			"EsE",
			"sFR",
			"EER",
			'E', Item.diamond,
			's', Item.sugar,
			'F', Item.feather,
			'R', Item.redstone,
		});
		GameRegistry.addShapedRecipe(FPR2.CoreStick, new Object[]
		{
			" s ",
			"   ",
			"c/c",
			's', FPR2.CoreHammer,
			'/', Item.stick,
			'c', FPR2.CorePregnant,
		});
		GameRegistry.addShapedRecipe(FPR2.CoreBlade, new Object[]
		{
			"s c",
			" cc",
			"/  ",
			's', FPR2.CoreHammer,
			'c', FPR2.CorePregnant,
			'/', FPR2.CoreStick,
		});
		GameRegistry.addShapedRecipe(FPR2.Laevataine, new Object[]
		{
			"BCS",
			"BSC",
			"SC ",
			'B', FPR2.CoreBlade,
			'C', FPR2.CorePregnant,
			'S', FPR2.CoreStick,
		});
		GameRegistry.addShapedRecipe(FPR2.Laevataine, new Object[]
		{
			"|  ",
			"|BC",
			"  C",
			'B', FPR2.CoreLaevatainBroken,
			'|', FPR2.CoreBlade,
			'C', FPR2.CorePregnant,
		});

		GameRegistry.addShapedRecipe(FPR2.CoreHammer, new Object[]
		{
			" C ",
			" CC",
			"C  ",
			'C', FPR2.CorePregnant,
		});
		ItemStack res = FPR2.CorePlate.copy();
		res.stackSize = 16;
		GameRegistry.addShapedRecipe(res, new Object[]
		{
			"H",
			" ",
			"C",
			'C', FPR2.CorePregnant,
			'H', FPR2.CoreHammer,
		});

		res = FPR2.Chip.copy();
		new InventoryPickaxe(res, true).setInventorySlotContents(1, FPR2.Flandre.copy());
		GameRegistry.addShapelessRecipe(res, new Object[]{
			FPR2.CorePlate,
		});

		FurnaceRecipes.smelting().addSmelting(FPR2.CoreRaw.getItem().itemID, FPR2.CoreRaw.getItemDamage(), FPR2.CorePregnant, 32);
	}

	@Override
	public CraftingResult getResult(InventoryCrafting ic)
	{
		return Failed;
	}
}
