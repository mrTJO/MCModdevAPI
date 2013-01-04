package net.mcmoddev.test;

import cpw.mods.fml.common.Mod;
import net.mcmoddev.block.BlockExtended;
import net.mcmoddev.helper.BlockMeta;
import net.mcmoddev.helper.ModdevHelper;

@Mod(modid = "MCMTest", name = "MCModdev Test", version = "1.4.7.0")
public class SampleMod
{
	public static final BlockExtended SAMPLE_BLOCK = ModdevHelper.createBlock(500)
			.addMeta(BlockMeta.createMeta(0).setTextureFile("/res/test.png").setName("test").setDefaultName("Test Block"));
}
