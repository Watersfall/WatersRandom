package net.watersfall.random.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.watersfall.random.registry.RandomBlocks;

import java.util.HashMap;
import java.util.Map;

public class WatersCandleCakeBlock extends AbstractCandleBlock
{
	public static final BooleanProperty LIT = Properties.LIT;
	public static final VoxelShape SHAPE = VoxelShapes.union(
			Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
			Block.createCuboidShape(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D)
	);
	public static final Map<Block, WatersCandleCakeBlock> CANDLES_TO_CANDLE_CAKES = new HashMap<>();
	public static final Iterable<Vec3d> PARTICLE_OFFSETS = ImmutableList.of(new Vec3d(0.5D, 1.0D, 0.5D));

	public WatersCandleCakeBlock(Block candle, Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getDefaultState().with(LIT, false));
		CANDLES_TO_CANDLE_CAKES.put(candle, this);
	}

	protected Iterable<Vec3d> getParticleOffsets(BlockState state)
	{
		return PARTICLE_OFFSETS;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return SHAPE;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		if(!itemStack.isOf(Items.FLINT_AND_STEEL) && !itemStack.isOf(Items.FIRE_CHARGE))
		{
			if(isHittingCandle(hit) && player.getStackInHand(hand).isEmpty() && state.get(LIT))
			{
				extinguish(player, state, world, pos);
				return ActionResult.success(world.isClient);
			}
			else
			{
				ActionResult actionResult = WatersCakeBlock.tryEat(world, pos, RandomBlocks.CHOCOLATE_CAKE.getDefaultState(), player);
				if(actionResult.isAccepted())
				{
					dropStacks(state, world, pos);
				}

				return actionResult;
			}
		}
		else
		{
			return ActionResult.PASS;
		}
	}

	private static boolean isHittingCandle(BlockHitResult hitResult)
	{
		return hitResult.getPos().y - (double)hitResult.getBlockPos().getY() > 0.5D;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(LIT);
	}

	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(RandomBlocks.CHOCOLATE_CAKE);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).getMaterial().isSolid();
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos)
	{
		return CakeBlock.DEFAULT_COMPARATOR_OUTPUT;
	}

	public boolean hasComparatorOutput(BlockState state)
	{
		return true;
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
	{
		return false;
	}

	public static BlockState getCandleCakeFromCandle(Block candle)
	{
		return CANDLES_TO_CANDLE_CAKES.get(candle).getDefaultState();
	}
}
