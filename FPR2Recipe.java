package flandrelaevatain;

import flandrelaevatain.recipe.RecipeChip;
import flandrelaevatain.recipe.RecipeCore;
import flandrelaevatain.recipe.RecipeSupportCore;
import flandrelaevatain.recipe.RecipeUpgrader;

public class FPR2Recipe
{
	public static void registerAll()
	{
		new RecipeCore().registerAllRecipes();
		new RecipeChip().registerAllRecipes();
		new RecipeSupportCore().registerAllRecipes();
		new RecipeUpgrader().registerAllRecipes();
	}
}
