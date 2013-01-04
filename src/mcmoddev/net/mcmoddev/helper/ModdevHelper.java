package net.mcmoddev.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.item.ItemBlockExtended;
import net.mcmoddev.model.IModelBlockExtended;
import net.mcmoddev.proxy.BaseModdevProxy;
import net.mcmoddev.tileentity.TileEntityBlockExtended;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class ModdevHelper
{
	private static final List<BlockExtended> _initBlock = new ArrayList<BlockExtended>();
	private static final List<String> _initModel = new ArrayList<String>();
	
	private static final Map<String, IModelBlockExtended> _modelBlock = new HashMap<String, IModelBlockExtended>();
	
	private static final BaseModdevProxy _proxy = ModdevHelper.getProxy();
	
	public static BlockExtended createBlock(int blockId)
	{
		BlockExtended block = new BlockExtended(blockId);
		_initBlock.add(block);
		return block;
	}
	
	public static void onInit()
	{
		for (BlockExtended block : _initBlock)
		{
			ModLoader.registerBlock(block, ItemBlockExtended.class);
			_proxy.registerTileEntity(TileEntityBlockExtended.class, "BlockExtended"+block.blockID, "net.mcmoddev.client.renderer.RenderBlockExtended");
			_proxy.registerItemRenderer(block.blockID, "net.mcmoddev.client.renderer.RenderItemBlockExtended");

			for (BlockMeta meta : block.getBlockMetaList())
			{
				LanguageRegistry.instance();
				LanguageRegistry.addName(new ItemStack(block.blockID, 1, meta.getMetaValue()), meta.getDefaultName());
			}
		}
		
		for (String model : _initModel)
		{
			IModelBlockExtended modelBlock = _proxy.registerBlockModel(model);
			if (modelBlock == null)
				return;
			
			_modelBlock.put(modelBlock.getModelName(), modelBlock);
		}
	}
	
	public static void registerBlockModel(String className)
	{
		_initModel.add(className);
	}
	
	public static IModelBlockExtended getBlockModel(String modelName)
	{
		return _modelBlock.get(modelName);
	}
	
	private static BaseModdevProxy getProxy()
	{
		switch (FMLCommonHandler.instance().getSide())
		{
			case CLIENT:
				try
				{
					return (BaseModdevProxy)Class.forName("net.mcmoddev.proxy.ClientModdevProxy").newInstance();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new LoaderException();
				}
			case SERVER:
				try
				{
					return (BaseModdevProxy)Class.forName("net.mcmoddev.proxy.ServerModdevProxy").newInstance();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new LoaderException();
				}
			case BUKKIT:
				return null;
		}
		return null;
	}
}
