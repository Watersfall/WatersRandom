package net.watersfall.random.client.renderer.block;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class BrewingStandBlockEntityRenderer implements BlockEntityRenderer<BrewingStandBlockEntity>
{
	public BrewingStandBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){}

	@Override
	public void render(BrewingStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
		ModelTransformation.Mode mode = ModelTransformation.Mode.GROUND;

		matrices.push();
		ItemStack item = entity.getStack(2);
		matrices.translate(0.291, 0.3, 0.291);
		matrices.multiply(new Quaternion(0, -45, 0, true));
		renderer.renderItem(item, mode, light, overlay, matrices, vertexConsumers, 0);
		matrices.pop();

		matrices.push();
		item = entity.getStack(1);
		matrices.translate(0.291, 0.3, 0.709);
		matrices.multiply(new Quaternion(0, 45, 0,true));
		renderer.renderItem(item, mode, light, overlay, matrices, vertexConsumers, 0);
		matrices.pop();

		matrices.push();
		item = entity.getStack(0);
		matrices.translate(0.765, 0.3, 0.5);
		renderer.renderItem(item, mode, light, overlay, matrices, vertexConsumers, 0);
		matrices.pop();

		matrices.push();
		item = entity.getStack(3);
		double yOffset = MathHelper.sin((entity.getWorld().getTime() + tickDelta) / 10F) / 20D;
		matrices.translate(0.5, 1 + yOffset, 0.5);
		matrices.scale(0.75F, 0.75F, 0.75F);
		float angle = (entity.getWorld().getTime() + tickDelta) / 20F;
		matrices.multiply(new Quaternion(0, angle, 0, false));
		renderer.renderItem(item, mode, light, overlay, matrices, vertexConsumers, 0);
		matrices.pop();
	}
}
