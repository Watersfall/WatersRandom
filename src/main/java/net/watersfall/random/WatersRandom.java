package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class WatersRandom implements ModInitializer
{
	public static final String MODID = "waters_random";

	public static Identifier getId(String name)
	{
		return new Identifier(MODID, name);
	}

	@Override
	public void onInitialize()
	{

	}
}
