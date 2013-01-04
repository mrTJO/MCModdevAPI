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
package net.mcmoddev.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.model.IModelBlockExtended;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMeta
{
	private final int _metaValue;
	private BlockExtended _parentBlock;
	
	private int _lightOpacity = 0;
	private int _lightValue = 0;
	
	private float _hardness;
	private float _resistance;
	
	private boolean _tickRandomly;
	
	private double _minX = 0.0D;
	private double _minY = 0.0D;
	private double _minZ = 0.0D;
	
	private double _maxX = 1.0D;
	private double _maxY = 1.0D;
	private double _maxZ = 1.0D;
	
	public StepSound _stepSound = Block.soundPowderFootstep;
	
	private Material _material = Material.ground;

	private String _name = "moddev";
	private String _textureFile = "/res/notfound.png";

	private String _defaultName = "Unknown Moddev Block";

	private final Map<Integer, List<BlockDrop>> _dropList = new HashMap<Integer, List<BlockDrop>>();

	private String _modelBlock = "classic";

	private BlockMeta(int metaValue)
	{
		_metaValue = metaValue;
	}

	/**
	 * Create an Extended Block Sub-Block
	 * 
	 * @param metaValue Sub-Block MetaData
	 * @return Sub-Block instance
	 */
	public static BlockMeta createMeta(int metaValue)
	{
		return new BlockMeta(metaValue);
	}

	/**
	 * Return Sub-Block MetaData Value
	 * 
	 * @return
	 */
	public int getMetaValue()
	{
		return _metaValue;
	}

	public void setParentBlock(BlockExtended parentBlock)
	{
		_parentBlock = parentBlock;
	}

	public BlockExtended getParentBlock()
	{
		return _parentBlock;
	}
	
	// Block Drops

	public BlockMeta addDrop(BlockDrop drop)
	{
		if (!_dropList.containsKey(drop.getCategory()) || _dropList.get(drop.getCategory()) == null)
		{
			List<BlockDrop> dropList = new ArrayList<BlockDrop>();
			dropList.add(drop);

			_dropList.put(drop.getCategory(), dropList);
			return this;
		}

		_dropList.get(drop.getCategory()).add(drop);
		return this;
	}

	public Map<Integer, List<BlockDrop>> getDropList()
	{
		return _dropList;
	}

	public BlockMeta addDropSelf()
	{
		return addDrop(BlockDrop.createDrop(-1, this).setDropQuantity(1));
	}

	public BlockMeta addDropSelf(int dropCategory, int quantity)
	{
		return addDrop(BlockDrop.createDrop(dropCategory, this).setDropQuantity(quantity));
	}

	public BlockMeta addDropSelf(int dropCategory, int minQuantity, int maxQuantity)
	{
		return addDrop(BlockDrop.createDrop(dropCategory, this).setDropQuantity(minQuantity, maxQuantity));
	}

	public BlockMeta setLightOpacity(int opacity)
	{
		_lightOpacity = opacity;
		return this;
	}

	public int getLightOpacity()
	{
		return _lightOpacity;
	}

	public BlockMeta setLightValue(float value)
	{
		_lightValue = (int)(15.0F * value);
		return this;
	}

	public int getLightValue()
	{
		return _lightValue;
	}

	public BlockMeta setResistance(float resistance)
	{
		_resistance = resistance * 3.0F;
		return this;
	}

	public float getResistance()
	{
		return _resistance;
	}

	public BlockMeta setHardness(float hardness)
	{
		_hardness = hardness;

		if (_resistance < hardness * 5.0F)
			_resistance = hardness * 5.0F;

		return this;
	}

	public BlockMeta setUnbreakable()
	{
		setHardness(-1.0F);
		return this;
	}

	public float getHardness()
	{
		return _hardness;
	}

	public BlockMeta setTickRandomly(boolean tickRandomly)
	{
		_tickRandomly = tickRandomly;
		return this;
	}

	public boolean getTickRandomly()
	{
		return _tickRandomly;
	}

	public BlockMeta setName(String name)
	{
		_name = name;
		return this;
	}

	public String getName()
	{
		return _name;
	}

	public BlockMeta setTextureFile(String textureFile)
	{
		_textureFile = textureFile;
		return this;
	}

	public String getTextureFile()
	{
		return _textureFile;
	}

	public BlockMeta setDefaultName(String defaultName)
	{
		_defaultName = defaultName;
		return this;
	}

	public String getDefaultName()
	{
		return _defaultName;
	}
	
	public BlockMeta setModelBlock(String modelBlock)
	{
		_modelBlock = modelBlock;
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public IModelBlockExtended getModelBlock()
	{
		return ModdevHelper.getBlockModel(_modelBlock);
	}
	
	public final BlockMeta setBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
    {
        _minX = (double)minX;
        _minY = (double)minY;
        _minZ = (double)minZ;
        _maxX = (double)maxX;
        _maxY = (double)maxY;
        _maxZ = (double)maxZ;
        return this;
    }
	
	/**
     * returns the block bounderies minX value
     */
    public final double getBoundsMinX()
    {
        return _minX;
    }

    /**
     * returns the block bounderies maxX value
     */
    public final double getBoundsMaxX()
    {
        return _maxX;
    }

    /**
     * returns the block bounderies minY value
     */
    public final double getBoundsMinY()
    {
        return _minY;
    }

    /**
     * returns the block bounderies maxY value
     */
    public final double getBoundsMaxY()
    {
        return _maxY;
    }

    /**
     * returns the block bounderies minZ value
     */
    public final double getBoundsMinZ()
    {
        return _minZ;
    }

    /**
     * returns the block bounderies maxZ value
     */
    public final double getBoundsMaxZ()
    {
        return _maxZ;
    }
    
    public BlockMeta setMaterial(Material material)
    {
    	_material = material;
    	return this;
    }
    
    public Material getMaterial()
    {
    	return _material;
    }
    
    public BlockMeta setStepSound(StepSound stepSound)
    {
    	_stepSound = stepSound;
    	return this;
    }
    
    public StepSound getStepSound()
    {
    	return _stepSound;
    }
}
