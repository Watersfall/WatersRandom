package net.watersfall.random.block;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.function.Supplier;

public class FireMelonBlock extends WatersMelonBlock
{
	public FireMelonBlock(Supplier<StemBlock> stem, Supplier<AttachedStemBlock> attachedStem, Settings settings)
	{
		super(stem, attachedStem, settings);
	}

	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile)
	{
		super.onProjectileHit(world, state, hit, projectile);
		if(!world.isClient)
		{
			world.setBlockState(hit.getBlockPos(), Blocks.AIR.getDefaultState());
		}
		world.createExplosion(projectile, hit.getPos().x, hit.getPos().y, hit.getPos().z, 1, true, Explosion.DestructionType.NONE);
	}
}
