package net.watersfall.random.compat.tools;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.tools.abilities.item.ToolItemAbilityImpl;
import net.watersfall.tools.api.abilities.item.ToolComponentAbility;
import net.watersfall.tools.api.abilities.item.ToolItemAbility;
import net.watersfall.tools.item.ToolComponentItem;
import net.watersfall.tools.tool.ToolTypes;
import net.watersfall.wet.api.abilities.AbilityProvider;
import net.watersfall.wet.api.event.AbilityCreateEvent;

import java.util.Optional;

public class ToolsCompatLoader
{
	public static Item loadDagger()
	{
		return Registry.register(Registry.ITEM, WatersRandom.getId("forged_dagger"), new ForgedDaggerItem());
	}

	public static Item loadDaggerBlade()
	{
		return Registry.register(Registry.ITEM, WatersRandom.getId("dagger_blade"), new ToolComponentItem(ToolTypes.HEAD));
	}

	public static void load()
	{
		AbilityCreateEvent.ITEM.register((item, provider) -> {
			if(item == ToolsCompat.INSTANCE.DAGGER)
			{
				provider.addAbility(new ToolItemAbilityImpl(1, -1));
			}
		});
	}

	public static void loadClient()
	{
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			Optional<ToolItemAbility> optional = AbilityProvider.getAbility(stack, ToolItemAbility.ID, ToolItemAbility.class);
			if(optional.isPresent())
			{
				if(tintIndex == 0)
				{
					return optional.get().getHead().getColor();
				}
				else if(tintIndex == 1)
				{
					return optional.get().getBinding().getColor();
				}
				else
				{
					return optional.get().getHandle().getColor();
				}
			}
			return 0;
		}, ToolsCompat.INSTANCE.DAGGER);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			Optional<ToolComponentAbility> optional = AbilityProvider.getAbility(stack, ToolComponentAbility.ID, ToolComponentAbility.class);
			if(optional.isPresent() && stack.getItem() instanceof ToolComponentItem item)
			{
				return item.getType().getComponent(optional.get().getMaterial()).getColor();
			}
			return 0;
		}, ToolsCompat.INSTANCE.DAGGER_BLADE);
	}
}
