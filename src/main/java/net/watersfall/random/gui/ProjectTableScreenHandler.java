package net.watersfall.random.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watersfall.random.block.entity.ProjectTableBlockEntity;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomScreenHandlers;

import java.util.Optional;

public class ProjectTableScreenHandler extends ScreenHandler
{
	private final CraftingResultInventory output = new CraftingResultInventory();
	private final ScreenHandlerContext context;
	private final CraftingInventory crafting;
	private final PlayerEntity player;
	private final Inventory inventory;

	public ProjectTableScreenHandler(int syncId, PlayerInventory playerInventory)
	{
		this(syncId, playerInventory, new ProjectTableBlockEntity(BlockPos.ORIGIN, RandomBlocks.PROJECT_TABLE.getDefaultState()), ScreenHandlerContext.EMPTY);
	}

	public ProjectTableScreenHandler(int syncId, PlayerInventory playerInventory, ProjectTableBlockEntity inventory, ScreenHandlerContext context)
	{
		super(RandomScreenHandlers.PROJECT_TABLE, syncId);
		this.context = context;
		this.player = playerInventory.player;
		this.crafting = new CraftingInventory(this, 3, 3)
		{
			@Override
			public void setStack(int slot, ItemStack stack)
			{
				inventory.setStack(slot, stack);
				super.setStack(slot, stack);
			}

			@Override
			public ItemStack getStack(int slot)
			{
				return inventory.getStack(slot);
			}

			@Override
			public ItemStack removeStack(int slot)
			{
				return Inventories.removeStack(inventory.getContents(), slot);
			}

			@Override
			public ItemStack removeStack(int slot, int amount)
			{
				ItemStack itemStack = Inventories.splitStack(inventory.getContents(), slot, amount);
				if(!itemStack.isEmpty())
				{
					ProjectTableScreenHandler.this.onContentChanged(this);
				}
				return itemStack;
			}
		};
		this.inventory = inventory;

		this.addSlot(new CraftingResultSlot(playerInventory.player, crafting, inventory, 27, 124, 35));

		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				this.addSlot(new Slot(crafting, x + y * 3, 30 + x * 18, 17 + y * 18));
			}
		}
		for(int y = 1; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlot(new Slot(inventory, x + y * 9, 8 + x * 18, y * 18 + 69));
			}
		}

		//Player Inventory
		for(int y = 0; y < 3; ++y)
		{
			for(int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 133 + y * 18));
			}
		}
		for(int y = 0; y < 9; ++y)
		{
			this.addSlot(new Slot(playerInventory, y, 8 + y * 18, 191));
		}

		for(int i = 0; i < 9; i++)
		{
			crafting.setStack(i, inventory.getStack(i));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

	protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory, Inventory realInventory)
	{
		CraftingRecipe craftingRecipe;
		if(world.isClient)
		{
			return;
		}
		ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
		ItemStack itemStack = ItemStack.EMPTY;
		Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
		if(optional.isPresent() && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe = optional.get()))
		{
			itemStack = craftingRecipe.craft(craftingInventory);
		}
		realInventory.setStack(27, itemStack);
		handler.setPreviousTrackedSlot(0, itemStack);
		serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
	}

	@Override
	public void onContentChanged(Inventory inventory)
	{
		this.context.run((world, pos) -> updateResult(this, world, this.player, this.crafting, this.output, this.inventory));
		super.onContentChanged(this.inventory);
		super.onContentChanged(inventory);
	}


	public ItemStack transferSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if(slot.hasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(index == 0)
			{
				itemstack1.getItem().onCraft(itemstack1, playerIn.world, playerIn);
				if(!insertItem(itemstack1, 28, 63, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(itemstack1, itemstack);
			}
			else if(index >= 1 && index < 9)
			{
				if(!insertItem(itemstack1, 10, 27, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(index >= 10 && index < 27)
			{
				if(!insertItem(itemstack1, 28, 63, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(index >= 28 && index < 64)
			{
				if(!insertItem(itemstack1, 10, 27, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!insertItem(itemstack1, 28, 63, false))
			{
				return ItemStack.EMPTY;
			}

			if(itemstack1.isEmpty())
			{
				slot.setStack(ItemStack.EMPTY);
			}
			else
			{
				slot.markDirty();
			}
			if(itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.markDirty();
		}
		return itemstack;
	}
}
