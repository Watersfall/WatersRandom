package net.watersfall.random.registry;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.CaveSurfaceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.watersfall.random.WatersRandom;

import java.util.Set;

public abstract class RandomConfiguredFeatures extends ConfiguredFeatures
{
	public static RegistryKey<ConfiguredFeature<?, ?>> EARTH_MELONS;
	public static RegistryKey<ConfiguredFeature<?, ?>> FIRE_MELONS;
	public static RegistryKey<ConfiguredFeature<?, ?>> AIR_MELONS;

	public static void register()
	{
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("earth_melons"), Feature.RANDOM_PATCH.configure(
				new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(RandomBlocks.EARTH_MELON.getDefaultState()), new SimpleBlockPlacer())
						.tries(12)
						.whitelist(Set.of(Blocks.DIRT, Blocks.STONE))
						.cannotProject()
						.build()
		).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_60).spreadHorizontally().repeatRandomly(64).applyChance(8));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("fire_melons"), Feature.RANDOM_PATCH.configure(
				new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(RandomBlocks.FIRE_MELON.getDefaultState()), new SimpleBlockPlacer())
						.tries(128)
						.whitelist(Set.of(Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM))
						.cannotProject()
						.build()
		).decorate(Decorators.FIRE).applyChance(128));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, WatersRandom.getId("air_melons"), Feature.RANDOM_PATCH.configure(
				new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(RandomBlocks.AIR_MELON.getDefaultState()), new SimpleBlockPlacer())
						.tries(32)
						.whitelist(Set.of(Blocks.END_STONE))
						.build()
		).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(128));
		EARTH_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("earth_melons"));
		FIRE_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("fire_melons"));
		AIR_MELONS = RegistryKey.of(BuiltinRegistries.CONFIGURED_FEATURE.getKey(), WatersRandom.getId("air_melons"));
	}
}
