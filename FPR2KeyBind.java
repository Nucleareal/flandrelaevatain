package flandrelaevatain;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class FPR2KeyBind extends KeyHandler
{
	public FPR2KeyBind(KeyBinding[] keys, boolean[] repeats)
	{
		super(keys, repeats);
	}

	@Override
	public String getLabel()
	{
		return "Laevatain Function";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		isPressed = true;
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		isPressed = false;
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return types;
	}

	private EnumSet<TickType> types = EnumSet.of(TickType.CLIENT);

	public static boolean isPressed = false;
}
