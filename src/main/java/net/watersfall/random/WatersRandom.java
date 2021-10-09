package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomItems;
import net.watersfall.random.registry.RandomScreenHandlers;

public class WatersRandom implements ModInitializer
{
	public static final String MODID = "watersrandom";

	public static Identifier getId(String name)
	{
		return new Identifier(MODID, name);
	}

	@Override
	public void onInitialize()
	{
		RandomBlocks.register();
		RandomItems.register();
		RandomBlockEntities.register();
		RandomScreenHandlers.register();
	}
}
