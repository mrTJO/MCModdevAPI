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

import java.util.Random;

public class BlockDrop
{
	private static final Random _random = new Random();

	private final int _dropCategory;
	private final Object _dropItem;
	private int _dropChances = 1000000;

	private int _minQuantity = 0;
	private int _maxQuantity = 0;

	private boolean _isDropQtyRandom = false;

	private BlockDrop(int dropCategory, Object dropItem)
	{
		_dropCategory = dropCategory;
		_dropItem = dropItem;
	}

	public static BlockDrop createDrop(int dropCategory, Object dropItem)
	{
		return new BlockDrop(dropCategory, dropItem);
	}

	public int getCategory()
	{
		return _dropCategory;
	}

	public Object getDrop()
	{
		return _dropItem;
	}

	public BlockDrop setDropQuantity(int quantity)
	{
		_minQuantity = quantity;
		_isDropQtyRandom = false;
		return this;
	}

	public BlockDrop setDropQuantity(int minQuantity, int maxQuantity)
	{
		_minQuantity = minQuantity;
		_maxQuantity = maxQuantity;
		_isDropQtyRandom = true;
		return this;
	}

	public int getDropQuantity()
	{
		return _isDropQtyRandom ? _minQuantity+(int)(_random.nextDouble()*((_maxQuantity-_minQuantity)+1)) : _minQuantity;
	}

	public BlockDrop setDropChances(int chancesPerMillion)
	{
		_dropChances = chancesPerMillion;
		return this;
	}

	public int getDropChances()
	{
		return _dropChances;
	}
}
