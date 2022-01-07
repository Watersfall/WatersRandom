package net.watersfall.random.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.gui.DrawbridgeHandler;
import net.watersfall.random.gui.ProjectTableScreenHandler;
import net.watersfall.random.gui.TinyChestScreenHandler;

public class RandomScreenHandlers
{
	public static ScreenHandlerType<DrawbridgeHandler> DRAWBRIDGE;
	public static ScreenHandlerType<TinyChestScreenHandler> TINY_CHEST;
	public static ScreenHandlerType<ProjectTableScreenHandler> PROJECT_TABLE;

	public static void register()
	{
		DRAWBRIDGE = ScreenHandlerRegistry.registerSimple(WatersRandom.getId("drawbridge"), DrawbridgeHandler::new);
		TINY_CHEST = ScreenHandlerRegistry.registerExtended(WatersRandom.getId("tiny_chest"), TinyChestScreenHandler::new);
		PROJECT_TABLE = ScreenHandlerRegistry.registerSimple(WatersRandom.getId("project_table"), ProjectTableScreenHandler::new);
	}
}
