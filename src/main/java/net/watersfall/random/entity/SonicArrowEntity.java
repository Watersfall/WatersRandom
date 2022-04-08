package net.watersfall.random.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.watersfall.random.registry.RandomEntities;

public class SonicArrowEntity extends ArrowEntity
{
	private boolean hit = false;
	private int hitTime = 0;

	public SonicArrowEntity(EntityType<SonicArrowEntity> type, World world)
	{
		super(type, world);
	}

	public SonicArrowEntity(World world, LivingEntity owner)
	{
		this(world, owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ());
		this.setOwner(owner);
	}

	public SonicArrowEntity(World world, double x, double y, double z)
	{
		this(RandomEntities.SONIC_ARROW, world);
		this.setPosition(x, y, z);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		ArrowEntity arrow = new ArrowEntity(world, getX(), getY(), getZ());
		arrow.setVelocity(this.getVelocity());
		arrow.setId(this.getId());
		arrow.setUuid(this.getUuid());
		arrow.setOwner(this.getOwner());
		Entity entity = this.getOwner();
		return new EntitySpawnS2CPacket(arrow, entity == null ? 0 : entity.getId());
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult)
	{
		this.setVelocity(this.getVelocity().multiply(-0.1D));
		this.setYaw(this.getYaw() + 180.0F);
		this.prevYaw += 180.0F;
		if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
			if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
				this.dropStack(this.asItemStack(), 0.1F);
			}

			this.discard();
		}
		this.hit = true;
	}

	@Override
	public void tick()
	{
		super.tick();
		if(this.inGround)
		{
			this.hit = true;
		}
		if(hit)
		{
			hitTime++;
		}
		if(hit && hitTime <= 120)
		{
			world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(9), entity -> true).forEach(entity -> {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5), this);
			});
		}
		else if(hitTime > 120)
		{
			this.discard();
		}
	}


}
