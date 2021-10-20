package net.watersfall.random.hooks;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.watersfall.random.WatersRandom;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

public class Hooks
{
	private static final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

	static
	{
		modifiers = ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
				.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "attack damage", 3, EntityAttributeModifier.Operation.ADDITION))
				.build();
	}

	public static void checkWearingGoldArmor(ItemStack stack, CallbackInfoReturnable<Boolean> info)
	{
		if(stack.isIn(WatersRandom.GOLD_ARMORS))
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}

	public static Multimap<EntityAttribute, EntityAttributeModifier> getArrowModifiers(EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> other)
	{
		if(slot == EquipmentSlot.MAINHAND)
		{
			return modifiers;
		}
		return other;
	}

	public static void afterArrowMeleeHit(ItemStack stack, LivingEntity attacker)
	{
		if(attacker.world.random.nextDouble() > 0.9)
		{
			stack.decrement(1);
			attacker.world.playSound(null, attacker.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}

	public static void giveMelonSeeds(Entity entity, ItemStack stack)
	{
		if(entity instanceof PlayerEntity player)
		{
			if(stack.isOf(Items.MELON_SLICE))
			{
				if(entity.world.random.nextDouble() < 0.05)
				{
					ItemStack seeds = Items.MELON_SEEDS.getDefaultStack();
					if(!player.getInventory().insertStack(seeds))
					{
						player.dropItem(seeds, true);
					}
				}
			}
		}
	}
}
