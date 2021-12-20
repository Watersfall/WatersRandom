package net.watersfall.random.registry;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NetherPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.watersfall.random.WatersRandom;

import java.util.List;

public class RandomPlacedFeatures
{
	public static RegistryKey<PlacedFeature> EARTH_MELONS;
	public static RegistryKey<PlacedFeature> FIRE_MELONS;
	public static RegistryKey<PlacedFeature> AIR_MELONS;

	public static void register()
	{
		Registry.register(BuiltinRegistries.PLACED_FEATURE, WatersRandom.getId("earth_melons"), BuiltinRegistries.CONFIGURED_FEATURE.get(RandomConfiguredFeatures.EARTH_MELONS).withPlacement(
				CountPlacementModifier.of(25),
				SquarePlacementModifier.of(),
				PlacedFeatures.BOTTOM_TO_120_RANGE,
				RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
				EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.solid(), BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_OVERWORLD)), BlockPredicate.replaceable(), 12)
		));
		Registry.register(BuiltinRegistries.PLACED_FEATURE, WatersRandom.getId("fire_melons"), BuiltinRegistries.CONFIGURED_FEATURE.get(RandomConfiguredFeatures.FIRE_MELONS).withPlacement(
				CountPlacementModifier.of(UniformIntProvider.create(5, 10)),
				SquarePlacementModifier.of(),
				PlacedFeatures.FOUR_ABOVE_AND_BELOW_RANGE
		));
		Registry.register(BuiltinRegistries.PLACED_FEATURE, WatersRandom.getId("air_melons"), BuiltinRegistries.CONFIGURED_FEATURE.get(RandomConfiguredFeatures.AIR_MELONS).withPlacement(
				CountPlacementModifier.of(8),
				SquarePlacementModifier.of(),
				RarityFilterPlacementModifier.of(128),
				HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG)
		));
		EARTH_MELONS = RegistryKey.of(BuiltinRegistries.PLACED_FEATURE.getKey(), WatersRandom.getId("earth_melons"));
		FIRE_MELONS = RegistryKey.of(BuiltinRegistries.PLACED_FEATURE.getKey(), WatersRandom.getId("fire_melons"));
		AIR_MELONS = RegistryKey.of(BuiltinRegistries.PLACED_FEATURE.getKey(), WatersRandom.getId("air_melons"));
	}
}
