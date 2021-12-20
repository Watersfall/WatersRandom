package net.watersfall.random.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.watersfall.random.block.DrawbridgeBlock;
import net.watersfall.random.registry.RandomBlockEntities;

public class DrawbridgeBlockEntity extends SyncableBlockEntity
{
	public static final int MAX_LENGTH = 16;

	private Inventory inventory = new SimpleInventory(2);
	private int placedBlocks = 0;
	private ItemStack placedStack = ItemStack.EMPTY;

	public DrawbridgeBlockEntity(BlockPos pos, BlockState state)
	{
		super(RandomBlockEntities.DRAWBRIDGE, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		DefaultedList<ItemStack> list = DefaultedList.ofSize(2, ItemStack.EMPTY);
		Inventories.readNbt(nbt, list);
		inventory.setStack(0, list.get(0));
		inventory.setStack(1, list.get(1));
		this.placedBlocks = nbt.getInt("placed_blocks");
		this.placedStack = ItemStack.fromNbt(nbt.getCompound("last_placed"));
	}

	@Override
	public void writeNbt(NbtCompound nbt)
	{
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, DefaultedList.copyOf(ItemStack.EMPTY, inventory.getStack(0), inventory.getStack(1)));
		nbt.putInt("placed_blocks", placedBlocks);
		nbt.put("last_placed", placedStack.writeNbt(new NbtCompound()));
	}

	@Override
	public void fromClientTag(NbtCompound tag)
	{
		this.setCamouflage(new ItemStack(Registry.ITEM.get(tag.getInt("item"))));
	}

	@Override
	public NbtCompound toClientTag(NbtCompound tag)
	{
		tag.putInt("item", Registry.ITEM.getRawId(getCamouflage().getItem()));
		return tag;
	}

	public boolean canPlaceBlock()
	{
		return getPlacedBlocks() < MAX_LENGTH && !getPlacingStack().isEmpty();
	}

	public boolean canRemoveBlock()
	{
		return getPlacedBlocks() > 0 && !getPlacedStack().isEmpty();
	}

	public static void tick(World world, BlockPos pos, BlockState state, DrawbridgeBlockEntity entity)
	{
		if(state.get(DrawbridgeBlock.POWERED))
		{
			if(entity.canPlaceBlock())
			{
				Direction direction = state.get(FacingBlock.FACING);
				BlockPos placePos = pos.offset(direction, entity.getPlacedBlocks() + 1);
				BlockState replaceState = world.getBlockState(placePos);
				if(replaceState.getMaterial().isReplaceable())
				{
					ItemPlacementContext placement = new AutomaticItemPlacementContext(world, placePos, direction, entity.getPlacingStack(), direction);
					BlockState placeState = ((BlockItem)entity.getPlacingStack().getItem()).getBlock().getPlacementState(placement);
					world.setBlockState(placePos, placeState);
					entity.setPlacedBlocks(entity.getPlacedBlocks() + 1);
					entity.setPlacedStack(new ItemStack(entity.getPlacingStack().getItem(), entity.getPlacedBlocks()));
					entity.getPlacingStack().decrement(1);
					entity.markDirty();
				}
			}
		}
		else
		{
			if(entity.canRemoveBlock())
			{
				Direction direction = state.get(FacingBlock.FACING);
				BlockPos placePos = pos.offset(direction, entity.getPlacedBlocks());
				BlockState replaceState = world.getBlockState(placePos);
				if(replaceState.isOf(((BlockItem)entity.getPlacedStack().getItem()).getBlock()))
				{
					world.removeBlock(placePos, false);
					if(entity.getPlacingStack().isEmpty())
					{
						entity.setPlacingStack(new ItemStack(entity.getPlacedStack().getItem()));
					}
					else
					{
						entity.getPlacingStack().increment(1);
					}
					if(entity.getPlacedBlocks() == 0)
					{
						entity.setPlacedStack(ItemStack.EMPTY);
					}
					entity.markDirty();
				}
				entity.setPlacedBlocks(entity.getPlacedBlocks() - 1);
			}
		}
	}

	public Inventory getInventory()
	{
		return inventory;
	}

	public int getPlacedBlocks()
	{
		return placedBlocks;
	}

	public void setPlacedBlocks(int placedBlocks)
	{
		this.placedBlocks = placedBlocks;
	}

	public ItemStack getPlacingStack()
	{
		return inventory.getStack(0);
	}

	public void setPlacingStack(ItemStack placingStack)
	{
		inventory.setStack(0, placingStack);
	}

	public ItemStack getPlacedStack()
	{
		return placedStack;
	}

	public void setPlacedStack(ItemStack placedStack)
	{
		this.placedStack = placedStack;
	}

	public ItemStack getCamouflage()
	{
		return inventory.getStack(1);
	}

	public void setCamouflage(ItemStack camouflage)
	{
		inventory.setStack(1, camouflage);
	}

	public BlockState getCamouflageState()
	{
		BlockState state = this.getCachedState();
		if(!this.getCamouflage().isEmpty() && this.getCamouflage().getItem() instanceof BlockItem block)
		{
			state = block.getBlock().getDefaultState();
			Direction direction = this.getCachedState().get(FacingBlock.FACING);
			if(state.contains(FacingBlock.FACING))
			{
				state = state.with(FacingBlock.FACING, direction);
			}
			else if(state.contains(HorizontalFacingBlock.FACING) && !direction.getAxis().isVertical())
			{
				state = state.with(HorizontalFacingBlock.FACING, direction);
			}
			if(state.contains(Properties.POWERED))
			{
				state = state.with(Properties.POWERED, this.getCachedState().get(Properties.POWERED));
			}
			if(state.contains(Properties.AXIS))
			{
				state = state.with(Properties.AXIS, direction.getAxis());
			}
			else if(state.contains(Properties.HORIZONTAL_AXIS) && direction.getAxis().isHorizontal())
			{
				state = state.with(Properties.HORIZONTAL_AXIS, direction.getAxis());
			}
		}
		return state;
	}
}
