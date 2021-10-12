package net.watersfall.random.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.client.gui.DrawbridgeScreen;
import net.watersfall.random.client.renderer.block.DrawbridgeBlockRenderer;
import net.watersfall.random.client.renderer.entity.RailgunBulletEntityRenderer;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.entity.RailgunBulletEntity;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomEntities;
import net.watersfall.random.registry.RandomScreenHandlers;
import net.watersfall.tools.api.abilities.item.ToolComponentAbility;
import net.watersfall.tools.api.abilities.item.ToolItemAbility;
import net.watersfall.tools.item.ToolComponentItem;
import net.watersfall.tools.registry.ToolItems;
import net.watersfall.wet.api.abilities.AbilityProvider;

import java.util.Optional;
import java.util.UUID;

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

		EntityRendererRegistry.register(RandomEntities.RAILGUN_BULLET, RailgunBulletEntityRenderer::new);

		ItemTooltipCallback.EVENT.register(((stack, context, lines) -> {
			if(RailgunItem.RailgunAmmo.containsKey(stack.getItem()))
			{
				RailgunItem.RailgunAmmo ammo = RailgunItem.RailgunAmmo.get(stack.getItem());
				lines.add(new TranslatableText("item.watersrandom.railgun.ammo").formatted(Formatting.DARK_GRAY));
				lines.add(new TranslatableText("item.watersrandom.railgun.ammo.damage", ammo.damage()).formatted(Formatting.DARK_GRAY));
			}
		}));

		ClientPlayNetworking.registerGlobalReceiver(WatersRandom.getId("spawn_packet"), (client, handler, buf, response) -> {
			EntityType<?> type = Registry.ENTITY_TYPE.get(buf.readVarInt());
			UUID uuid = buf.readUuid();
			int id = buf.readVarInt();
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			float pitch = buf.readFloat();
			float yaw = buf.readFloat();
			Vec3d vel = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			int owner = buf.readVarInt();
			ItemStack stack = buf.readItemStack();
			client.execute(() -> {
				RailgunBulletEntity entity = (RailgunBulletEntity)type.create(client.world);
				entity.updatePositionAndAngles(x, y, z, yaw, pitch);
				entity.updateTrackedPosition(x, y, z);
				entity.setVelocity(vel);
				entity.setId(id);
				entity.setUuid(uuid);
				entity.setOwner(client.world.getEntityById(owner));
				entity.setItem(stack);
				entity.setAmmo(RailgunItem.RailgunAmmo.get(stack.getItem()));
				client.world.addEntity(id, entity);
			});
		});
	}
}
