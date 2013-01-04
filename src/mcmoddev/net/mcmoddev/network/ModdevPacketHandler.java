package net.mcmoddev.network;

import net.mcmoddev.network.packet.BaseModdevPacket;
import net.mcmoddev.network.packet.ModdevTileEntity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ModdevPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);

		int opcode = data.readByte() &0xFF;

		BaseModdevPacket moddevPacket = null;

		switch (opcode)
		{
		case 0x00:
			moddevPacket = new ModdevTileEntity();
			break;
		}

		if (moddevPacket != null)
		{
			moddevPacket.parsePacket(player, data);
		}
	}
}
