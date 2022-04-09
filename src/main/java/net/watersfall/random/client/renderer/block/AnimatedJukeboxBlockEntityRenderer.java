package net.watersfall.random.client.renderer.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.watersfall.random.block.entity.AnimatedJukeboxBlockEntity;

public class AnimatedJukeboxBlockEntityRenderer implements BlockEntityRenderer<AnimatedJukeboxBlockEntity>
{
	public AnimatedJukeboxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

	@Override
	public void render(AnimatedJukeboxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		matrices.push();
		matrices.translate(7.875D / 16D, 10.5d / 16d, 5.25 / 16D);
		matrices.scale(1.5F, 1.2F, 1.5F);
		matrices.multiply(Quaternion.fromEulerXyz(MathHelper.PI / 2, 0, 0));
		if((entity.getWorld().getTime() / 15) % 2 == 0)
		{
			matrices.translate(-0.5 / 16d, 0, 0);
			matrices.multiply(Quaternion.fromEulerXyz(0, MathHelper.PI, 0));
		}
		MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getRecord(), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
		matrices.pop();
	}
}
