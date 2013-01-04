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
package net.mcmoddev.proxy;

import net.mcmoddev.model.IModelBlockExtended;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientModdevProxy extends BaseModdevProxy
{
	@Override
	public void preloadTexture(String texture)
	{
		MinecraftForgeClient.preloadTexture(texture);
	}

	@Override
	public void registerTileEntity(Class<? extends TileEntity> tileEntity, String tileName, String tileRenderer)
	{
		try
		{
			TileEntitySpecialRenderer spRender = (TileEntitySpecialRenderer)Class.forName(tileRenderer).newInstance();

			ClientRegistry.registerTileEntity(tileEntity, tileName, spRender);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void registerItemRenderer(int blockId, String itemRenderer)
	{
		try
		{
			IItemRenderer itRender = (IItemRenderer)Class.forName(itemRenderer).newInstance();

			MinecraftForgeClient.registerItemRenderer(blockId, itRender);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IModelBlockExtended registerBlockModel(String className)
	{
		try
		{
			return (IModelBlockExtended)Class.forName(className).newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
