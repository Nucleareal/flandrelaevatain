package flandrelaevatain.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.src.nucleareal.CraftingResult;
import net.minecraft.src.nucleareal.RecipeBase;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.FPR2;

public class RecipeUpgrader extends RecipeBase
{
	@Override
	public void registerAllRecipes()
	{
		GameRegistry.addShapedRecipe(FPR2.UpgraderStack, new Object[]{
			"PPP",
			"CCC",
			"GGG",
			'P', FPR2.CorePlate,
			'C', FPR2.CorePregnant,
			'G', Block.glass,
		});
		GameRegistry.addShapedRecipe(FPR2.MoverStack, new Object[]{
			"TPT",
			"CCC",
			"GGG",
			'T', FPR2.Laevataine,
			'P', FPR2.CorePlate,
			'C', FPR2.CorePregnant,
			'G', Block.glass,
		});
	}

	@Override
	public CraftingResult getResult(InventoryCrafting ic)
	{
		return Failed;
	}
}
