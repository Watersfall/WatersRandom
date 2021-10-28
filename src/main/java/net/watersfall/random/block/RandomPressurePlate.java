package net.watersfall.random.block;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public abstract class RandomPressurePlate extends AbstractPressurePlateBlock
{
	public static final BooleanProperty POWERED = Properties.POWERED;
	private final boolean silent;
	private final boolean visible;

	public RandomPressurePlate(boolean silent, boolean visible, Settings settings)
	{
		super(settings);
		this.silent = silent;
		this.visible = visible;
		this.setDefaultState(this.getDefaultState().with(POWERED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(POWERED);
	}

	@Override
	protected void playPressSound(WorldAccess world, BlockPos pos)
	{
		if(!silent)
		{
			world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}
	}

	@Override
	protected void playDepressSound(WorldAccess world, BlockPos pos)
	{
		if(!silent)
		{
			world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
		}
	}

	@Override
	protected int getRedstoneOutput(BlockState state)
	{
		return state.get(POWERED) ? 15 : 0;
	}

	@Override
	protected BlockState setRedstoneOutput(BlockState state, int rsOut)
	{
		return state.with(POWERED, rsOut > 0);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return visible ? super.getRenderType(state) : BlockRenderType.INVISIBLE;
	}
}
