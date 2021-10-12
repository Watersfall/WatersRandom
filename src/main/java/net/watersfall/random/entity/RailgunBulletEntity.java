package net.watersfall.random.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.item.RailgunItem;
import net.watersfall.random.registry.RandomEntities;

public class RailgunBulletEntity extends ThrownItemEntity
{
	public static float MAX_RANGE = 50;

	private RailgunItem.RailgunAmmo ammo;

	public RailgunBulletEntity(EntityType<RailgunBulletEntity> type, World world)
	{
		super(type, world);
	}

	public RailgunBulletEntity(LivingEntity owner, World world)
	{
		super(RandomEntities.RAILGUN_BULLET, owner, world);
		this.setNoGravity(true);
	}

	@Override
	protected Item getDefaultItem()
	{
		return Items.IRON_NUGGET;
	}

	public RailgunItem.RailgunAmmo getAmmo()
	{
		return ammo;
	}

	public void setAmmo(RailgunItem.RailgunAmmo ammo)
	{
		this.ammo = ammo;
	}

	@Override
	protected void updateRotation()
	{

	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt)
	{
		super.readCustomDataFromNbt(nbt);
		this.setAmmo(RailgunItem.RailgunAmmo.get(this.getStack().getItem()));
	}

	@Override
	public void tick()
	{
		Vec3d prev = this.getPos();
		super.tick();
		Vec3d current = this.getPos();
		this.distanceTraveled += Math.abs(prev.distanceTo(current));
		if(this.distanceTraveled > MAX_RANGE)
		{
			this.discard();
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult hit)
	{
		super.onEntityHit(hit);
		if(ammo != null && hit.getEntity() instanceof LivingEntity target)
		{
			if(!world.isClient)
			{
				target.damage(DamageSource.GENERIC, ammo.damage());
				this.discard();
			}
			target.takeKnockback(ammo.knockback(), -getVelocity().x, -getVelocity().z);
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult hit)
	{
		super.onBlockHit(hit);
		BlockState state = world.getBlockState(hit.getBlockPos());
		if(state.getMaterial().isSolid())
		{
			this.discard();
		}
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.getType()));
		buf.writeUuid(this.getUuid());
		buf.writeVarInt(this.getId());
		buf.writeDouble(this.getPos().x);
		buf.writeDouble(this.getPos().y);
		buf.writeDouble(this.getPos().z);
		buf.writeFloat(this.getPitch());
		buf.writeFloat(this.getYaw());
		buf.writeDouble(this.getVelocity().x);
		buf.writeDouble(this.getVelocity().y);
		buf.writeDouble(this.getVelocity().z);
		buf.writeVarInt(this.getOwner() != null ? this.getOwner().getId() : -1);
		buf.writeItemStack(this.getStack());
		return ServerPlayNetworking.createS2CPacket(WatersRandom.getId("spawn_packet"), buf);
	}
}
