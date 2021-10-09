package net.watersfall.random.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class RandomItems
{
	public static BlockItem DRAWBRIDGE;

	private static BlockItem register(Block block)
	{
		return Registry.register(Registry.ITEM, Registry.BLOCK.getId(block), new BlockItem(block, new FabricItemSettings()));
	}

	public static void register()
	{
		DRAWBRIDGE = register(RandomBlocks.DRAWBRIDGE);
	}
}
