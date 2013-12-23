package flandrelaevatain.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.FPR2;

public abstract class ItemMultiParts extends Item
{
	public ItemMultiParts(int i)
	{
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		MultiIcon = new Icon[getMaxIconAmount()];
		setCreativeTab(FPR2.Tab);
	}

	protected abstract int getMaxIconAmount();

	protected Icon[] MultiIcon;

	public int getMetadata(int i)
	{
		return i;
	}

	public void getSubItems(int p, CreativeTabs tab, List list)
	{
		for (int i = 0; i < getMaxDamageAmount(); i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	public String getUnlocalizedName(ItemStack ist)
	{
		return super.getUnlocalizedName(ist) + "." + ist.getItemDamage();
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		return MultiIcon[damage];
	}

	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registerer)
    {
		register(registerer);
    }

	public int getMaxNameAmount()
	{
		return getMaxDamageAmount();
	}

	protected abstract void register(IconRegister r);

	protected abstract int getMaxDamageAmount();

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
