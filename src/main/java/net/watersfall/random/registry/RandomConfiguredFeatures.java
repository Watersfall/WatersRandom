package net.watersfall.random.registry;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
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
	public static RegistryKey<ConfiguredFeature<?, ?>> EARTH_MELONS;
	public static RegistryKey<ConfiguredFeature<?, ?>> FIRE_MELONS;
	public static RegistryKey<ConfiguredFeature<?, ?>> AIR_MELONS;

	public static void register()
	{
		ConfiguredFeature<?, ?> feature = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.EARTH_MELON)));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("earth_melons"), Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(
				2, feature.withBlockPredicateFilter(BlockPredicate.allOf(BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_OVERWORLD, new Vec3i(0, -1, 0)), BlockPredicate.IS_AIR))
		)));
		feature = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.FIRE_MELON)));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("fire_melons"), Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(
				4, feature.withBlockPredicateFilter(BlockPredicate.allOf(BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_NETHER, new Vec3i(0, -1, 0)), BlockPredicate.IS_AIR))
		)));
		feature = Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(RandomBlocks.AIR_MELON)));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("air_melons"), Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(
				12, feature.withBlockPredicateFilter(BlockPredicate.allOf(BlockPredicate.solid(new Vec3i(0, -1, 0)), BlockPredicate.IS_AIR))
		)));
		EARTH_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("earth_melons"));
		FIRE_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("fire_melons"));
		AIR_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("air_melons"));
	}
}
