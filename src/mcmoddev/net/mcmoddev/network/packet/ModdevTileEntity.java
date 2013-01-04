package net.mcmoddev.network.packet;

import java.util.HashMap;
import java.util.Map;

import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ModdevTileEntity extends BaseModdevPacket
{
	//private static final int FLAG_ENABLED = 1;
	private static final int FLAG_METAB = 2;
	private static final int FLAG_METAT = 4;
	//private static final int B0000_1000 = 8;

	//private static final int B0001_0000 = 16;
	//private static final int B0010_0000 = 32;
	//private static final int B0100_0000 = 64;
	//private static final int FLAG_EXTRA = 128;

	private static final int PROP_METAB = 0;
	private static final int PROP_METAT = 1;

	private int _x;
	private int _y;
	private int _z;

	private Map<Integer, Object> _tileProp = new HashMap<Integer, Object>();

	@Override
	public void readImpl()
	{
		_x = readD();
		_y = readD();
		_z = readD();

		int flags = readC() &0xFF;
		if (flags != 0)
		{
			for (int i = 1; i < 8; i++)
			{
				int curFlag = flags & (1 << i);

				switch (curFlag)
				{
				case FLAG_METAB:
					_tileProp.put(PROP_METAB, readH());
					break;
				case FLAG_METAT:
					_tileProp.put(PROP_METAT, readH());
					break;
				}
			}
		}
	}

	@Override
	public void runImpl()
	{
		for (int prop : _tileProp.keySet())
		{
			switch (prop)
			{
				case PROP_METAB:
				{
					World world = ((EntityPlayer)getPlayer()).worldObj;
	
					TileEntity tileEntity = world.getBlockTileEntity(_x, _y, _z);
	
					if (tileEntity != null)
					{
						if (tileEntity instanceof TileEntityBlockExtended)
						{
							((TileEntityBlockExtended)tileEntity).setExtendedMeta((Integer)_tileProp.get(PROP_METAB));
							world.markBlockForRenderUpdate(_x, _y, _z);
						}
					}
					break;
				}
			}
		}
	}
}
