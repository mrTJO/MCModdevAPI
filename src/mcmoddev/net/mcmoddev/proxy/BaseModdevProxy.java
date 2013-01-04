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
import net.minecraft.tileentity.TileEntity;

public abstract class BaseModdevProxy
{
	public abstract void preloadTexture(String texture);

	public abstract void registerTileEntity(Class<? extends TileEntity> tileEntity, String tileName, String tileRenderer);

	public abstract void registerItemRenderer(int blockId, String itemRenderer);

	public abstract IModelBlockExtended registerBlockModel(String className);
}
