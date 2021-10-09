package net.watersfall.random.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.gui.DrawbridgeHandler;

public class RandomScreenHandlers
{
	public static ScreenHandlerType<DrawbridgeHandler> DRAWBRIDGE;

	public static void register()
	{
		DRAWBRIDGE = ScreenHandlerRegistry.registerSimple(WatersRandom.getId("drawbridge"), DrawbridgeHandler::new);
	}
}
