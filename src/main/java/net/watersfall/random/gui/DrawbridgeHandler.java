package net.watersfall.random.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.watersfall.random.block.entity.DrawbridgeBlockEntity;
import net.watersfall.random.registry.RandomScreenHandlers;

public class DrawbridgeHandler extends ScreenHandler
{
	private final DrawbridgeBlockEntity drawbridge;

	public DrawbridgeHandler(int syncId, PlayerInventory playerInventory)
	{
		this(syncId, playerInventory, new DrawbridgeBlockEntity(BlockPos.ORIGIN, null));
	}

	public DrawbridgeHandler(int syncId, PlayerInventory playerInventory, DrawbridgeBlockEntity drawbridge)
	{
		super(RandomScreenHandlers.DRAWBRIDGE, syncId);

		this.drawbridge = drawbridge;

		this.addSlot(new Slot(drawbridge.getInventory(), 0, 80, 20) {
			@Override
			public boolean canInsert(ItemStack stack)
			{
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getMaxItemCount()
			{
				return 16;
			}
		});
		this.addSlot(new Slot(drawbridge.getInventory(), 1, 134, 20) {
			@Override
			public boolean canInsert(ItemStack stack)
			{
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getMaxItemCount()
			{
				return 1;
			}

			@Override
			public void markDirty()
			{
				super.markDirty();
				if(drawbridge.getWorld() != null && !drawbridge.getWorld().isClient)
				{
					drawbridge.sync();
					drawbridge.markDirty();
				}
			}
		});

		//Player Inventory
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 51 + y * 18));
			}
		}
		for (int y = 0; y < 9; ++y)
		{
			this.addSlot(new Slot(playerInventory, y, 8 + y * 18, 109));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

	@Override
	public void close(PlayerEntity player)
	{
		if(!player.getEntityWorld().isClient)
		{
			drawbridge.sync();
		}
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index)
	{
		Slot slot = slots.get(index);
		if(slot.hasStack())
		{
			ItemStack stack = slot.getStack();
			if(index >= 0 && index < 2)
			{
				if(!this.insertItem(stack, 2, 38, true))
				{
					slot.markDirty();
					return ItemStack.EMPTY;
				}
				slot.markDirty();
			}
			else if(index < 39)
			{
				if(stack.getItem() instanceof BlockItem)
				{
					Slot input = slots.get(0);
					if(input.canInsert(stack) && input.getStack().getCount() < input.getMaxItemCount() && (input.getStack().isEmpty() || ItemStack.canCombine(stack, input.getStack())))
					{
						input.insertStack(stack);
						return ItemStack.EMPTY;
					}
					Slot camo = slots.get(1);
					if(camo.canInsert(stack) && !camo.hasStack())
					{
						camo.insertStack(stack);
						camo.markDirty();
						return ItemStack.EMPTY;
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
