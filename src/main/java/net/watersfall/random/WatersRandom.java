package net.watersfall.random;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.registry.*;

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
		ToolsCompat.INSTANCE.load(FabricLoader.getInstance().isModLoaded("tools"));
	}
}
