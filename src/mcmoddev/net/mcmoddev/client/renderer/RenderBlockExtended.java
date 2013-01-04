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
package net.mcmoddev.client.renderer;

import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.client.model.ModelBlockExtendedClassic;
import net.mcmoddev.model.IModelBlockExtended;
import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockExtended extends TileEntitySpecialRenderer
{
	IModelBlockExtended _defaultModel = new ModelBlockExtendedClassic();
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f)
	{
		if (!(tileEntity instanceof TileEntityBlockExtended))
			return;

		TileEntityBlockExtended ext = (TileEntityBlockExtended)tileEntity;
		
		IModelBlockExtended model = _defaultModel;
		
		if (ext.getExtendedMeta() != -1 && ((BlockExtended)tileEntity.getBlockType()).getBlockMeta(ext.getExtendedMeta()) != null)
			model = ((BlockExtended)tileEntity.getBlockType()).getBlockMeta(ext.getExtendedMeta()).getModelBlock();

		if (ext.getWorldObj() != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			model.renderModel(this, ext, 0.0625F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		else
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.4F, (float)d2 + 0.5F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			model.renderModel(this, ext, 0.0625F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void bindTextureByName(String texture)
	{
		super.bindTextureByName(texture);
	}
}
