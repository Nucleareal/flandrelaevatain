package flandrelaevatain.block.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.FPR2;
import flandrelaevatain.block.BlockUpgrader;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemUpgrader extends ItemBlock
{
	private BlockUpgrader block;

	public ItemUpgrader(int par1)
	{
		super(par1);
		setCreativeTab(FPR2.Tab);
		this.block = (BlockUpgrader)FPR2.Upgrader;
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	public void getSubItems(int p, CreativeTabs tab, List list)
	{
		for (int i = 0; i < block.getMaxIcon(); i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	public int getMetadata(int i)
	{
		return i;
	}

	public String getUnlocalizedName(ItemStack ist)
	{
		return super.getUnlocalizedName(ist) + "." + ist.getItemDamage();
	}

	public EnumRarity getRarity(ItemStack ist)
    {
        return ist.isItemEnchanted() ? getEnchantedRarity(ist) : getNormalRarity(ist);
    }

	protected EnumRarity getNormalRarity(ItemStack ist)
	{
		return FPR2.RarityFlandre;
	}

	protected EnumRarity getEnchantedRarity(ItemStack ist)
	{
		return FPR2.RarityFlandreEpic;
	}
}
