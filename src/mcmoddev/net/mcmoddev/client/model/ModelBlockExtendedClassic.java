/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
public class ModelBlockExtendedClassic extends ModelBase implements IModelBlockExtended
{
	private final ModelRenderer _block;

	public ModelBlockExtendedClassic()
	{
		textureWidth = 64;
		textureHeight = 32;

		_block = new ModelRenderer(this, 0, 0);
		_block.addBox(-8F, -8F, -8F, 16, 16, 16);
		_block.setRotationPoint(0F, 16F, 0F);
		_block.setTextureSize(64, 32);
	}

	@Override
	public void renderModel(RenderBlockExtended renderer, TileEntityBlockExtended tileEntity, float f)
	{
		if (tileEntity.getExtendedMeta() == -1)
			renderer.bindTextureByName("/res/spinner/"+(1 + (System.currentTimeMillis() / 100 % 8))+".png");
		else
		{
			if (((BlockExtended)tileEntity.getBlockType()).getBlockMeta(tileEntity.getExtendedMeta()) != null)
				renderer.bindTextureByName(((BlockExtended)tileEntity.getBlockType()).getBlockMeta(tileEntity.getExtendedMeta()).getTextureFile());
			else
				renderer.bindTextureByName("/res/notfound.png");
		}
		_block.render(f);
	}

	@Override
	public String getModelName()
	{
		return "classic";
	}
}
