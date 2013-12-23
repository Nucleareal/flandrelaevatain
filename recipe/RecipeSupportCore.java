package flandrelaevatain.recipe;

import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.item.IPowerCore;
import flandrelaevatain.item.UpgradeCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.src.nucleareal.CraftingResult;
import net.minecraft.src.nucleareal.RecipeBase;

public class RecipeSupportCore extends RecipeBase implements ICraftingHandler
{
	@Override
	public void registerAllRecipes()
	{
		GameRegistry.addRecipe(this);
	}

	@Override
	public CraftingResult getResult(InventoryCrafting ic)
	{
		int flandreCount = 0;
		int itemCount = 0;

		ItemStack ist0 = null;

		for(int i = 0; i < ic.getSizeInventory(); i++)
		{
			ItemStack ist = ic.getStackInSlot(i);
			if(ist != null)
			{
				if(isFlandreCore(ist))
				{
					flandreCount++;
				}
				else
				{
					itemCount++;
					if(ist0 != null) return Failed;

					ist0 = ist;
				}
			}
		}
		if(ist0 != null && flandreCount == 1 && itemCount == 1)
		{
			ItemStack res = ist0.copy();
			res.stackSize += 1;
			return successOf(res);
		}

		return Failed;
	}

	private boolean isFlandreCore(ItemStack ist)
	{
		return ist.getItem() instanceof IPowerCore && ist.getItemDamage() == UpgradeCore.Flandre.getPower();
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory matrix)
	{
		if(!getResult((InventoryCrafting)matrix).hasResult) return;

		for(int i = 0; i < matrix.getSizeInventory(); i++)
		{
			ItemStack ist = matrix.getStackInSlot(i);
			if(ist != null)
			{
				if(!isFlandreCore(ist))
				{
					matrix.setInventorySlotContents(i, null);
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{
	}
}
