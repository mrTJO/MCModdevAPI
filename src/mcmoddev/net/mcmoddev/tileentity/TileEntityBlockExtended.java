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
package net.mcmoddev.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.mcmoddev.common.ModdevMod;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlockExtended extends TileEntity
{
	private int _extendedMeta = -1;

	public TileEntityBlockExtended()
	{

	}

	public TileEntityBlockExtended(Block block, int extendedMeta)
	{
		blockType = block;
		_extendedMeta = extendedMeta;
	}

	public void setExtendedMeta(int extendedMeta)
	{
		_extendedMeta = extendedMeta;
	}

	public int getExtendedMeta()
	{
		return _extendedMeta;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		if (tag.hasKey("ExtendedMeta"))
			_extendedMeta = tag.getInteger("ExtendedMeta");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("ExtendedMeta", _extendedMeta);
	}

	@Override
	public void updateEntity()
	{
		if (_extendedMeta == -1 && worldObj != null)
		{
			_extendedMeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 0);
		}
	}

	@Override
	public void validate()
	{
		super.validate();
	}

	@Override
	public Packet getDescriptionPacket()
	{
		if (_extendedMeta == -1)
			return null;

		try
		{
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			DataOutputStream data = new DataOutputStream(bytes);

			data.writeByte(0x00);

			data.writeInt(xCoord);
			data.writeInt(yCoord);
			data.writeInt(zCoord);

			data.writeByte(3);

			data.writeShort(_extendedMeta);

			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = ModdevMod.PACKET_CHANNEL;
			packet.data = bytes.toByteArray();
			packet.length = packet.data.length;
			return packet;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
