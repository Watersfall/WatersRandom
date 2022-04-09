package net.watersfall.random.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.watersfall.random.block.entity.AnimatedJukeboxBlockEntity;

public class AnimatedJukeboxBlock extends JukeboxBlock
{
	public static final VoxelShape OUTLINE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);

	public AnimatedJukeboxBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new AnimatedJukeboxBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return OUTLINE;
	}
}
