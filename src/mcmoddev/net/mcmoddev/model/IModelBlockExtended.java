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
package net.mcmoddev.model;

import net.mcmoddev.client.renderer.RenderBlockExtended;
import net.mcmoddev.tileentity.TileEntityBlockExtended;

public interface IModelBlockExtended
{
	public void renderModel(RenderBlockExtended renderer, TileEntityBlockExtended tileEntity, float f);

	public String getModelName();
}
