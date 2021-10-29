package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.block.DrawbridgeBlock;
import net.watersfall.random.block.MossyCobblestonePressurePlate;
import net.watersfall.random.block.ObsidianPressurePlate;
import net.watersfall.random.block.TinyChestBlock;

public class RandomBlocks
{
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
