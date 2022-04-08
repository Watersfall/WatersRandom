package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.entity.RailgunBulletEntity;
import net.watersfall.random.entity.SonicArrowEntity;

public class RandomEntities
{
	public static EntityType<RailgunBulletEntity> RAILGUN_BULLET;
	public static EntityType<SonicArrowEntity> SONIC_ARROW;

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

		SONIC_ARROW = Registry.register(Registry.ENTITY_TYPE, WatersRandom.getId("sonic_arrow"), FabricEntityTypeBuilder
				.<SonicArrowEntity>create(SpawnGroup.MISC)
				.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
				.trackRangeBlocks(4)
				.trackedUpdateRate(20)
				.entityFactory(SonicArrowEntity::new)
				.build()
		);
	}
}
