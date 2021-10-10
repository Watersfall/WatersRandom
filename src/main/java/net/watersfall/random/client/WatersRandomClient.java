package net.watersfall.random.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.watersfall.random.client.gui.DrawbridgeScreen;
import net.watersfall.random.client.renderer.block.DrawbridgeBlockRenderer;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomScreenHandlers;
import net.watersfall.tools.api.abilities.item.ToolComponentAbility;
import net.watersfall.tools.api.abilities.item.ToolItemAbility;
import net.watersfall.tools.item.ToolComponentItem;
import net.watersfall.tools.registry.ToolItems;
import net.watersfall.wet.api.abilities.AbilityProvider;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class WatersRandomClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), RandomBlocks.DRAWBRIDGE);
		BlockEntityRendererRegistry.register(RandomBlockEntities.DRAWBRIDGE, DrawbridgeBlockRenderer::new);
		ScreenRegistry.register(RandomScreenHandlers.DRAWBRIDGE, DrawbridgeScreen::new);

		ToolsCompat.INSTANCE.loadClient(FabricLoader.getInstance().isModLoaded("tools"));
	}
}
