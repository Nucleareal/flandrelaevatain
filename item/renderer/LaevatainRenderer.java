package flandrelaevatain.item.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import flandrelaevatain.FPR2;
import flandrelaevatain.item.ItemLaevatain;

public class LaevatainRenderer implements IItemRenderer
{
	private static RenderItem renderItem = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return ItemRenderType.INVENTORY.equals(type);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack ist, Object... data)
	{
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        // ====================== Render item texture ======================
		Icon icon = FPR2.Laevatain.getIcon(ist, 0, null, null, 0);
        renderItem.renderIcon(0, 0, icon, 16, 16);

        // ====================== Render OpenGL square shape ======================
        /*GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA(0, 0, 0, 128);
        tessellator.addVertex(0, 0, 0);
        tessellator.addVertex(0, 8, 0);
        tessellator.addVertex(8, 8, 0);
        tessellator.addVertex(8, 0, 0);
        tessellator.draw();

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);*/

        // ====================== Render text ======================
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //String text = Integer.toString(ist.getItemDamage());
        //fontRenderer.drawStringWithShadow(text, 1, 1, 0xFFFFFF);

	}

	/**/
}
