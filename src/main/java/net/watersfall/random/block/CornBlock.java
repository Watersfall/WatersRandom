package net.watersfall.random.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.watersfall.random.registry.RandomItems;

import java.util.Random;

public class CornBlock extends CropBlock
{
	public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;
	public static final IntProperty AGE = Properties.AGE_5;
	public static final VoxelShape[] OUTLINES = new VoxelShape[]{
			Block.createCuboidShape(0, 0, 0, 16, 4, 16),
			Block.createCuboidShape(0, 0, 0, 16, 4, 16),
			Block.createCuboidShape(0, 0, 0, 16, 7, 16),
			Block.createCuboidShape(0, 0, 0, 16, 10, 16),
			Block.createCuboidShape(0, 0, 0, 16, 15, 16),
			Block.createCuboidShape(0, 0, 0, 16, 16, 16)
	};

	public CornBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getDefaultState().with(HALF, BlockHalf.BOTTOM).with(AGE, 1));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(AGE, HALF);
	}

	@Override
	public IntProperty getAgeProperty()
	{
		return AGE;
	}

	@Override
	protected ItemConvertible getSeedsItem()
	{
		return RandomItems.CORN_SEED;
	}

	@Override
	public int getMaxAge()
	{
		return 5;
	}

	@Override
	public boolean hasRandomTicks(BlockState state)
	{
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.randomTick(state, world, pos, random);
		if(state.get(HALF) == BlockHalf.BOTTOM && state.get(AGE) == 5)
		{
			if(world.getBlockState(pos.up()).isAir())
			{
				world.setBlockState(pos.up(), getDefaultState().with(HALF, BlockHalf.TOP));
			}
		}
		else if(state.get(HALF) == BlockHalf.TOP && state.get(AGE) < getMaxAge())
		{
			if(CropBlock.getAvailableMoisture(this, world, pos.down()) > 0)
			{
				world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
			}
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return OUTLINES[state.get(AGE)];
	}
}
