package flandrelaevatain;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.nucleareal.NBTTool;

public class FPR2
{
	public static final int PickaxeGuiID = 0;
	public static final int UpgraderGuiID = 1;
	public static final int MoverGuiID = 2;

	public static int BlockUpgraderRenderID;

	public static Item Core; public static int CoreID = 17261;
	public static Item Laevatain; public static int LaevatainID = 17262;
	public static Item LaevatainChip; public static int LaevatainChipID = 17263;
	public static Item SupportCore; public static int SupportCoreID = 17264;

	public static Block Upgrader; public static int UpgraderID = 495;

	public static NBTTool NBT;

	public static EnumRarity RarityFlandre;
	public static EnumRarity RarityFlandreEpic;
	public static EnumRarity RarityFlandreGod;
	public static CreativeTabs Tab = new FlandreTab("FPR2");

	public static ItemStack CoreRaw;
	public static ItemStack CorePregnant;
	public static ItemStack CoreStick;
	public static ItemStack CoreBlade;
	public static ItemStack CoreLaevatainBroken;
	public static ItemStack CorePlate;
	public static ItemStack CoreHammer;
	public static ItemStack Chip;
	public static ItemStack Laevataine;

	public static ItemStack Abandoned;
	public static ItemStack Bane;
	public static ItemStack Collapse;
	public static ItemStack Destroy;
	public static ItemStack Elimination;
	public static ItemStack Flandre;

	public static ItemStack UpgraderStack;
	public static ItemStack MoverStack;

	public static int EntityTorchStateID = 0;
}
