package net.watersfall.random.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.item.DaggerItem;
import net.watersfall.tools.item.ForgedSwordItem;

public class RandomItems
{
	public static BlockItem DRAWBRIDGE;
	public static BlockItem CHARCOAL_BLOCK;

	public static Item COAL_PIECE;
	public static Item CHARCOAL_PIECE;

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
		CHARCOAL_BLOCK = register(RandomBlocks.CHARCOAL_BLOCK, ItemGroup.BUILDING_BLOCKS);
		COAL_PIECE = register("coal_piece", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(64)));
		CHARCOAL_PIECE = register("charcoal_piece", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(64)));
		WOOD_DAGGER = register("wood_dagger", ToolMaterials.WOOD);
		STONE_DAGGER = register("stone_dagger", ToolMaterials.STONE);
		IRON_DAGGER = register("iron_dagger", ToolMaterials.IRON);
		GOLD_DAGGER = register("gold_dagger", ToolMaterials.GOLD);
		DIAMOND_DAGGER = register("diamond_dagger", ToolMaterials.DIAMOND);
		NETHERITE_DAGGER = register("netherite_dagger", ToolMaterials.NETHERITE);

		FuelRegistry.INSTANCE.add(COAL_PIECE, 200);
		FuelRegistry.INSTANCE.add(CHARCOAL_PIECE, 200);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK, 16000);
	}
}
