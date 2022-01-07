package net.watersfall.random.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.gui.ProjectTableScreenHandler;
import net.watersfall.random.inventory.BasicInventory;
import net.watersfall.random.registry.RandomBlockEntities;
import net.watersfall.random.registry.RandomBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ProjectTableBlockEntity extends SyncableBlockEntity implements NamedScreenHandlerFactory, BasicInventory
{
	private final DefaultedList<ItemStack> contents = DefaultedList.ofSize(28, ItemStack.EMPTY);
	private final Set<ProjectTableScreenHandler> openScreens = new HashSet<>();
	private Block block;

	@Environment(EnvType.CLIENT)
	private BlockState renderState = Blocks.OAK_PLANKS.getDefaultState();


	public ProjectTableBlockEntity(BlockPos pos, BlockState state)
	{
		super(RandomBlockEntities.PROJECT_TABLE, pos, state);
	}

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText(RandomBlocks.PROJECT_TABLE.getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new ProjectTableScreenHandler(syncId, inv, this, ScreenHandlerContext.create(world, pos));
	}

	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		Inventories.readNbt(nbt, contents);
		setBlock(nbt.getString("block"));
	}

	@Override
	protected void writeNbt(NbtCompound nbt)
	{
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, contents);
		nbt.putString("block", Registry.BLOCK.getId(block).toString());
	}

	@Override
	public NbtCompound toClientTag(NbtCompound nbt)
	{
		nbt.putString("block", Registry.BLOCK.getId(block).toString());
		return nbt;
	}

	@Override
	public void fromClientTag(NbtCompound nbt)
	{
		renderState = Registry.BLOCK.get(new Identifier(nbt.getString("block"))).getDefaultState();
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
	}

	@Override
	public DefaultedList<ItemStack> getContents()
	{
		return contents;
	}

	public void setBlock(String id)
	{
		this.block = Registry.BLOCK.get(new Identifier(id));
		if(this.block.getDefaultState().isAir())
		{
			this.block = Blocks.OAK_PLANKS;
		}
	}

	@Environment(EnvType.CLIENT)
	public void setRenderState(String id)
	{
		setBlock(id);
		renderState = block.getDefaultState();
	}

	@Environment(EnvType.CLIENT)
	public BlockState getRenderState()
	{
		return renderState;
	}
}
