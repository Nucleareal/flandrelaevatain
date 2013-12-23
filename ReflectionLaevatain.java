package flandrelaevatain;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.entity.RenderItem;

public class ReflectionLaevatain
{
	public static RenderItem getRenderItem(GuiIngame gui)
	{
		RenderItem ri = null;
		try
		{
			Class<? extends GuiIngame> clazz = gui.getClass();
			Field field = clazz.getDeclaredField(ReflectionLaevatainHelper.RenderItem.getFieldName());
			Object rio = field.get(gui);
			ri = (RenderItem)rio;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ri;
	}
}
