package net.watersfall.random.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.watersfall.random.registry.RandomItems;

public class MelonItem extends Item
{
	private final StatusEffect first, second;
	private final Item seeds;

	public MelonItem(StatusEffect first, StatusEffect second, Item seeds)
	{
		super(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.MELON_SLICE));
		this.first = first;
		this.second = second;
		this.seeds = seeds;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if(!world.isClient && user instanceof PlayerEntity player)
		{
			if(world.random.nextFloat() < 0.6F)
			{
				player.addStatusEffect(new StatusEffectInstance(first, 160, 1, false, true));
				if(world.random.nextFloat() < 0.15F)
				{
					player.addStatusEffect(new StatusEffectInstance(second, 80, 2, false, true));
				}
			}

			if(world.random.nextDouble() < 0.05)
			{
				ItemStack seeds = this.seeds.getDefaultStack();
				if(!player.getInventory().insertStack(seeds))
				{
					player.dropItem(seeds, true);
				}
			}
		}
		return super.finishUsing(stack, world, user);
	}
}
