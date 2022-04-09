package net.watersfall.random.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.watersfall.random.registry.RandomBlockEntities;
import org.jetbrains.annotations.Nullable;

public class AnimatedJukeboxBlockEntity extends JukeboxBlockEntity implements BlockEntityClientSerializable
{
	public AnimatedJukeboxBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state);
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
		return toClientTag(new NbtCompound());
	}

	@Override
	public NbtCompound toClientTag(NbtCompound nbt)
	{
		nbt.put("item", getRecord().writeNbt(new NbtCompound()));
		return nbt;
	}

	@Override
	public void fromClientTag(NbtCompound nbt)
	{
		setRecord(ItemStack.fromNbt(nbt.getCompound("item")));
	}

	@Override
	public void setRecord(ItemStack stack)
	{
		super.setRecord(stack);
		if(this.world != null && !this.world.isClient)
		{
			this.sync();
		}
	}

	@Override
	public BlockEntityType<?> getType()
	{
		return RandomBlockEntities.ANIMATED_JUKEBOX;
	}
}
