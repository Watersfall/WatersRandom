package net.watersfall.random.client.renderer.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.watersfall.random.block.entity.DrawbridgeBlockEntity;
import net.watersfall.random.registry.RandomBlocks;

public class DrawbridgeBlockRenderer implements BlockEntityRenderer<DrawbridgeBlockEntity>
{
	private final BlockRenderManager blockRenderer;
	private final BlockEntityRenderDispatcher blockEntityRenderer;

	public DrawbridgeBlockRenderer(BlockEntityRendererFactory.Context ctx)
	{
		this.blockRenderer = ctx.getRenderManager();
		this.blockEntityRenderer = ctx.getRenderDispatcher();
	}

	@Override
	public void render(DrawbridgeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		BlockState state = entity.getCamouflageState();
		if(state.getBlock().getRenderType(state) == BlockRenderType.ENTITYBLOCK_ANIMATED && state.hasBlockEntity() && !state.isOf(RandomBlocks.DRAWBRIDGE))
		{
			BlockEntity blockEntity = ((BlockEntityProvider)state.getBlock()).createBlockEntity(entity.getPos(), state);
			if(blockEntity != null)
			{
				blockEntity.setWorld(entity.getWorld());
				blockEntityRenderer.render(blockEntity, tickDelta, matrices, vertexConsumers);
			}
		}
		else
		{
			blockRenderer.getModelRenderer().render(
					entity.getWorld(),
					blockRenderer.getModel(state),
					state,
					entity.getPos(),
					matrices,
					vertexConsumers.getBuffer(RenderLayers.getBlockLayer(state)),
					false,
					entity.getWorld().random,
					state.getRenderingSeed(entity.getPos()),
					overlay
			);
		}

		/*
		 * Original ItemStack way
		 */
//		ItemStack stack = entity.getCamouflage();
//		if(stack.isEmpty())
//		{
//			stack = new ItemStack(RandomItems.DRAWBRIDGE);
//		}
//		matrices.push();
//		matrices.translate(0.5, 0.5, 0.5);
//		Direction facing = entity.getCachedState().get(FacingBlock.FACING);
//		switch(facing) {
//			case UP:
//				matrices.multiply(new Quaternion(90 , 0, 0, true));
//				break;
//			case DOWN:
//				matrices.multiply(new Quaternion(270, 0, 0, true));
//				break;
//			case NORTH:
//				matrices.multiply(new Quaternion(0  , 0, 0, true));
//				break;
//			case WEST:
//				matrices.multiply(new Quaternion(0, 90 , 0, true));
//				break;
//			case SOUTH:
//				matrices.multiply(new Quaternion(0, 180, 0, true));
//				break;
//			case EAST:
//				matrices.multiply(new Quaternion(0, 270, 0, true));
//				break;
//		}
//		MinecraftClient.getInstance().getItemRenderer().renderItem(
//				stack,
//				ModelTransformation.Mode.NONE,
//				light, overlay,
//				matrices,
//				vertexConsumers,
//				0
//		);
//		matrices.pop();
	}
}
