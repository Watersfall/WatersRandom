package net.watersfall.random.client.renderer.block;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.watersfall.random.block.entity.TinyChestBlockEntity;

public class TinyChestBlockRenderer extends ChestBlockEntityRenderer<TinyChestBlockEntity>
{
	public TinyChestBlockRenderer(BlockEntityRendererFactory.Context ctx)
	{
		super(ctx);
	}

	@Override
	public void render(TinyChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		matrices.push();
		float scale = 8F / 14F;
		double translate = 3D / 14D;
		matrices.translate(translate, 0, translate);
		matrices.scale(scale, scale, scale);
		super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
		matrices.pop();
	}
}
