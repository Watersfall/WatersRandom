package net.watersfall.random.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.item.DaggerItem;
import net.watersfall.random.item.OpenTools;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.item.WoodArmorItem;
import net.watersfall.random.item.material.RandomArmorMaterials;
import net.watersfall.random.item.material.RandomToolMaterials;
import net.watersfall.tools.item.ForgedSwordItem;

import java.util.ArrayList;
import java.util.List;

public class RandomItems
{
	public static BlockItem DRAWBRIDGE;
	public static BlockItem CHARCOAL_BLOCK;
	
	public static BlockItem MOSSY_PRESSURE_PLATE;
	public static BlockItem MOSSY_PRESSURE_PLATE_SILENT;
	public static BlockItem MOSSY_PRESSURE_PLATE_INVISIBLE;
	public static BlockItem MOSSY_PRESSURE_PLATE_SILENT_INVISIBLE;
	public static BlockItem OBSIDIAN_PRESSURE_PLATE;
	public static BlockItem OBSIDIAN_PRESSURE_PLATE_SILENT;
	public static BlockItem OBSIDIAN_PRESSURE_PLATE_INVISIBLE;
	public static BlockItem OBSIDIAN_PRESSURE_PLATE_SILENT_INVISIBLE;

	public static BlockItem TINY_CHEST;

	public static Item COAL_PIECE;
	public static Item CHARCOAL_PIECE;

	public static SwordItem WOOD_DAGGER;
	public static SwordItem STONE_DAGGER;
	public static SwordItem IRON_DAGGER;
	public static SwordItem GOLD_DAGGER;
	public static SwordItem DIAMOND_DAGGER;
	public static SwordItem NETHERITE_DAGGER;

	public static RailgunItem RAILGUN;

	public static ArmorItem GILDED_HELMET;
	public static ArmorItem GILDED_CHESTPLATE;
	public static ArmorItem GILDED_LEGGINGS;
	public static ArmorItem GILDED_BOOTS;
	public static PickaxeItem GILDED_PICKAXE;
	public static AxeItem GILDED_AXE;
	public static SwordItem GILDED_SWORD;
	public static HoeItem GILDED_HOE;
	public static ShovelItem GILDED_SHOVEL;
	public static SwordItem GILDED_DAGGER;

	public static WoodArmorItem OAK_WOOD_HELMET;
	public static WoodArmorItem OAK_WOOD_BOOTS;

	public static List<ArmorItem> ARMORS = new ArrayList<>();

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

	private static ArmorItem register(String id, EquipmentSlot slot, ArmorMaterial material)
	{
		ArmorItem item = register(id, new ArmorItem(material, slot, new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
		ARMORS.add(item);
		return item;
	}

	private static WoodArmorItem register(String id, EquipmentSlot slot)
	{
		WoodArmorItem item = register(id, new WoodArmorItem(RandomArmorMaterials.WOOD, slot, new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
		ARMORS.add(item);
		return item;
	}

	public static void register()
	{
		DRAWBRIDGE = register(RandomBlocks.DRAWBRIDGE, ItemGroup.REDSTONE);
		CHARCOAL_BLOCK = register(RandomBlocks.CHARCOAL_BLOCK, ItemGroup.BUILDING_BLOCKS);
		MOSSY_PRESSURE_PLATE = register(RandomBlocks.MOSSY_PRESSURE_PLATE, ItemGroup.REDSTONE);
		MOSSY_PRESSURE_PLATE_SILENT = register(RandomBlocks.MOSSY_PRESSURE_PLATE_SILENT, ItemGroup.REDSTONE);
		MOSSY_PRESSURE_PLATE_INVISIBLE = register(RandomBlocks.MOSSY_PRESSURE_PLATE_INVISIBLE, ItemGroup.REDSTONE);
		MOSSY_PRESSURE_PLATE_SILENT_INVISIBLE = register(RandomBlocks.MOSSY_PRESSURE_PLATE_SILENT_INVISIBLE, ItemGroup.REDSTONE);
		OBSIDIAN_PRESSURE_PLATE = register(RandomBlocks.OBSIDIAN_PRESSURE_PLATE, ItemGroup.REDSTONE);
		OBSIDIAN_PRESSURE_PLATE_SILENT = register(RandomBlocks.OBSIDIAN_PRESSURE_PLATE_SILENT, ItemGroup.REDSTONE);
		OBSIDIAN_PRESSURE_PLATE_INVISIBLE = register(RandomBlocks.OBSIDIAN_PRESSURE_PLATE_INVISIBLE, ItemGroup.REDSTONE);
		OBSIDIAN_PRESSURE_PLATE_SILENT_INVISIBLE = register(RandomBlocks.OBSIDIAN_PRESSURE_PLATE_SILENT_INVISIBLE, ItemGroup.REDSTONE);
		TINY_CHEST = register(RandomBlocks.TINY_CHEST, ItemGroup.DECORATIONS);
		COAL_PIECE = register("coal_piece", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(64)));
		CHARCOAL_PIECE = register("charcoal_piece", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).maxCount(64)));
		WOOD_DAGGER = register("wood_dagger", ToolMaterials.WOOD);
		STONE_DAGGER = register("stone_dagger", ToolMaterials.STONE);
		IRON_DAGGER = register("iron_dagger", ToolMaterials.IRON);
		GOLD_DAGGER = register("gold_dagger", ToolMaterials.GOLD);
		DIAMOND_DAGGER = register("diamond_dagger", ToolMaterials.DIAMOND);
		NETHERITE_DAGGER = register("netherite_dagger", ToolMaterials.NETHERITE);
		RAILGUN = register("railgun", new RailgunItem(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT)));
		GILDED_HELMET = register("gilded_helmet", EquipmentSlot.HEAD, RandomArmorMaterials.GILDED);
		GILDED_CHESTPLATE = register("gilded_chestplate", EquipmentSlot.CHEST, RandomArmorMaterials.GILDED);
		GILDED_LEGGINGS = register("gilded_leggings", EquipmentSlot.LEGS, RandomArmorMaterials.GILDED);
		GILDED_BOOTS = register("gilded_boots", EquipmentSlot.FEET, RandomArmorMaterials.GILDED);
		GILDED_PICKAXE = register("gilded_pickaxe", new OpenTools.PickaxeItem(RandomToolMaterials.GILDED, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)));
		GILDED_AXE = register("gilded_axe", new OpenTools.AxeItem(RandomToolMaterials.GILDED, 6, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)));
		GILDED_SHOVEL = register("gilded_shovel", new ShovelItem(RandomToolMaterials.GILDED, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
		GILDED_HOE = register("gilded_hoe", new OpenTools.HoeItem(RandomToolMaterials.GILDED, -2, -1, new FabricItemSettings().group(ItemGroup.TOOLS)));
		GILDED_SWORD = register("gilded_sword", new SwordItem(RandomToolMaterials.GILDED, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)));
		GILDED_DAGGER = register("gilded_dagger", RandomToolMaterials.GILDED);
		OAK_WOOD_HELMET = register("wood_helmet", EquipmentSlot.HEAD);
		OAK_WOOD_BOOTS = register("wood_boots", EquipmentSlot.FEET);

		FuelRegistry.INSTANCE.add(COAL_PIECE, 200);
		FuelRegistry.INSTANCE.add(CHARCOAL_PIECE, 200);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK, 16000);
	}
}
