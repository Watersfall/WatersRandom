package net.watersfall.random.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.watersfall.random.hooks.Hooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin
{
	@Inject(method = "wearsGoldArmor",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"),
			locals = LocalCapture.CAPTURE_FAILHARD,
			cancellable = true)
	private static void watersrandom$piglinGoldArmorCheck(LivingEntity entity,
														  CallbackInfoReturnable<Boolean> info,
														  Iterable<ItemStack> iterable,
														  Iterator<ItemStack> iterator,
														  ItemStack stack,
														  Item item
	)
	{
		Hooks.checkWearingGoldArmor(stack, info);
	}
}
