package net.watersfall.random.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class WatersCakeBlock extends CakeBlock
{
	public WatersCakeBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		ItemStack stack = player.getStackInHand(hand);
		if (stack.isIn(ItemTags.CANDLES) && state.get(BITES) == 0)
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			if (block instanceof CandleBlock)
			{
				if (!player.isCreative())
				{
					stack.decrement(1);
				}
				world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlockState(pos, WatersCandleCakeBlock.getCandleCakeFromCandle(block));
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	public static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		return CakeBlock.tryEat(world, pos, state, player);
	}
}
