package net.watersfall.random.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.api.ability.WoodArmorAbility;
import net.watersfall.random.block.entity.TinyChestBlockEntity;
import net.watersfall.random.client.gui.DrawbridgeScreen;
import net.watersfall.random.client.gui.TinyChestScreen;
import net.watersfall.random.client.renderer.block.DrawbridgeBlockRenderer;
import net.watersfall.random.client.renderer.block.TinyChestBlockRenderer;
import net.watersfall.random.client.renderer.entity.RailgunBulletEntityRenderer;
import net.watersfall.random.compat.tools.ToolsCompat;
import net.watersfall.random.entity.RailgunBulletEntity;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.item.material.RandomArmorMaterials;
import net.watersfall.random.registry.*;
import net.watersfall.tools.api.abilities.item.ToolComponentAbility;
import net.watersfall.tools.api.abilities.item.ToolItemAbility;
import net.watersfall.tools.item.ToolComponentItem;
import net.watersfall.tools.registry.ToolItems;
import net.watersfall.wet.api.abilities.AbilityProvider;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class WatersRandomClient implements ClientModInitializer
{
	public static BipedEntityModel<LivingEntity> armorModel = null;

	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), RandomBlocks.DRAWBRIDGE);
		BlockEntityRendererRegistry.register(RandomBlockEntities.DRAWBRIDGE, DrawbridgeBlockRenderer::new);
		BlockEntityRendererRegistry.register(RandomBlockEntities.TINY_CHEST, TinyChestBlockRenderer::new);
		ScreenRegistry.register(RandomScreenHandlers.DRAWBRIDGE, DrawbridgeScreen::new);
		ScreenRegistry.register(RandomScreenHandlers.TINY_CHEST, TinyChestScreen::new);

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

		ArmorRenderer.register(((matrices, vertexConsumers, stack, entity, slot, light, model) -> {
			if (armorModel == null)
			{
				armorModel = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
			}
			if(stack.getItem() instanceof ArmorItem item)
			{
				model.setAttributes(armorModel);
				armorModel.setVisible(false);
				armorModel.body.visible = slot == EquipmentSlot.CHEST;
				armorModel.leftArm.visible = slot == EquipmentSlot.CHEST;
				armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
				armorModel.head.visible = slot == EquipmentSlot.HEAD;
				armorModel.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
				armorModel.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
				if(item.getMaterial() == RandomArmorMaterials.WOOD)
				{
					armorModel.setVisible(false);
					ItemStack log = Items.OAK_LOG.getDefaultStack();
					Optional<WoodArmorAbility> optional = AbilityProvider.getAbility(stack, WoodArmorAbility.ID, WoodArmorAbility.class);
					if(optional.isPresent())
					{
						log = optional.get().getStack();
					}
					if(slot == EquipmentSlot.HEAD)
					{
						matrices.push();
						ModelPart.Cuboid cuboid = armorModel.head.getRandomCuboid(new Random());
						armorModel.getHead().rotate(matrices);
						matrices.scale((cuboid.maxX - cuboid.minX + 2) / 16F, (cuboid.maxY - cuboid.minY + 2) / 16F, (cuboid.maxZ - cuboid.minZ + 2) / 16F);
						matrices.translate(0, -0.4, 0);
						MinecraftClient.getInstance().getItemRenderer().renderItem(log, ModelTransformation.Mode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
						matrices.pop();
					}
					else if(slot == EquipmentSlot.FEET)
					{
						matrices.push();
						ModelPart.Cuboid cuboid = armorModel.leftLeg.getRandomCuboid(new Random());
						armorModel.leftLeg.rotate(matrices);
						matrices.scale((cuboid.maxX - cuboid.minX + 2) / 16F, (cuboid.maxX - cuboid.minX + 2) / 16F, (cuboid.maxZ - cuboid.minZ + 2) / 16F);
						matrices.translate(0, 1.55, 0);
						MinecraftClient.getInstance().getItemRenderer().renderItem(log, ModelTransformation.Mode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
						matrices.pop();
						matrices.push();
						cuboid = armorModel.rightLeg.getRandomCuboid(new Random());
						armorModel.rightLeg.rotate(matrices);
						matrices.scale((cuboid.maxX - cuboid.minX + 2) / 16F, (cuboid.maxX - cuboid.minX + 2) / 16F, (cuboid.maxZ - cuboid.minZ + 2) / 16F);
						matrices.translate(0, 1.55, 0);
						MinecraftClient.getInstance().getItemRenderer().renderItem(log, ModelTransformation.Mode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
						matrices.pop();
					}
					return;
				}
				if(slot == EquipmentSlot.LEGS)
				{
					ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, WatersRandom.getId("textures/models/armor/" + item.getMaterial().getName() + "_layer_2.png"));
				}
				else
				{
					ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, WatersRandom.getId("textures/models/armor/" + item.getMaterial().getName() + "_layer_1.png"));
				}
			}
		}), RandomItems.ARMORS.toArray(new ArmorItem[RandomItems.ARMORS.size()]));

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

		BuiltinItemRendererRegistry.INSTANCE.register(RandomItems.TINY_CHEST, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
			MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(
					new TinyChestBlockEntity(BlockPos.ORIGIN, RandomBlocks.TINY_CHEST.getDefaultState()),
					matrices,
					vertexConsumers,
					light,
					overlay
			);
		}));
	}
}
