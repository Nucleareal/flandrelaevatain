package flandrelaevatain;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FlandreTab extends CreativeTabs
{
	public FlandreTab(String label)
	{
		super(label);
		LanguageRegistry.instance().addStringLocalization("itemGroup.FPR2", "Flandre Laevatain");
	}

	public ItemStack getIconItemStack()
    {
        return new ItemStack(FPR2.Laevatain);
    }
}
