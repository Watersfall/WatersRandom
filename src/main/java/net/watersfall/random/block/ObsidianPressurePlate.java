package net.watersfall.random.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ObsidianPressurePlate extends RandomPressurePlate
{
	public ObsidianPressurePlate(boolean silent, boolean visible, Settings settings)
	{
		super(silent, visible, settings);
	}

	@Override
	protected int getRedstoneOutput(World world, BlockPos pos)
	{
		Box box = BOX.offset(pos);
		List<PlayerEntity> entities = world.getEntitiesByClass(PlayerEntity.class, box, (entity) -> true);
		if (!entities.isEmpty()) {
			for(PlayerEntity entity : entities)
			{
				if (!entity.canAvoidTraps())
				{
					return 15;
				}
			}
		}
		return 0;
	}
}
