package net.watersfall.random.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.watersfall.random.registry.RandomItems;

public class FireMelonItem extends Item
{
	public FireMelonItem()
	{
		super(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.MELON_SLICE));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if(!world.isClient && user instanceof PlayerEntity player)
		{
			if(world.random.nextFloat() < 0.6F)
			{
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 160, 1, false, true));
			}
			if(world.random.nextFloat() < 0.75F)
			{
				player.setFireTicks(100 + (int)(world.random.nextFloat() * 200));
			}
			if(world.random.nextDouble() < 0.05)
			{
				ItemStack seeds = RandomItems.FIRE_MELON_SEEDS.getDefaultStack();
				if(!player.getInventory().insertStack(seeds))
				{
					player.dropItem(seeds, true);
				}
			}
		}
		return super.finishUsing(stack, world, user);
	}
}
