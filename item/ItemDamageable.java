package flandrelaevatain.item;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flandrelaevatain.FPR2;

public abstract class ItemDamageable extends Item
{
	public ItemDamageable(int i)
	{
		super(i);
		setMaxDamage(getMaxDamageAmount());
		setMaxStackSize(getMaxStackAmount());
		MultiIcon = new Icon[getMaxIconAmount()];
		setCreativeTab(FPR2.Tab);
		rand = new Random();
	}

	protected abstract int getMaxIconAmount();

	protected Icon[] MultiIcon;
	protected Random rand;

	public int getMetadata(int i)
	{
		return i;
	}

	public String getUnlocalizedName(ItemStack ist)
	{
		return super.getUnlocalizedName(ist) + "." + getUnlocalizedNameForDisplay(ist);
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		return MultiIcon[0];
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registerer)
	{
		register(registerer);
	}

	public Icon getIcon(ItemStack ist, int layer, EntityPlayer Player, ItemStack Using, int Remain)
	{
		NBTTagCompound nbt = ist.getTagCompound();
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
			ist.setTagCompound(nbt);
		}
		return MultiIcon[getIconIndex(ist, layer, Player, Using, Remain, nbt)];
	}

	public int getMaxNameAmount()
	{
		return getMaxIconAmount();
	}

	protected abstract int getIconIndex(ItemStack ist, int layer, EntityPlayer player, ItemStack using, int remain, NBTTagCompound nbt);

	protected abstract String getUnlocalizedNameForDisplay(ItemStack ist);

	protected abstract void register(IconRegister r);

	protected abstract int getMaxDamageAmount();

	protected abstract int getMaxStackAmount();

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
