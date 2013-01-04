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
public class ModelBlockExtendedCrop extends ModelBase implements IModelBlockExtended
{
	ModelRenderer _shapeF;
	ModelRenderer _shapeL;
	ModelRenderer _shapeB;
	ModelRenderer _shapeR;

	public ModelBlockExtendedCrop()
	{
		textureWidth = 64;
		textureHeight = 32;

		_shapeF = new ModelRenderer(this, 0, 0);
		_shapeF.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shapeF.setRotationPoint(0F, 16F, -4F);
		_shapeF.setTextureSize(64, 32);
		setRotation(_shapeF, 0F, 0F, 0F);

		_shapeL = new ModelRenderer(this, 32, 0);
		_shapeL.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shapeL.setRotationPoint(4F, 16F, 0F);
		_shapeL.setTextureSize(64, 32);
		setRotation(_shapeL, 0F, 1.570796F, 0F);

		_shapeB = new ModelRenderer(this, 0, 16);
		_shapeB.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shapeB.setRotationPoint(0F, 16F, 4F);
		_shapeB.setTextureSize(64, 32);
		setRotation(_shapeB, 0F, 3.141593F, 0F);

		_shapeR = new ModelRenderer(this, 32, 16);
		_shapeR.addBox(-8F, -8F, 0F, 16, 16, 0);
		_shapeR.setRotationPoint(-4F, 16F, 0F);
		_shapeR.setTextureSize(64, 32);
		setRotation(_shapeR, 0F, -1.570796F, 0F);
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
		_shapeF.render(f);
		_shapeL.render(f);
		_shapeB.render(f);
		_shapeR.render(f);
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
		return "crop";
	}
}
