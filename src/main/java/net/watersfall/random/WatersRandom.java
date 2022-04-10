package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.mixin.accessor.MobSpawnerLogicAccessor;
import net.watersfall.random.registry.*;

import java.util.Optional;

public class WatersRandom implements ModInitializer
{
	public static final String MODID = "watersrandom";

	public static final TagKey<Item> GOLD_ARMORS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "gold_armors"));

	public static Identifier getId(String name)
	{
		return new Identifier(MODID, name);
	}

	private void registerEvents()
	{
		PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, test) -> {
			if(!world.isClient)
			{
				if(state.isOf(Blocks.SPAWNER))
				{
					if(test instanceof MobSpawnerBlockEntity spawner)
					{
						((MobSpawnerLogicAccessor)spawner.getLogic()).setSpawnDelay(0);
						spawner.getLogic().serverTick((ServerWorld)world, pos);
					}
				}
			}
		}));
	}

	private void registerBiomeModifications()
	{
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, RandomPlacedFeatures.EARTH_MELONS.getKey().get());
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.VEGETAL_DECORATION, RandomPlacedFeatures.FIRE_MELONS.getKey().get());
		BiomeModifications.addFeature((biome) -> BiomeSelectors.foundInTheEnd().test(biome) && biome.getBiomeKey() != BiomeKeys.THE_END, GenerationStep.Feature.VEGETAL_DECORATION, RandomPlacedFeatures.AIR_MELONS.getKey().get());
	}

	private void registerLootModifications()
	{
		LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
			if(id.equals(LootTables.ABANDONED_MINESHAFT_CHEST))
			{
				FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
						.rolls(UniformLootNumberProvider.create(0, 16))
						.withEntry(ItemEntry.builder(RandomItems.EARTH_MELON_SEEDS)
								.conditionally(RandomChanceLootCondition.builder(0.5F))
								.build()
						);
				supplier.withPool(pool.build());
			}
			else if(id.equals(LootTables.NETHER_BRIDGE_CHEST))
			{
				FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
						.rolls(UniformLootNumberProvider.create(0, 16))
						.withEntry(ItemEntry.builder(RandomItems.FIRE_MELON_SEEDS)
								.conditionally(RandomChanceLootCondition.builder(0.5F))
								.build()
						);
				supplier.withPool(pool.build());
			}
			else if(id.equals(LootTables.END_CITY_TREASURE_CHEST))
			{
				FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
						.rolls(UniformLootNumberProvider.create(0, 16))
						.withEntry(ItemEntry.builder(RandomItems.AIR_MELON_SEEDS)
								.conditionally(RandomChanceLootCondition.builder(0.5F))
								.build()
						);
				supplier.withPool(pool.build());
			}
			else if(id.equals(LootTables.SIMPLE_DUNGEON_CHEST))
			{
				FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
						.rolls(UniformLootNumberProvider.create(0, 16))
						.withEntry(ItemEntry.builder(RandomItems.CORN_SEED)
								.conditionally(RandomChanceLootCondition.builder(0.33F))
								.build()
						);
				supplier.withPool(pool.build());
			}
			else if(id.getPath().startsWith("entities/"))
			{
				String[] path = id.getPath().split("/");
				Optional<EntityType<?>> optional = EntityType.get(path[path.length - 1]);
				if(optional.isPresent())
				{
					FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.withEntry(ItemEntry.builder(RandomItems.SONIC_ARROW)
									.conditionally(RandomChanceWithLootingLootCondition.builder(0.01F, 0.02F))
									.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().type(EntityTypeTags.SKELETONS)))
									.build()
							);
					supplier.withPool(pool.build());
				}
			}
		}));
	}

	@Override
	public void onInitialize()
	{
		RandomBlocks.register();
		RandomItems.register();
		RandomBlockEntities.register();
		RandomScreenHandlers.register();
		RandomEntities.register();
		RandomRecipes.register();
		RailgunItem.registerAmmo();
		registerEvents();
		RandomConfiguredFeatures.register();
		RandomPlacedFeatures.register();
		registerBiomeModifications();
		registerLootModifications();
	}
}
