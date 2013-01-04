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
package net.mcmoddev.common;

import net.mcmoddev.helper.ModdevHelper;
import net.mcmoddev.network.ModdevConnectionHandler;
import net.mcmoddev.network.ModdevPacketHandler;
import net.mcmoddev.proxy.BaseModdevProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="ModdevCore", name="MCModDev - Core", version="1.4.7.0002")
@NetworkMod(clientSideRequired=true, serverSideRequired=true, channels=ModdevMod.PACKET_CHANNEL, connectionHandler=ModdevConnectionHandler.class, packetHandler=ModdevPacketHandler.class)
public class ModdevMod
{
	public static final String PACKET_CHANNEL = "MDEVNET";

	@SidedProxy(clientSide="net.mcmoddev.proxy.ClientModdevProxy", serverSide="net.mcmoddev.proxy.ServerModdevProxy")
	public static BaseModdevProxy SIDE_PROXY;

	@Init
	public void onInit(FMLInitializationEvent evt)
	{
		ModdevHelper.registerBlockModel("net.mcmoddev.client.model.ModelBlockExtendedClassic");
		ModdevHelper.registerBlockModel("net.mcmoddev.client.model.ModelBlockExtendedCrop");
		ModdevHelper.registerBlockModel("net.mcmoddev.client.model.ModelBlockExtendedDoubleSlab");
		ModdevHelper.registerBlockModel("net.mcmoddev.client.model.ModelBlockExtendedFlower");
		ModdevHelper.registerBlockModel("net.mcmoddev.client.model.ModelBlockExtendedSlab");
		
		ModdevHelper.onInit();
	}
}
