package flandrelaevatain.gui.mover;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.src.nucleareal.UtilWorld;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import flandrelaevatain.gui.invpickaxe.packet.PacketSpellCardWriter;
import flandrelaevatain.gui.mover.packet.PacketMover;

import unyuho.common.gui.scrollbar.GuiScrollBarVertical;
import unyuho.common.gui.scrollbar.IScrollable;

public class GuiMover extends GuiContainer implements IProfileManager
{
	private static final ResourceLocation texture = new ResourceLocation("fpr2:textures/gui/mover.png");
	private TileMover inv;
	private InventoryPlayer inp;

	private IScrollable container;
	private GuiScrollBarVertical scroller;
	private String[] matches;

	private boolean[] isMove;
	private boolean[] isMoveGui;

	private GuiButton move;

	public GuiMover(TileMover inv, InventoryPlayer inp)
	{
		super(new ContainerMover(inv, inp));
		this.inv = inv;
		this.inp = inp;

		xSize = 256;
		ySize = 196;
		container = (IScrollable)inventorySlots;
		isMove    = new boolean[16];
		isMoveGui = new boolean[16];
		for(int i = 0; i < 16; i++)
			isMoveGui[i] = true;

		matches = inv.getMatchedStrings();
		inv.setGui((IProfileManager)this);
	}

	@Override
    public void initGui()
    {
		super.initGui();
		scroller = new GuiScrollBarVertical(container, 0, guiLeft+186, guiTop+7, 105, 0, (16*16-105));

		buttonList.clear();
		int id = 0;
		move = new GuiButton(id++, guiLeft+226, guiTop+36, 16, 16, "");

		buttonList.add(move);
    }

	public void onProfileChanged(String[] sarray, boolean[] flags)
	{
		matches = sarray;
		isMove = flags;

		for(int i = 0; i < 16; i++)
			isMoveGui[i] = true;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		int value = scroller.getValue();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		isMove = inv.getFlags();

		for(int y = 0; y < 16; y++)
        {
        	boolean flag = isMove[y] && isMoveGui[y];
        	int val = y*16-value;

        	if(-8 <= val && val+16 < 105+16)
        	{
        		//drawTexturedModalRect(guiLeft+56, guiTop+7+val, 0, 196 +(flag?0:24), 130, 24);
        		fontRenderer.drawString(matches[y], 56+4, 7+4+val, 0xFFFFFF);
        	}
        }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int value = scroller.getValue();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        scroller.drawScrollBar();
        //
        //56, 7
        mc.getTextureManager().bindTexture(texture);
        for(int y = 0; y < 16; y++)
        {
        	boolean flag = isMove[y] && isMoveGui[y];

        	int val = y*16-value;

        	if(val < 0 && 0 < val+16) //上端
        	{
        		drawTexturedModalRect(guiLeft+56, guiTop+7, 0, 196 +(flag?0:16) -val, 130, 16+val);

        		//TODO flagでの描画処理
        	}
        	else
        	if(0 <= val && val+16 < 105)
        	{
        		drawTexturedModalRect(guiLeft+56, guiTop+7+val, 0, 196 +(flag?0:16), 130, 16);

        		//TODO flagでの描画処理
        	}
        	else
        	if(105 <= val+16 && val < 105) //下端
        	{
        		drawTexturedModalRect(guiLeft+56, guiTop+7+val, 0, 196 +(flag?0:16), 130, (105-val));

        		//TODO flagでの描画処理
        	}
        }
	}

	@Override
    public void handleMouseInput()
    {
        super.handleMouseInput();
        if(scroller.mouseOver())
        {
        	scroller.scrollTo();
        }
    }

	private boolean clicked = false;

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        boolean flag = Mouse.isButtonDown(0);

        if(flag)
        {
        	if(!clicked)
        	{
	        	int x = mouseX - guiLeft - 56;
	        	int y = mouseY - guiTop  -  7;
	        	if(0 <= x && x < 130 && 0 <= y && y < 105)
	        	{
	        		System.out.println(String.format("%d %d", x, y));

	        		int value = scroller.getValue();
	        		int index = ((value + y)/16)%16;
	        		isMoveGui[index] = !isMoveGui[index];
	        	}
	        	clicked = true;
        	}
        }
        else
        {
        	clicked = false;
        }

        if(scroller.mouseOver(mouseX, mouseY))
        {
        	scroller.scrollTo(flag, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, par3);
    }

    @Override
	protected void actionPerformed(GuiButton button)
    {
		/*int id = button.id;
		switch(id)
		{
			case 0: moveSpellCards(); break;
		}*/

    	/*
    	 * PacketSpellCardWriter p = new PacketSpellCardWriter();
		p.id = button.id;
		p.send();
    	 * */
    	moveSpellCards();
    }

	private void moveSpellCards()
	{
		PacketMover packet = new PacketMover();
		for(int i = 0; i < 16; i++)
		{
			packet.isMove[i] = isMove[i] && isMoveGui[i];
		}
		System.out.println("Sending Move Packet From "+UtilWorld.getIsServerWolrd(inv.worldObj));
		packet.send();

		/*for(int i = 0; i < 16; i++)
		{
			inv.store(i, isMove[i] && isMoveGui[i]);
		}
		inv.dispatch();*/
	}
}
