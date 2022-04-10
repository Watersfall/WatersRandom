package net.watersfall.random.block;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class MossyCobblestonePressurePlate extends RandomPressurePlate
{
	public MossyCobblestonePressurePlate(boolean silent, boolean visible, Settings settings)
	{
		super(silent, visible, settings);
	}

	@Override
	protected int getRedstoneOutput(World world, BlockPos pos)
	{
		Box box = BOX.offset(pos);
		List<HostileEntity> entities = world.getEntitiesByClass(HostileEntity.class, box, (entity) -> true);
		if (!entities.isEmpty()) {
			for(HostileEntity entity : entities)
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
