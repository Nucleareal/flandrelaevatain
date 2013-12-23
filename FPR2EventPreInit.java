package flandrelaevatain;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.nucleareal.ConfigurationCreator;
import net.minecraft.src.nucleareal.NBTTool;
import net.minecraftforge.client.EnumHelperClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import flandrelaevatain.item.entity.EntityTorchState;
import flandrelaevatain.recipe.RecipeChip;
import flandrelaevatain.recipe.RecipeSupportCore;

public class FPR2EventPreInit
{
	public static void cause(FMLPreInitializationEvent event, FlandreLaevatain mod)
	{
		ConfigurationCreator.create(event, mod);

		FPR2.NBT = new NBTTool("FPR2");

		FPR2.RarityFlandre			= EnumHelperClient.addRarity("Flandre", 0xc, "Flandre");
		FPR2.RarityFlandreEpic		= EnumHelperClient.addRarity("FlandreEpic", 0x4, "Flandre Epic");
		FPR2.RarityFlandreGod		= EnumHelperClient.addRarity("FlandreGod", 0x0, "Flandre God");

		KeyBinding[] keys = {new KeyBinding("key.laevatain", Keyboard.KEY_F)};
		boolean[] repeats = {false};
		KeyBindingRegistry.registerKeyBinding(new FPR2KeyBind(keys, repeats));

		EntityRegistry.registerModEntity(EntityTorchState.class, "TorchState", FPR2.EntityTorchStateID, FlandreLaevatain.instance, 250, 5, true);

		FlandreLaevatain.proxy.registerRendering();
		GameRegistry.registerPlayerTracker(new FPR2PlayerTracker());

		GameRegistry.registerCraftingHandler(FPR2CraftingObserver.get());
		FPR2CraftingObserver.register(new RecipeChip());
		FPR2CraftingObserver.register(new RecipeSupportCore());
	}
}
