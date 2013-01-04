package net.mcmoddev.network.packet;

import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.Player;

public abstract class BaseModdevPacket extends Thread
{
	protected final Logger _log = Logger.getLogger(BaseModdevPacket.class.getName());
	private Player _player;
	private ByteArrayDataInput _data;

	public abstract void readImpl();

	public abstract void runImpl();

	public void parsePacket(Player player, ByteArrayDataInput data)
	{
		_player = player;
		_data = data;
		try
		{
			readImpl();
			start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			runImpl();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected Player getPlayer()
	{
		return _player;
	}

	protected int readC()
	{
		return _data.readByte();
	}

	protected int readH()
	{
		return _data.readShort();
	}

	protected int readD()
	{
		return _data.readInt();
	}

	protected long readQ()
	{
		return _data.readLong();
	}

	protected String readS()
	{
		return _data.readUTF();
	}
}
