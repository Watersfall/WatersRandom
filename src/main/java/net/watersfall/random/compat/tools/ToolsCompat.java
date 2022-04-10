package net.watersfall.random.compat.tools;

import net.minecraft.item.Item;

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
