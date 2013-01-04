package net.mcmoddev.util;

import java.lang.reflect.Field;

import net.minecraft.world.ChunkCache;

public class ReflectUtil
{
	public static Field CHUNKCACHE_WORLDOBJ;
	
	private static void initReflections()
	{
		CHUNKCACHE_WORLDOBJ = setFieldAccessible(ChunkCache.class, "worldObj", "e");
	}
	
	private static Field setFieldAccessible(Class<?> classFile, String fieldName, String obfFieldName)
	{
		Field field = null;
		
		try
		{
			field = classFile.getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException obfuscated)
		{
			try
			{
				field = classFile.getDeclaredField(obfFieldName);
			}
			catch (NoSuchFieldException unsupported)
			{
				unsupported.printStackTrace();
			}
		}
		field.setAccessible(true);
		return field;
	}
	
	static
	{
		initReflections();
	}
}
