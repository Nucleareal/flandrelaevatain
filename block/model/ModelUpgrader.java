package flandrelaevatain.block.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUpgrader extends ModelBase
{
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;

	public ModelUpgrader()
	{
		textureWidth = 256;
		textureHeight = 256;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 16, 16);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(256, 256);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 64, 0);
		Shape2.addBox(1F, 1F, 1F, 16, 16, 16);
		Shape2.setRotationPoint(0F, 0F, 0F);
		Shape2.setTextureSize(256, 256);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private static final float base = 0.0625F;
	private static final float in = base*(14F/16F);

	public void renderAll()
	{
		Shape1.render(base);
		Shape2.render(in);
	}

	/*public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5);
	}*/

}
