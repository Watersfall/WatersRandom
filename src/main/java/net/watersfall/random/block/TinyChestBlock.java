package net.watersfall.random.block;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.watersfall.random.block.entity.TinyChestBlockEntity;
import net.watersfall.random.gui.TinyChestScreenHandler;
import net.watersfall.random.registry.RandomBlockEntities;

public class TinyChestBlock extends AbstractChestBlock<TinyChestBlockEntity> implements Waterloggable
{
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty TINY = BooleanProperty.of("tiny");
	public static final VoxelShape OUTLINE = Block.createCuboidShape(4, 0.0, 4, 12, 8.0, 12);

	public TinyChestBlock(Settings settings)
	{
		super(settings, () -> RandomBlockEntities.TINY_CHEST);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(TINY, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		super.appendProperties(builder);
		builder.add(FACING, WATERLOGGED, TINY);
	}

	@Override
	public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked)
	{
		return DoubleBlockProperties.PropertyRetriever::getFallback;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if(player.getStackInHand(hand).isOf(Items.NETHER_STAR))
		{
			if(state.get(TINY))
			{
				if(!world.isClient)
				{
					if(world.getBlockEntity(pos) instanceof TinyChestBlockEntity entity)
					{
						entity.upgrade();
					}
					Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, player.getStackInHand(hand));
					world.setBlockState(pos, state.with(TINY, false));
				}
				return ActionResult.success(world.isClient);
			}
		}
		if(!world.isClient)
		{
			player.openHandledScreen(this.createScreenHandlerFactory(state, world, pos));
		}
		return ActionResult.success(world.isClient);
	}

	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos)
	{
		BlockEntity test = world.getBlockEntity(pos);
		if(test instanceof Inventory inventory)
		{
			return new ExtendedScreenHandlerFactory()
			{
				@Override
				public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf)
				{
					buf.writeBoolean(state.get(TINY));
				}

				@Override
				public Text getDisplayName()
				{
					return TinyChestBlock.this.getName();
				}

				@Override
				public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
				{
					return new TinyChestScreenHandler(syncId, inv, inventory, state.get(TINY));
				}
			};
		}
		return null;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return this.getDefaultState()
				.with(FACING, ctx.getPlayerFacing().getOpposite())
				.with(WATERLOGGED, fluidState.isEqualAndStill(Fluids.WATER));
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock()))
		{
			BlockEntity test = world.getBlockEntity(pos);
			if (test instanceof Inventory inventory)
			{
				ItemScatterer.spawn(world, pos, inventory);
				world.updateComparators(pos, this);
			}
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if(state.get(WATERLOGGED))
		{
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return OUTLINE;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new TinyChestBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
	{
		return RandomBlockEntities.checkType(type, RandomBlockEntities.TINY_CHEST, world.isClient ? TinyChestBlockEntity::clientTick : null);
	}
}
