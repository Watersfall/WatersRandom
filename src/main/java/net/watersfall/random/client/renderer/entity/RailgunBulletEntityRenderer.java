package net.watersfall.random.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.watersfall.random.entity.RailgunBulletEntity;

public class RailgunBulletEntityRenderer extends FlyingItemEntityRenderer<RailgunBulletEntity>
{
	public RailgunBulletEntityRenderer(EntityRendererFactory.Context context)
	{
		super(context);
	}

	@Override
	public void render(RailgunBulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
	{
		if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25D)) {
			matrices.push();
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-entity.getYaw(tickDelta) - 90));
			matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-entity.getPitch(tickDelta)));
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
			if(!entity.getAmmo().horizontal())
			{
				matrices.translate(0, 0.1, 0);
				matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90F));
			}
			MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(), ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
			matrices.pop();
		}
	}
}
