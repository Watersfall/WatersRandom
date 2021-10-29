package net.watersfall.random.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.watersfall.random.registry.RandomScreenHandlers;

public class TinyChestScreenHandler extends ScreenHandler
{
	private final boolean tiny;
	private final Inventory inventory;

	public TinyChestScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf)
	{
		this(syncId, inventory, new SimpleInventory(9 * 12), buf.readBoolean());
	}

	public TinyChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, boolean tiny)
	{
		super(RandomScreenHandlers.TINY_CHEST, syncId);
		this.inventory = inventory;
		this.tiny = tiny;
		inventory.onOpen(playerInventory.player);
		if(isTiny())
		{
			this.addSlot(new Slot(inventory, 0, 80, 20));
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
		else
		{
			for(int y = 0; y < 9; y++)
			{
				for(int x = 0; x < 12; x++)
				{
					this.addSlot(new Slot(inventory, x + y * 12, 8 + x * 18, 17 + y * 18));
				}
			}
			for (int y = 0; y < 3; ++y)
			{
				for (int x = 0; x < 9; ++x)
				{
					this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 35 + x * 18, 191 + y * 18));
				}
			}
			for (int y = 0; y < 9; ++y)
			{
				this.addSlot(new Slot(playerInventory, y, 35 + y * 18, 249));
			}
		}
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index)
	{
		Slot slot = slots.get(index);
		if(slot.hasStack())
		{
			if(isTiny())
			{
				if(index == 0)
				{
					if(!insertItem(slot.getStack(), 1, 37, true))
					{
						return ItemStack.EMPTY;
					}
					slot.markDirty();
				}
				else
				{
					if(!insertItem(slot.getStack(), 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
					slot.markDirty();;
				}
			}
			else
			{
				if(index < 108)
				{
					if(!insertItem(slot.getStack(), 108, 108 + 36, true))
					{
						return ItemStack.EMPTY;
					}
					slot.markDirty();
				}
				else
				{
					if(!insertItem(slot.getStack(), 0, 108, false))
					{
						return ItemStack.EMPTY;
					}
					slot.markDirty();;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

	public boolean isTiny()
	{
		return tiny;
	}

	@Override
	public void close(PlayerEntity player)
	{
		super.close(player);
		inventory.onClose(player);
	}
}
