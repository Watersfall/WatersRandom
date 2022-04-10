package net.watersfall.random.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watersfall.random.block.entity.BlockEntityClientSerializable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin extends BlockEntity
{
	@Shadow protected abstract void writeNbt(NbtCompound nbt);

	public BrewingStandBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket()
	{
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt()
	{
		NbtCompound nbt = new NbtCompound();
		this.writeNbt(nbt);
		return nbt;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		BlockEntityClientSerializable.sync(this);
	}

	@Inject(method = "craft", at = @At("TAIL"))
	private static void watersrandom$syncBrewingStandOnCraft(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if(blockEntity != null)
		{
			BlockEntityClientSerializable.sync(blockEntity);
		}
	}
}
