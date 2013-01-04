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
public class ModelBlockExtendedDoubleSlab extends ModelBase implements IModelBlockExtended
{
	ModelRenderer _slabB;
	ModelRenderer _slabT;

	public ModelBlockExtendedDoubleSlab()
	{
		textureWidth = 64;
		textureHeight = 32;

		_slabB = new ModelRenderer(this, 0, 0);
		_slabB.addBox(-8F, -4F, -8F, 16, 8, 16);
		_slabB.setRotationPoint(0F, 20F, 0F);
		_slabB.setTextureSize(64, 32);

		_slabT = new ModelRenderer(this, 0, 0);
		_slabT.addBox(-8F, -4F, -8F, 16, 8, 16);
		_slabT.setRotationPoint(0F, 12F, 0F);
		_slabT.setTextureSize(64, 32);
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
		_slabB.render(f);
		_slabT.render(f);
	}

	@Override
	public String getModelName()
	{
		return "doubleSlab";
	}
}
