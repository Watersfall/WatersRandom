package net.watersfall.random.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IgniterBlock extends Block
{
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public IgniterBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getDefaultState().with(POWERED, false).with(FACING, Direction.NORTH));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(POWERED, FACING);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
	{
		boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
		boolean on = state.get(POWERED);
		BlockPos forward = pos.offset(state.get(FACING));
		BlockState forwardState = world.getBlockState(forward);
		if(on && !powered)
		{
			if(forwardState.getBlock() instanceof FireBlock || forwardState.getBlock() instanceof NetherPortalBlock)
			{
				world.setBlockState(pos, state.with(POWERED, false));
				world.setBlockState(forward, Blocks.AIR.getDefaultState());
			}
		}
		if(powered)
		{
			if(forwardState.isAir())
			{
				world.setBlockState(forward, Blocks.FIRE.getDefaultState());
			}
			if(!on)
			{
				world.setBlockState(pos, state.with(POWERED, true));
			}
		}
		super.neighborUpdate(state, world, pos, block, fromPos, notify);
	}
}
