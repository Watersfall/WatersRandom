package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.block.DrawbridgeBlock;

public class RandomBlocks
{
	public static DrawbridgeBlock DRAWBRIDGE;

	private static <T extends Block> T register(String name, T block)
	{
		return Registry.register(Registry.BLOCK, WatersRandom.getId(name), block);
	}

	public static void register()
	{
		DRAWBRIDGE = register("drawbridge", new DrawbridgeBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().dynamicBounds()));
	}
}
