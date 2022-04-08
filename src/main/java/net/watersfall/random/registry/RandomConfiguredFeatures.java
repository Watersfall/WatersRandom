package net.watersfall.random.registry;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.watersfall.random.WatersRandom;

import java.util.List;
import java.util.Set;

public abstract class RandomConfiguredFeatures extends ConfiguredFeatures
{
	public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> EARTH_MELONS;
	public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> FIRE_MELONS;
	public static RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> AIR_MELONS;

	public static void register()
	{
		RegistryEntry<PlacedFeature> melon = PlacedFeatures.createEntry(
				Feature.SIMPLE_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.EARTH_MELON)),
				BlockPredicate.allOf(
						BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_OVERWORLD, new Vec3i(0, -1, 0)),
						BlockPredicate.IS_AIR
				)
		);
		EARTH_MELONS = ConfiguredFeatures.register("watersrandom:earth_melons", Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(2, 7, 3, melon));
		melon = PlacedFeatures.createEntry(
				Feature.SIMPLE_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.FIRE_MELON)),
				BlockPredicate.allOf(
						BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_NETHER, new Vec3i(0, -1, 0)),
						BlockPredicate.IS_AIR
				)
		);
		FIRE_MELONS = ConfiguredFeatures.register("watersrandom:fire_melons", Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(4, 7, 3, melon));
		melon = PlacedFeatures.createEntry(
				Feature.SIMPLE_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.AIR_MELON)),
				BlockPredicate.allOf(
						BlockPredicate.solid(new Vec3i(0, -1, 0)),
						BlockPredicate.IS_AIR
				)
		);
		AIR_MELONS = ConfiguredFeatures.register("watersrandom:air_melons", Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(12, 7, 3, melon));
	}
}
