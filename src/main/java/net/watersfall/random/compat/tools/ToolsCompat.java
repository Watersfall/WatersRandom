package net.watersfall.random.compat.tools;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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

public class ToolsCompat
{
	public Item DAGGER;
	public Item DAGGER_BLADE;

	public static final ToolsCompat INSTANCE = new ToolsCompat();

	private boolean loaded;

	private ToolsCompat() {}

	public void load(boolean isLoaded)
	{
		if(isLoaded)
		{
			DAGGER = ToolsCompatLoader.loadDagger();
			DAGGER_BLADE = ToolsCompatLoader.loadDaggerBlade();
			ToolsCompatLoader.load();
		}
		else
		{
			/*DAGGER = new Item(new FabricItemSettings());
			DAGGER_BLADE = new Item(new FabricItemSettings());*/
		}
		loaded = isLoaded;
	}

	public void loadClient(boolean isLoaded)
	{
		loaded = isLoaded;
		if(isLoaded)
		{
			ToolsCompatLoader.loadClient();
		}
	}

	public boolean isLoaded()
	{
		return loaded;
	}
}
