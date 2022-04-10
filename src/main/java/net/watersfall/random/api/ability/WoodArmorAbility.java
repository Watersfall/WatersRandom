package net.watersfall.random.api.ability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.watersfall.random.WatersRandom;
import net.watersfall.wet.api.abilities.Ability;

public interface WoodArmorAbility extends Ability<ItemStack>
{
	Identifier ID = WatersRandom.getId("wood_armor");

	@Override
	default Identifier getId()
	{
		return ID;
	}

	ItemStack getStack();

	void setStack(ItemStack stack);
}
