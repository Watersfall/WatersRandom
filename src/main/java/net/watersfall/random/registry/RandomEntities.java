package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.entity.RailgunBulletEntity;

public class RandomEntities
{
	public static EntityType<RailgunBulletEntity> RAILGUN_BULLET;

	public static void register()
	{
		RAILGUN_BULLET = Registry.register(Registry.ENTITY_TYPE, WatersRandom.getId("railgun_bullet"), FabricEntityTypeBuilder
				.<RailgunBulletEntity>create(SpawnGroup.MISC)
				.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
				.trackedUpdateRate(10)
				.trackRangeBlocks(4)
				.fireImmune()
				.entityFactory(RailgunBulletEntity::new)
				.build()
		);
	}
}
