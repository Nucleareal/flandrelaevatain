package flandrelaevatain;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.block.tile.renderer.RenderUpgrader;
import flandrelaevatain.gui.mover.TileMover;
import flandrelaevatain.gui.upgrader.TileUpgrader;
import flandrelaevatain.item.ItemUpgradeCore;
import flandrelaevatain.item.renderer.LaevatainRenderer;

public class FPR2EventPostInit
{
	public static void cause(FMLPostInitializationEvent event, FlandreLaevatain flandreLaevatain)
	{
		FPR2Recipe.registerAll();

		MinecraftForge.EVENT_BUS.register(FPR2.Laevatain);

		MinecraftForgeClient.registerItemRenderer(FPR2.Laevatain.itemID, new LaevatainRenderer());


		GameRegistry.registerTileEntity(TileUpgrader.class, "Upgrader");
		GameRegistry.registerTileEntity(TileMover.class, "Mover");


		RenderUpgrader renderer = new RenderUpgrader();
		RenderingRegistry.registerBlockHandler(FPR2.BlockUpgraderRenderID, renderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileUpgrader.class, renderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileMover.class, renderer);

		FPR2LanguageTransision.registerAll();

		GameRegistry.registerFuelHandler((ItemUpgradeCore)FPR2.SupportCore);
	}
}
