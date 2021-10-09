package net.watersfall.random.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.block.entity.DrawbridgeBlockEntity;
import net.watersfall.random.gui.DrawbridgeHandler;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomItems;
import org.jetbrains.annotations.Nullable;

public class DrawbridgeBlock extends FacingBlock implements BlockEntityProvider
{
	public static BooleanProperty POWERED = Properties.POWERED;

	public DrawbridgeBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP).with(POWERED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(FACING, POWERED);
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
	{
		super.neighborUpdate(state, world, pos, block, fromPos, notify);
		boolean powered = world.isReceivingRedstonePower(pos);
		if(state.get(POWERED) && !powered)
		{
			world.setBlockState(pos, state.with(POWERED, false));
		}
		else if(!state.get(POWERED) && powered)
		{
			world.setBlockState(pos, state.with(POWERED, true));
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if(!world.isClient)
		{
			player.openHandledScreen(new NamedScreenHandlerFactory()
			{
				@Override
				public Text getDisplayName()
				{
					return new TranslatableText("block.watersrandom.drawbridge");
				}

				@Nullable
				@Override
				public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
				{
					if(world.getBlockEntity(pos) instanceof DrawbridgeBlockEntity drawbridge)
					{
						return new DrawbridgeHandler(syncId, inv, drawbridge);
					}
					return null;
				}
			});
		}
		return ActionResult.success(world.isClient);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, pos, state, player);
		if(!world.isClient)
		{
			if(world.getBlockEntity(pos) instanceof DrawbridgeBlockEntity drawbridge)
			{
				ItemScatterer.spawn(world, pos, drawbridge.getInventory());
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new DrawbridgeBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
	{
		return world.isClient ? null : RandomBlockEntities.checkType(type, RandomBlockEntities.DRAWBRIDGE, DrawbridgeBlockEntity::tick);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		if(ctx.getPlayer() != null)
		{
			return getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
		}
		return super.getPlacementState(ctx);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		if(world.getBlockEntity(pos) instanceof DrawbridgeBlockEntity drawbridge)
		{
			ItemStack stack = drawbridge.getCamouflage();
			if(!stack.isEmpty() && !stack.isOf(RandomItems.DRAWBRIDGE))
			{
				if(stack.getItem() instanceof BlockItem block)
				{
					return drawbridge.getCamouflageState().getOutlineShape(world, pos, context);
				}
			}
		}
		return super.getOutlineShape(state, world, pos, context);
	}
}
