package net.mcmoddev.client.model;

import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.client.renderer.RenderBlockExtended;
import net.mcmoddev.model.IModelBlockExtended;
import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBlockExtendedFlower extends ModelBase implements IModelBlockExtended
{
	ModelRenderer _shape1;
	ModelRenderer _shape2;

	public ModelBlockExtendedFlower()
	{
		textureWidth = 64;
		textureHeight = 32;

		_shape1 = new ModelRenderer(this, 16, 0);
		_shape1.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shape1.setRotationPoint(0F, 16F, 0F);
		_shape1.setTextureSize(64, 32);
		setRotation(_shape1, 0F, 0.7853982F, 0F);

		_shape2 = new ModelRenderer(this, 16, 16);
		_shape2.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shape2.setRotationPoint(0F, 16F, 0F);
		_shape2.setTextureSize(64, 32);
		setRotation(_shape2, 0F, 2.356194F, 0F);
	}

	@Override
	public void renderModel(RenderBlockExtended renderer, TileEntityBlockExtended tileEntity, float f)
	{
		if (tileEntity.getExtendedMeta() != -1)
		{
			if (((BlockExtended)tileEntity.getBlockType()).getBlockMeta(tileEntity.getExtendedMeta()) != null)
				renderer.bindTextureByName(((BlockExtended)tileEntity.getBlockType()).getBlockMeta(tileEntity.getExtendedMeta()).getTextureFile());
			else
				renderer.bindTextureByName("/res/notfound.png");
		}
		_shape1.render(f);
		_shape2.render(f);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public String getModelName()
	{
		return "flower";
	}
}
