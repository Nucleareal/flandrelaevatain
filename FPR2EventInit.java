package flandrelaevatain;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import flandrelaevatain.block.BlockUpgrader;
import flandrelaevatain.block.item.ItemUpgrader;
import flandrelaevatain.gui.invpickaxe.PickaxeGuiHandler;
import flandrelaevatain.gui.mover.MoverGuiHandler;
import flandrelaevatain.gui.upgrader.UpgraderGuiHandler;
import flandrelaevatain.item.ItemCore;
import flandrelaevatain.item.ItemCoreChip;
import flandrelaevatain.item.ItemLaevatain;
import flandrelaevatain.item.ItemUpgradeCore;
import flandrelaevatain.item.UpgradeCore;

public class FPR2EventInit
{
	public static void cause(FMLInitializationEvent event, FlandreLaevatain mod)
	{
		FPR2.Core			= new ItemCore(FPR2.CoreID).setTextureName("fpr2:Core").setUnlocalizedName("core");
		FPR2.Laevatain		= new ItemLaevatain(FPR2.LaevatainID).setTextureName("fpr2:Laevatain").setUnlocalizedName("laevatain");
		FPR2.LaevatainChip  = new ItemCoreChip(FPR2.LaevatainChipID).setTextureName("fpr2:Chip").setUnlocalizedName("spellchip");
		FPR2.SupportCore	= new ItemUpgradeCore(FPR2.SupportCoreID).setTextureName("fpr2:Support").setUnlocalizedName("supportcore");
		FPR2.SupportCore.setContainerItem(FPR2.SupportCore);

		FPR2.Upgrader		= new BlockUpgrader(FPR2.UpgraderID).setTextureName("fpr2:Tile").setUnlocalizedName("upgrader");
		FPR2.BlockUpgraderRenderID = RenderingRegistry.getNextAvailableRenderId();

		GameRegistry.registerItem(FPR2.Core, "Core", FlandreLaevatain.ModName);
		GameRegistry.registerItem(FPR2.Laevatain, "Laevatain", FlandreLaevatain.ModName);
		GameRegistry.registerItem(FPR2.LaevatainChip, "LaevatainChip", FlandreLaevatain.ModName);
		GameRegistry.registerItem(FPR2.SupportCore, "SupportCore", FlandreLaevatain.ModName);

		GameRegistry.registerBlock(FPR2.Upgrader, ItemUpgrader.class, "Upgrader", FlandreLaevatain.ModName);

		for(int i = 0; i < ((ItemCore)FPR2.Core).getMaxNameAmount(); i++)
		{
			LanguageRegistry.addName(new ItemStack(FPR2.Core, 1, i), "Flandre "+CoreNames[i]);
			LanguageRegistry.instance().addNameForObject(new ItemStack(FPR2.Core, 1, i), "ja_JP", CoreNames_jaJP[i]);
		}
		for(int i = 0; i < ((ItemLaevatain)FPR2.Laevatain).getMaxNameAmount(); i++)
		{
			LanguageRegistry.instance().addStringLocalization("item.laevatain."+i+".name", "Laevatain Form "+LaevatainNames[i]);
			LanguageRegistry.instance().addStringLocalization("item.laevatain."+i+".name", "ja_JP", "裏切りにみてる枝  "+LaevatainNames_jaJP[i]+"形態");
		}
		for(int i = 0; i < ((ItemCoreChip)FPR2.LaevatainChip).getMaxNameAmount(); i++)
		{
			LanguageRegistry.addName(new ItemStack(FPR2.LaevatainChip, 1, i), "SpellCard "+SpellChipNames[i]);
			LanguageRegistry.instance().addNameForObject(new ItemStack(FPR2.LaevatainChip), "ja_JP", SpellChipNames_jaJP[i]);
		}
		for(int i = 0; i < ((ItemUpgradeCore)FPR2.SupportCore).getMaxNameAmount(); i++)
		{
			LanguageRegistry.addName(new ItemStack(FPR2.SupportCore, 1, i), UpgradeCore.of(i).name());
			LanguageRegistry.instance().addNameForObject(new ItemStack(FPR2.SupportCore, 1, i), "ja_JP", UpgradeCore.of(i).getJaJPName());
		}
		for(int i = 0; i < ((BlockUpgrader)FPR2.Upgrader).getMaxIcon(); i++)
		{
			LanguageRegistry.addName(new ItemStack(FPR2.Upgrader, 1, i), TileNames[i]);
			LanguageRegistry.instance().addNameForObject(new ItemStack(FPR2.Upgrader, 1, i), "ja_JP", TileNames_jaJP[i]);
		}

		FPR2.CoreRaw = new ItemStack(FPR2.Core, 1, 0);
		FPR2.CorePregnant = new ItemStack(FPR2.Core, 1, 1);
		FPR2.CoreStick = new ItemStack(FPR2.Core, 1, 2);
		FPR2.CoreBlade = new ItemStack(FPR2.Core, 1, 3);
		FPR2.CoreLaevatainBroken = new ItemStack(FPR2.Core, 1, 4);
		FPR2.CorePlate = new ItemStack(FPR2.Core, 1, 5);
		FPR2.CoreHammer = new ItemStack(FPR2.Core, 1, 6);
		FPR2.Laevataine = new ItemStack(FPR2.Laevatain, 1, 0);
		FPR2.Chip = new ItemStack(FPR2.LaevatainChip, 1, 0);

		FPR2.Abandoned = new ItemStack(FPR2.SupportCore, 1, 0);
		FPR2.Bane = new ItemStack(FPR2.SupportCore, 1, 1);
		FPR2.Collapse = new ItemStack(FPR2.SupportCore, 1, 2);
		FPR2.Destroy = new ItemStack(FPR2.SupportCore, 1, 3);
		FPR2.Elimination = new ItemStack(FPR2.SupportCore, 1, 4);
		FPR2.Flandre = new ItemStack(FPR2.SupportCore, 1, 5);

		FPR2.UpgraderStack = new ItemStack(FPR2.Upgrader, 1, 0);
		FPR2.MoverStack = new ItemStack(FPR2.Upgrader, 1, 1);

		NetworkRegistry.instance().registerGuiHandler(mod, new FPR2GuiHandler());
		FPR2GuiHandler.registerHandler(FPR2.PickaxeGuiID, new PickaxeGuiHandler());
		FPR2GuiHandler.registerHandler(FPR2.UpgraderGuiID, new UpgraderGuiHandler());
		FPR2GuiHandler.registerHandler(FPR2.MoverGuiID, new MoverGuiHandler());
	}

	public static final String[] SpellChipNames = { "Chip" };
	public static final String[] CoreNames = {"Raw Core", "Pregnant Core", "Core Stick", "Core Blade", "Broken Laevatain", "Core Plate", "Hammer"};
	public static final String[] LaevatainNames = {"Pickaxe", "Sword", "Laevatain", "Feather", "Torch", "Igniter", "Shear", "BucketEmpty", "Water Provider", "Lava Provider"};
	public static final String[] TileNames = { "Power Upgrader", "SpellCard Mover" };

	public static final String[] SpellChipNames_jaJP = { "スペルカード記録容器" };
	public static final String[] CoreNames_jaJP = {"妖力の容器", "妖力の満ちた核", "妖力の棒", "妖力の刃", "壊れた裏切りにみてる枝", "妖力のプレート", "妖力のハンマー"};
	public static final String[] LaevatainNames_jaJP = {"鶴橋", "剣", "波動剣", "翼", "明かり", "火打ち石", "鋏", "バケツ", "水の祭器", "溶岩の祭器"};
	public static final String[] TileNames_jaJP = {"妖力上昇器", "スペルカード移動容器"};
}
