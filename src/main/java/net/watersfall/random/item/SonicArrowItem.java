package net.watersfall.random.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.watersfall.random.entity.SonicArrowEntity;

public class SonicArrowItem extends ArrowItem
{
	public SonicArrowItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter)
	{
		SonicArrowEntity arrow = new SonicArrowEntity(world, shooter);
		arrow.initFromStack(stack);
		return arrow;
	}
}
