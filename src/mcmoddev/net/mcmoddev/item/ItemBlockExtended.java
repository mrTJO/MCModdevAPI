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
package net.mcmoddev.item;

import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.base.Joiner;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockExtended extends ItemBlock
{
	private static final Joiner _joiner = Joiner.on('.');

	private final Block _block;

	public ItemBlockExtended(int itemId)
	{
		super(itemId);
		setMaxDamage(0);
		setHasSubtypes(true);
		_block = Block.blocksList[itemId+256];
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer holder, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		if (world.isRemote)
		{
			return true;
		}
		
		int var11 = world.getBlockId(x, y, z);

		if (var11 == Block.snow.blockID)
        {
            side = 1;
        }
        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID
                && (Block.blocksList[var11] == null || !Block.blocksList[var11].isBlockReplaceable(world, x, y, z)))
        {
			switch (side)
			{
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					z--;
					break;
				case 3:
					z++;
					break;
				case 4:
					x--;
					break;
				case 5:
					x++;
					break;
			}
        }

		if (item.stackSize == 0)
            return false;
		
		if (!holder.canPlayerEdit(x, y, z, side, item))
            return false;
		
		if (y == 255)
            return false;
		
		if (!world.canPlaceEntityOnSide(_block.blockID, x, y, z, false, side, holder))
			return false;

		world.setBlockAndMetadataWithNotify(x, y, z, _block.blockID, 0);

		((TileEntityBlockExtended)world.getBlockTileEntity(x, y, z)).setExtendedMeta(item.getItemDamage());

		world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), _block.stepSound.getPlaceSound(), (_block.stepSound.getVolume() + 1.0F) / 2.0F, _block.stepSound.getPitch() * 0.8F);
		--item.stackSize;
		return true;
	}

	/**
	 * Return MetaData
	 */
	@Override
	public int getMetadata(int metaData)
	{
		return metaData;
	}

	/**
	 * Return Texture Id
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getIconFromDamage(int damageValue)
	{
		return _block.getBlockTextureFromSideAndMetadata(2, damageValue);
	}

	/**
	 * Return Item Name StringIdentifier
	 */
	@Override
	public String getItemNameIS(ItemStack itemStack)
	{
		return (_joiner.join("tile", ((BlockExtended)_block).getBlockMeta(itemStack.getItemDamage()).getName()));
	}
}
