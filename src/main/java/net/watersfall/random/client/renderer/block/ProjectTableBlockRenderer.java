package net.watersfall.random.client.renderer.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.watersfall.random.block.entity.ProjectTableBlockEntity;


public class ProjectTableBlockRenderer implements BlockEntityRenderer<ProjectTableBlockEntity>
{
	private final BlockRenderManager block;

	public ProjectTableBlockRenderer(BlockEntityRendererFactory.Context ctx)
	{
		block = ctx.getRenderManager();
	}

	@Override
	public void render(ProjectTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		matrices.push();
		matrices.scale(0.995F, 0.995F, 0.995F);
		matrices.translate(0.0025, 0.0025, 0.0025);
		block.renderBlock(
				entity.getRenderState(),
				entity.getPos(),
				MinecraftClient.getInstance().world,
				matrices,
				vertexConsumers.getBuffer(RenderLayer.getSolid()),
				false,
				MinecraftClient.getInstance().world.random
		);
		matrices.pop();
		matrices.push();
		matrices.scale(1, 0.001F, 1);
		matrices.translate(0, 0, 0);
		block.renderBlock(
				entity.getRenderState(),
				entity.getPos(),
				MinecraftClient.getInstance().world,
				matrices,
				vertexConsumers.getBuffer(RenderLayer.getSolid()),
				false,
				MinecraftClient.getInstance().world.random
		);
		matrices.pop();
	}
}
