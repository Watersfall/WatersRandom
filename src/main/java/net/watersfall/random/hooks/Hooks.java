package net.watersfall.random.hooks;

import net.minecraft.item.ItemStack;
import net.watersfall.random.WatersRandom;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Hooks
{
	public static void checkWearingGoldArmor(ItemStack stack, CallbackInfoReturnable<Boolean> info)
	{
		if(stack.isIn(WatersRandom.GOLD_ARMORS))
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
}
