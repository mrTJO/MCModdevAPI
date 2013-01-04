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
package net.mcmoddev.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mcmoddev.helper.BlockDrop;
import net.mcmoddev.helper.BlockMeta;
import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.mcmoddev.util.ReflectUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExtended extends BlockContainer
{
	private final Map<Integer, BlockMeta> _metaMap = new HashMap<Integer, BlockMeta>();

	public BlockExtended(int blockId)
	{
		super(blockId, Material.ground);

		super.setCreativeTab(CreativeTabs.tabBlock);
	}

	public BlockExtended addMeta(BlockMeta meta)
	{
		if (meta == null)
			return this;

		if (_metaMap.containsKey(meta.getMetaValue()))
			return this;

		meta.setParentBlock(this);
		_metaMap.put(meta.getMetaValue(), meta);
		return this;
	}

	public Collection<BlockMeta> getBlockMetaList()
	{
		return _metaMap.values();
	}

	public BlockMeta getBlockMeta(int damageValue)
	{
		return _metaMap.get(damageValue);
	}

	private BlockMeta getBlockMeta(IBlockAccess worldAccess, int x, int y, int z)
	{
		World world = getWorldByAccess(worldAccess);
		if (world == null)
			return null;

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null || !(te instanceof TileEntityBlockExtended))
		{
			te = createNewTileEntity(world);
			((TileEntityBlockExtended)te).setExtendedMeta(world.getBlockMetadata(x, y, z));
			world.setBlockTileEntity(x, y, z, te);
		}

		int meta = ((TileEntityBlockExtended)te).getExtendedMeta();

		if (_metaMap.containsKey(meta))
			return _metaMap.get(meta);

		return null;
	}

	private World getWorldByAccess(IBlockAccess worldAccess)
	{
		if (worldAccess instanceof World)
			return (World)worldAccess;

		try
		{
			if (worldAccess instanceof ChunkCache)
				return (World)ReflectUtil.CHUNKCACHE_WORLDOBJ.get((ChunkCache)worldAccess);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return super.getRenderBlockPass();
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	/**
	 * Blocks with this attribute will not notify all near blocks when it's metadata change. The default behavior is
	 * always notify every neighbor block when anything changes.
	 */
	@Override
	public BlockExtended setRequiresSelfNotify()
	{
		return (BlockExtended)super.setRequiresSelfNotify();
	}

	/**
	 * Sets the footstep sound for the block. Returns the object for convenience in constructing.
	 */
	@Override
	public BlockExtended setStepSound(StepSound sound)
	{
		return (BlockExtended)super.setStepSound(sound);
	}

	/**
	 * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
	 */
	@Override
	@Deprecated
	public BlockExtended setLightOpacity(int opacity)
	{
		return this;
	}

	/**
	 * Location aware and overrideable version of the lightOpacity array,
	 * return the number to subtract from the light value when it passes through this block.
	 * 
	 * This is not guaranteed to have the tile entity in place before this is called, so it is
	 * Recommended that you have your tile entity call relight after being placed if you
	 * rely on it for light info.
	 * 
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z position
	 * @return The amount of light to block, 0 for air, 255 for fully opaque.
	 */
	@Override
	public int getLightOpacity(World world, int x, int y, int z)
	{
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return blockMeta.getLightOpacity();
		return super.getLightOpacity(world, x, y, z);
	}

	/**
	 * NOT USED in BlockExtended, use BlockMeta setLightValue
	 */
	@Override
	@Deprecated
	public BlockExtended setLightValue(float light)
	{
		return this;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return blockMeta.getLightValue();
		return super.getLightValue(world, x, y, z);
	}

	/**
	 * NOT USED in BlockExtended, use BlockMeta setResistance
	 */
	@Override
	public BlockExtended setResistance(float resistance)
	{
		return this;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return blockMeta.getHardness();
		return super.getBlockHardness(world, x, y, z);
	}

	/**
	 * Disable statistics for the block, the block will no count for mined or placed.
	 */
	@Override
	public BlockExtended disableStats()
	{
		return (BlockExtended)super.disableStats();
	}

	/**
	 * Sets the CreativeTab to display this block on.
	 */
	@Override
	@Deprecated
	public BlockExtended setCreativeTab(CreativeTabs creativeTab)
	{
		return (BlockExtended)super.setCreativeTab(creativeTab);
	}

	/**
	 * NOT USED in BlockExtended, use BlockMeta setTextureFile
	 * 
	 * @param texture The texture file
	 */
	@Override
	@Deprecated
	public BlockExtended setTextureFile(String texture)
	{
		return this;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int meta)
	{
		TileEntityBlockExtended tileEntity = (TileEntityBlockExtended)world.getBlockTileEntity(x, y, z);
		if (tileEntity != null)
		{
			int extMeta = tileEntity.getExtendedMeta() != -1 ? tileEntity.getExtendedMeta() : meta;

			if (extMeta == -1)
				return;

			BlockMeta blockMeta = _metaMap.get(extMeta);

			if (blockMeta == null)
				return;

			for (List<BlockDrop> list : blockMeta.getDropList().values())
			{
				List<BlockDrop> temp = new ArrayList<BlockDrop>();
				temp.addAll(list);

				BlockDrop drop = null;

				while (temp.size() > 0)
				{
					int el = world.rand.nextInt(list.size());

					BlockDrop tmpDrop = list.get(el);

					if (world.rand.nextInt(1000000) <= tmpDrop.getDropChances())
					{
						drop = tmpDrop;
						break;
					}
					temp.remove(el);
				}

				if (drop == null)
					return;

				if (drop.getDrop() instanceof ItemStack)
				{
					ItemStack is = ((ItemStack) drop.getDrop());
					for (int i = 0; i < drop.getDropQuantity(); i++)
					{
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, is));
					}
				}

				else if (drop.getDrop() instanceof Block)
				{
					for (int i = 0; i < drop.getDropQuantity(); i++)
					{
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack((Block)drop.getDrop())));
					}
				}
				else if (drop.getDrop() instanceof Item)
				{
					for (int i = 0; i < drop.getDropQuantity(); i++)
					{
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack((Item)drop.getDrop())));
					}
				}
				else if (drop.getDrop() instanceof Integer)
				{
					for (int i = 0; i < drop.getDropQuantity(); i++)
					{
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack((Integer)drop.getDrop(), 1, 0)));
					}
				}
				else if (drop.getDrop() instanceof BlockMeta)
				{
					for (int i = 0; i < drop.getDropQuantity(); i++)
					{
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(((BlockMeta)drop.getDrop()).getParentBlock(), 1, ((BlockMeta)drop.getDrop()).getMetaValue())));
					}
				}
			}
			return;
		}
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return blockMeta.getResistance() / 5.0F;

		return super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 0;
	}

	@Override
	public TileEntityBlockExtended createNewTileEntity(World world)
	{
		return new TileEntityBlockExtended();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockId, CreativeTabs creativeTab, List creativeList)
	{
		for (int meta : _metaMap.keySet())
			creativeList.add(new ItemStack(blockId, 1, meta));
	}
	
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x + blockMeta.getBoundsMinX(), (double)y + blockMeta.getBoundsMinY(), (double)z + blockMeta.getBoundsMinZ(), (double)x + blockMeta.getBoundsMaxX(), (double)y + blockMeta.getBoundsMaxY(), (double)z + blockMeta.getBoundsMaxZ());
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		if (blockMeta != null)
			return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x + blockMeta.getBoundsMinX(), (double)y + blockMeta.getBoundsMinY(), (double)z + blockMeta.getBoundsMinZ(), (double)x + blockMeta.getBoundsMaxX(), (double)y + blockMeta.getBoundsMaxY(), (double)z + blockMeta.getBoundsMaxZ());
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)x + minX, (double)y + minY, (double)z + minZ, (double)x + maxX, (double)y + maxY, (double)z + maxZ);
    }
	
	@Override
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z)
	{
		BlockMeta blockMeta = getBlockMeta(blockAccess, x, y, z);
		if (blockMeta != null)
			return !blockMeta.getMaterial().blocksMovement();
		return super.getBlocksMovement(blockAccess, x, y, z);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		BlockMeta blockMeta = getBlockMeta(world, x, y, z);
		
		int blockId = world.getBlockId(x, y, z);
        return blockId == 0 || (blockMeta != null && blockMeta.getMaterial().isReplaceable() || blocksList[blockId].blockMaterial.isReplaceable());
	}
}
