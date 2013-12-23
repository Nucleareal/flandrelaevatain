package flandrelaevatain.gui.upgrader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiUpgrader extends GuiContainer
{
	private TileUpgrader tile;
	private InventoryPlayer inp;
	private ResourceLocation guilocation = new ResourceLocation("fpr2:textures/gui/upgrader.png");

	public GuiUpgrader(TileUpgrader tile, InventoryPlayer inp)
	{
		super(new ContainerUpgreader(tile, inp));
		this.tile = tile;
		this.inp = inp;
		xSize = 251;
		ySize = 196;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		String percCurrent = String.format("%03.8f%%", (1F - tile.getCurrentProgress()) * 100F);
		String percTotal = String.format("%03.8f%%", (tile.getTotalProgress() * 100F));

		fontRenderer.drawString(percCurrent, 12, 14   , 0x000000);
		fontRenderer.drawString(percTotal  , 12, 14+20, 0x000000);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(guilocation);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		//TODO Draw Process
		drawHorizonalCenteringProgress(x+93, y+12, 64, 196, 64, 32, tile.getTotalProgressForDisplay());
		drawHorizonalCenteringProgress(x+93, y+12,  0, 196, 64, 16, tile.getCurrentProgressForDisplay());
	}

	protected void drawHorizonalCenteringProgress(int bx, int by, int sx, int sy, int gmaxX, int gy, float rate)
	{
		int bdx = (int)( (gmaxX - (gmaxX * rate))/2 );
		int drawX = (int)(gmaxX * rate);
		drawTexturedModalRect(bx + bdx, by, sx + bdx, sy, drawX, gy);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}
}
