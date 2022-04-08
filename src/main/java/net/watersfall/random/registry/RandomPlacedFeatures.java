package net.watersfall.random.registry;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NetherPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;
import net.watersfall.random.WatersRandom;

import java.util.List;

public class RandomPlacedFeatures
{
	public static RegistryEntry<PlacedFeature> EARTH_MELONS;
	public static RegistryEntry<PlacedFeature> FIRE_MELONS;
	public static RegistryEntry<PlacedFeature> AIR_MELONS;

	public static void register()
	{
		EARTH_MELONS = PlacedFeatures.register(
				"watersrandom:earth_melons",
				RandomConfiguredFeatures.EARTH_MELONS,
				CountPlacementModifier.of(25),
				SquarePlacementModifier.of(),
				PlacedFeatures.BOTTOM_TO_120_RANGE,
				RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
				EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.solid(), BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_OVERWORLD)), BlockPredicate.replaceable(), 12)
		);
		FIRE_MELONS = PlacedFeatures.register(
				"watersrandom:fire_melons",
				RandomConfiguredFeatures.FIRE_MELONS,
				CountPlacementModifier.of(UniformIntProvider.create(5, 10)),
				SquarePlacementModifier.of(),
				PlacedFeatures.FOUR_ABOVE_AND_BELOW_RANGE
		);
		AIR_MELONS = PlacedFeatures.register(
				"watersrandom:air_melons",
				RandomConfiguredFeatures.AIR_MELONS,
				CountPlacementModifier.of(8),
				SquarePlacementModifier.of(),
				RarityFilterPlacementModifier.of(128),
				HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG)
		);
	}
}
