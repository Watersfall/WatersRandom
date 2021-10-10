package net.watersfall.random.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.item.DaggerItem;
import net.watersfall.tools.item.ForgedSwordItem;

public class RandomItems
{
	public static BlockItem DRAWBRIDGE;
	public static SwordItem WOOD_DAGGER;
	public static SwordItem STONE_DAGGER;
	public static SwordItem IRON_DAGGER;
	public static SwordItem GOLD_DAGGER;
	public static SwordItem DIAMOND_DAGGER;
	public static SwordItem NETHERITE_DAGGER;

	private static BlockItem register(Block block, ItemGroup group)
	{
		return Registry.register(Registry.ITEM, Registry.BLOCK.getId(block), new BlockItem(block, new FabricItemSettings().group(group)));
	}

	private static <T extends Item> T register(String id, T item)
	{
		return Registry.register(Registry.ITEM, WatersRandom.getId(id), item);
	}

	private static SwordItem register(String id, ToolMaterial material)
	{
		return register(id, new DaggerItem(material, 0));
	}

	public static void register()
	{
		DRAWBRIDGE = register(RandomBlocks.DRAWBRIDGE, ItemGroup.REDSTONE);
		WOOD_DAGGER = register("wood_dagger", ToolMaterials.WOOD);
		STONE_DAGGER = register("stone_dagger", ToolMaterials.STONE);
		IRON_DAGGER = register("iron_dagger", ToolMaterials.IRON);
		GOLD_DAGGER = register("gold_dagger", ToolMaterials.GOLD);
		DIAMOND_DAGGER = register("diamond_dagger", ToolMaterials.DIAMOND);
		NETHERITE_DAGGER = register("netherite_dagger", ToolMaterials.NETHERITE);
	}
}
