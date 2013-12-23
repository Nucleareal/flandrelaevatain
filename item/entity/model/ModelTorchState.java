package flandrelaevatain.item.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTorchState extends ModelBase
{
	ModelRenderer model;

	public ModelTorchState()
	{
		textureWidth  = 256;
		textureHeight = 256;

		model = new ModelRenderer(this, 0, 0);
		model.addBox(-8F, 0F, 0F, 16, 16, 16);
		model.setRotationPoint(0, 12F, 0F);
		model.setTextureSize(256, 256);
		model.mirror = true;
		setRotation(model, -0.7853982F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		model.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void render()
	{
		model.render(0.0625F/2F);
	}
}
