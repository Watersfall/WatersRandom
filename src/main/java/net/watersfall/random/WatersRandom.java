package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.watersfall.random.ability.WoodArmorAbilityImpl;
import net.watersfall.random.api.ability.WoodArmorAbility;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.mixin.accessor.MobSpawnerLogicAccessor;
import net.watersfall.random.registry.*;
import net.watersfall.wet.api.abilities.AbilityProvider;
import net.watersfall.wet.api.event.AbilityCreateEvent;

public class WatersRandom implements ModInitializer
{
	public static final String MODID = "watersrandom";

	public static final Tag<Item> GOLD_ARMORS = TagFactory.ITEM.create(new Identifier("c", "gold_armors"));

	public static Identifier getId(String name)
	{
		return new Identifier(MODID, name);
	}

	private void registerAbilities()
	{
		AbilityProvider.ITEM_REGISTRY.register(WoodArmorAbility.ID, WoodArmorAbilityImpl::new);
		AbilityCreateEvent.ITEM.register(((item, provider) -> {
			if(item == RandomItems.OAK_WOOD_BOOTS || item == RandomItems.OAK_WOOD_HELMET)
			{
				provider.addAbility(new WoodArmorAbilityImpl());
			}
		}));
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
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, RandomPlacedFeatures.EARTH_MELONS);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.VEGETAL_DECORATION, RandomPlacedFeatures.FIRE_MELONS);
		BiomeModifications.addFeature((biome) -> BiomeSelectors.foundInTheEnd().test(biome) && biome.getBiomeKey() != BiomeKeys.THE_END, GenerationStep.Feature.VEGETAL_DECORATION, RandomPlacedFeatures.AIR_MELONS);
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
		registerAbilities();
		registerEvents();
		RandomConfiguredFeatures.register();
		RandomPlacedFeatures.register();
		registerBiomeModifications();
		registerLootModifications();
		ToolsCompat.INSTANCE.load(FabricLoader.getInstance().isModLoaded("tools"));
	}
}
