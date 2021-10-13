package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.watersfall.random.ability.WoodArmorAbilityImpl;
import net.watersfall.random.api.ability.WoodArmorAbility;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.item.WoodArmorItem;
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
		ToolsCompat.INSTANCE.load(FabricLoader.getInstance().isModLoaded("tools"));
	}
}
