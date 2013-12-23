package flandrelaevatain.item.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.src.nucleareal.DataContainer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import flandrelaevatain.item.entity.EntityTorchState;
import flandrelaevatain.item.entity.model.ModelTorchState;

public class RenderTorchState extends Render
{
	private static final ResourceLocation texture = new ResourceLocation("fpr2:textures/entity/torch.png");
	private static final ModelTorchState model = new ModelTorchState();

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
		doRenderTorchState((EntityTorchState)entity, d0, d1, d2, f, f1);
	}

	private void doRenderTorchState(EntityTorchState entity, double dx, double dy, double dz, float f0, float f1)
	{
		//System.out.println("Rendering");

		for(int i = -8; i <= 8; i++)
		{
			for(int j = -8; j <= 8; j++)
			{
				doRenderTorchStateWith(entity, dx+i, dy, dz+j, f0, f1);
			}
		}
	}

	private static double offset = (1D-Math.sqrt(2D)/2D)/2D;

	private void doRenderTorchStateWith(EntityTorchState entity, double dx, double dy, double dz, float f0, float f1)
	{
		DataContainer con = DataContainer.fetchClient();
		int x = getFloored(con.player.posX + dx);
		int y = getFloored(con.player.posY + dy);
		int z = getFloored(con.player.posZ + dz);

		Tessellator t = Tessellator.instance;

		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);

        model.render();

        GL11.glPopMatrix();



		//t.draw();

		//bindTexture(getEntityTexture(entity));

		/*t.disableColor();

		//0D
		t.startDrawingQuads();
		t.addVertexWithUV(x+1D, y+ 0D, z+ 1D, 1F, 1F); // Right Down
		t.addVertexWithUV(x+1D, y+.5D, z+.5D, 1F, 0F); // Right Up
		t.addVertexWithUV(x+0D, y+.5D, z+.5D, 0F, 0F); // Left  Up
		t.addVertexWithUV(x+0D, y+ 0D, z+ 1D, 0F, 1F); // Left  Down
		t.draw();

		System.out.println("Write");

		t.startDrawingQuads();
		t.addVertexWithUV(x+1, y+.01, z+1, 1F, 1F);
		t.addVertexWithUV(x+1, y+.01, z+0, 1F, 0F);
		t.addVertexWithUV(x+0, y+.01, z+0, 0F, 0F);
		t.addVertexWithUV(x+0, y+.01, z+1, 0F, 1F);
		t.draw();*/

		//t.startDrawingQuads();

		//bindTexture(TextureMap.locationBlocksTexture);
	}

	private int getFloored(double d)
	{
		return (int)Math.floor(d);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}
