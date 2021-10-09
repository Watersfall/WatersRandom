package net.watersfall.random.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.watersfall.random.client.gui.DrawbridgeScreen;
import net.watersfall.random.client.renderer.block.DrawbridgeBlockRenderer;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomScreenHandlers;

@Environment(EnvType.CLIENT)
public class WatersRandomClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), RandomBlocks.DRAWBRIDGE);
		BlockEntityRendererRegistry.register(RandomBlockEntities.DRAWBRIDGE, DrawbridgeBlockRenderer::new);
		ScreenRegistry.register(RandomScreenHandlers.DRAWBRIDGE, DrawbridgeScreen::new);
	}
}
