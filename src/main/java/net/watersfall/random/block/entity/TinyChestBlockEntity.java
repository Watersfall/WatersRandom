package net.watersfall.random.block.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watersfall.random.block.TinyChestBlock;
import net.watersfall.random.gui.TinyChestScreenHandler;
import net.watersfall.random.inventory.BasicInventory;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;

import java.util.ArrayList;
import java.util.List;

public class TinyChestBlockEntity extends BlockEntity implements ChestAnimationProgress, BasicInventory, NamedScreenHandlerFactory
{
	static void playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent)
	{
		double x = pos.getX() + 0.5;
		double y = pos.getY() + 0.5;
		double z = pos.getZ() + 0.5;
		if(soundEvent == SoundEvents.BLOCK_CHEST_OPEN)
		{
			world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 1.9F);
		}
		else
		{
			world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 1.6F);
		}
	}

	private List<PlayerEntity> players;
	private DefaultedList<ItemStack> contents;
	private ChestLidAnimator lidAnimator;
	private final ViewerCountManager stateManager = new ViewerCountManager()
	{
		@Override
		protected void onContainerOpen(World world, BlockPos pos, BlockState state)
		{
			TinyChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_OPEN);
		}

		@Override
		protected void onContainerClose(World world, BlockPos pos, BlockState state)
		{
			TinyChestBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_CLOSE);
		}

		@Override
		protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount)
		{
			TinyChestBlockEntity.this.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
		}

		@Override
		protected boolean isPlayerViewing(PlayerEntity player)
		{
			if(player.currentScreenHandler instanceof GenericContainerScreenHandler screen)
			{
				Inventory inventory = screen.getInventory();
				return inventory == TinyChestBlockEntity.this;
			}
			return false;
		}
	};

	public TinyChestBlockEntity(BlockPos pos, BlockState state)
	{
		super(RandomBlockEntities.TINY_CHEST, pos, state);
		players = new ArrayList<>();
		if(state.get(TinyChestBlock.TINY))
		{
			contents = DefaultedList.ofSize(1, ItemStack.EMPTY);
		}
		else
		{
			contents = DefaultedList.ofSize(9 * 12, ItemStack.EMPTY);
		}
		this.lidAnimator = new ChestLidAnimator();
	}

	public void upgrade()
	{
		while(!players.isEmpty())
		{
			PlayerEntity player = players.get(players.size() - 1);
			if(player.currentScreenHandler instanceof TinyChestScreenHandler && player instanceof ServerPlayerEntity serverPlayer)
			{
				serverPlayer.closeHandledScreen();
			}
		}
		ItemStack stack = contents.get(0);
		contents = DefaultedList.ofSize(9 * 12, ItemStack.EMPTY);
		contents.set(0, stack);
	}

	@Override
	public float getAnimationProgress(float tickDelta)
	{
		return lidAnimator.getProgress(tickDelta);
	}

	@Override
	public Text getDisplayName()
	{
		return RandomBlocks.TINY_CHEST.getName();
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new TinyChestScreenHandler(syncId, inv, this, getCachedState().get(TinyChestBlock.TINY));
	}

	@Override
	public DefaultedList<ItemStack> getContents()
	{
		return contents;
	}

	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		Inventories.readNbt(nbt, contents);
	}

	@Override
	public void writeNbt(NbtCompound nbt)
	{
		Inventories.writeNbt(nbt, contents);
		super.writeNbt(nbt);
	}

	public static void clientTick(World world, BlockPos pos, BlockState state, TinyChestBlockEntity entity)
	{
		entity.lidAnimator.step();
	}

	@Override
	public void onOpen(PlayerEntity player)
	{
		if (!this.removed && !player.isSpectator())
		{
			players.add(player);
			this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
	}

	@Override
	public void onClose(PlayerEntity player)
	{
		if (!this.removed && !player.isSpectator())
		{
			players.remove(player);
			this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
	}

	protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount)
	{
		Block block = state.getBlock();
		world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
	}

	@Override
	public boolean onSyncedBlockEvent(int type, int data)
	{
		if (type == 1)
		{
			this.lidAnimator.setOpen(data > 0);
			return true;
		}
		else
		{
			return super.onSyncedBlockEvent(type, data);
		}
	}
}
