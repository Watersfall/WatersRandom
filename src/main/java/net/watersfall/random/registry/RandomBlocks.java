package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.block.*;

public class RandomBlocks
{
	public static WatersMelonBlock AIR_MELON;
	public static WatersMelonBlock FIRE_MELON;
	public static WatersMelonBlock EARTH_MELON;
	public static WatersStemBlock AIR_MELON_STEM;
	public static WatersStemBlock FIRE_MELON_STEM;
	public static WatersStemBlock EARTH_MELON_STEM;
	public static WatersAttachedStemBlock AIR_MELON_ATTACHED_STEM;
	public static WatersAttachedStemBlock FIRE_MELON_ATTACHED_STEM;
	public static WatersAttachedStemBlock EARTH_MELON_ATTACHED_STEM;

	public static DrawbridgeBlock DRAWBRIDGE;
	public static Block CHARCOAL_BLOCK;
	public static MossyCobblestonePressurePlate MOSSY_PRESSURE_PLATE;
	public static MossyCobblestonePressurePlate MOSSY_PRESSURE_PLATE_SILENT;
	public static MossyCobblestonePressurePlate MOSSY_PRESSURE_PLATE_INVISIBLE;
	public static MossyCobblestonePressurePlate MOSSY_PRESSURE_PLATE_SILENT_INVISIBLE;
	public static ObsidianPressurePlate OBSIDIAN_PRESSURE_PLATE;
	public static ObsidianPressurePlate OBSIDIAN_PRESSURE_PLATE_SILENT;
	public static ObsidianPressurePlate OBSIDIAN_PRESSURE_PLATE_INVISIBLE;
	public static ObsidianPressurePlate OBSIDIAN_PRESSURE_PLATE_SILENT_INVISIBLE;
	public static TinyChestBlock TINY_CHEST;


	private static <T extends Block> T register(String name, T block)
	{
		return Registry.register(Registry.BLOCK, WatersRandom.getId(name), block);
	}

	public static void register()
	{
		AIR_MELON = register("air_melon_block", new WatersMelonBlock(() -> AIR_MELON_STEM, () -> AIR_MELON_ATTACHED_STEM, FabricBlockSettings.copyOf(Blocks.MELON)));
		FIRE_MELON = register("fire_melon_block", new FireMelonBlock(() -> FIRE_MELON_STEM, () -> FIRE_MELON_ATTACHED_STEM, FabricBlockSettings.copyOf(Blocks.MELON)));
		EARTH_MELON = register("earth_melon_block", new WatersMelonBlock(() -> EARTH_MELON_STEM, () -> EARTH_MELON_ATTACHED_STEM, FabricBlockSettings.copyOf(Blocks.MELON)));
		AIR_MELON_STEM = register("air_melon_stem", new WatersStemBlock(AIR_MELON, () -> RandomItems.AIR_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.MELON_STEM)));
		FIRE_MELON_STEM = register("fire_melon_stem", new WatersStemBlock(FIRE_MELON, () -> RandomItems.FIRE_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.MELON_STEM)));
		EARTH_MELON_STEM = register("earth_melon_stem", new WatersStemBlock(EARTH_MELON, () -> RandomItems.EARTH_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.MELON_STEM)));
		AIR_MELON_ATTACHED_STEM = register("air_melon_attached_stem", new WatersAttachedStemBlock(AIR_MELON, () -> RandomItems.AIR_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.ATTACHED_MELON_STEM)));
		FIRE_MELON_ATTACHED_STEM = register("fire_melon_attached_stem", new WatersAttachedStemBlock(FIRE_MELON, () -> RandomItems.FIRE_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.ATTACHED_MELON_STEM)));
		EARTH_MELON_ATTACHED_STEM = register("earth_melon_attached_stem", new WatersAttachedStemBlock(EARTH_MELON, () -> RandomItems.EARTH_MELON_BLOCK, FabricBlockSettings.copyOf(Blocks.ATTACHED_MELON_STEM)));
		DRAWBRIDGE = register("drawbridge", new DrawbridgeBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().dynamicBounds().requiresTool()));
		CHARCOAL_BLOCK = register("charcoal_block", new Block(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK)));
		MOSSY_PRESSURE_PLATE = register("mossy_pressure_plate", new MossyCobblestonePressurePlate(false, true, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		MOSSY_PRESSURE_PLATE_SILENT = register("mossy_pressure_plate_silent", new MossyCobblestonePressurePlate(true, true, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		MOSSY_PRESSURE_PLATE_INVISIBLE = register("mossy_pressure_plate_invisible", new MossyCobblestonePressurePlate(false, false, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		MOSSY_PRESSURE_PLATE_SILENT_INVISIBLE = register("mossy_pressure_plate_silent_invisible", new MossyCobblestonePressurePlate(true, false, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		OBSIDIAN_PRESSURE_PLATE = register("obsidian_pressure_plate", new ObsidianPressurePlate(false, true, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		OBSIDIAN_PRESSURE_PLATE_SILENT = register("obsidian_pressure_plate_silent", new ObsidianPressurePlate(true, true, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		OBSIDIAN_PRESSURE_PLATE_INVISIBLE = register("obsidian_pressure_plate_invisible", new ObsidianPressurePlate(false, false, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		OBSIDIAN_PRESSURE_PLATE_SILENT_INVISIBLE = register("obsidian_pressure_plate_silent_invisible", new ObsidianPressurePlate(true, false, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE)));
		TINY_CHEST = register("tiny_chest", new TinyChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
	}
}
