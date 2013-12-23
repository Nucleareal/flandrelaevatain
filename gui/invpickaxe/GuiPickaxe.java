package flandrelaevatain.gui.invpickaxe;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.LanguageRegistry;

import flandrelaevatain.FPR2;
import flandrelaevatain.gui.invpickaxe.packet.PacketSpellCardWriter;

public class GuiPickaxe extends GuiContainer
{
	private InventoryPickaxe inv;
	private InventoryPlayer inp;
	private ResourceLocation guilocation = new ResourceLocation("fpr2:textures/gui/spell.png");

	private GuiButton prev;
	private GuiButton next;

	private int saveCount = 0;

	public GuiPickaxe(InventoryPickaxe inv, InventoryPlayer inp)
	{
		super(new ContainerPickaxe(inv, inp));
		xSize = 256;
		ySize = 196;
		this.inv = inv;
		this.inp = inp;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
    {
		String name = LanguageRegistry.instance().getStringLocalization(inv.getMatchingName());
		String profile = String.valueOf(inv.getProfileIndex()+1);

		int nx = (xSize - fontRenderer.getStringWidth(name)) / 2;
		fontRenderer.drawStringWithShadow(name, nx, 31, inv.getFunctionNameColor());
		nx = (xSize - fontRenderer.getStringWidth(profile)) / 2;
		fontRenderer.drawStringWithShadow(profile, nx, 10, 0xFFFFFF);

		if(saveCount > 0)
		{
			String comp = "Save Completed";

			nx = (xSize - fontRenderer.getStringWidth(comp)) / 2;
			fontRenderer.drawStringWithShadow(comp, nx, ySize/2-3, 0xFFFFFF);
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(guilocation);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for(int dy = 0; dy < 3; dy++)
		{
			for(int dx = 0; dx < 3; dx++)
			{
				drawTexturedModalRect(x+96 + (16+8)*dx, y+51 + (16+8)*dy, 0+inv.getProfileElement(dy * 3 + dx)*16, 196, 16, 16);
			}
		}

		if(saveCount > 0)
		{
			int nx = (width - 128) / 2;
			int ny = (height - 36) / 2;
			drawTexturedModalRect(nx, ny, 0, 212, 128, 36);
			saveCount--;
		}
	}

	public void initGui()
    {
		super.initGui();

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		prev = new GuiButton(0, x+ 8, y+166, 16, 16, "");
		next = new GuiButton(1, x+64, y+166, 16, 16, "");

		buttonList.clear();
		buttonList.add(prev);
		buttonList.add(next);
    }

	@Override
	protected void actionPerformed(GuiButton button)
    {
		int id = button.id;
		switch(id)
		{
			case 0: moveSpellCards(false); break; // Pickaxe -> Chip
			case 1: moveSpellCards(true); break;  // Chip    -> Pickaxe
		}

		PacketSpellCardWriter p = new PacketSpellCardWriter();
		p.id = button.id;
		p.send();
	}

	private void moveSpellCards(boolean isRev)
	{
		InventoryPickaxe inx;

		if(inv.getStackInSlot(0) == null)
		{
			inx = new InventoryPickaxe(FPR2.Chip.copy(), true);
		}
		else
		{
			inx = new InventoryPickaxe(inv.getStackInSlot(0), !isRev);
		}

		if(!isRev)
		{
			//Pickaxe -> Chip
			inx.copySpellFrom(inv, false);
		}
		else
		{
			//Chip -> Pickaxe
			inv.copySpellFrom(inx, true);
		}
		inv.save();
		inx.save();
	}

	protected void keyTyped(char c, int i)
    {
		if(c == 'a')
		{
			inv.prevProfile();
		}
		if(c == 'd')
		{
			inv.nextProfile();
		}
		if(c == 's')
		{
			inv.save();
			saveCount = 80;
		}
    	super.keyTyped(c, i);
    }

	@Override
	public void onGuiClosed()
	{
		inv.save();

		inv.dropAll();

		super.onGuiClosed();
	}
}
