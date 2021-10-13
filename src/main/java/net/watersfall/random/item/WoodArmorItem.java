package net.watersfall.random.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.watersfall.random.api.ability.WoodArmorAbility;
import net.watersfall.wet.api.abilities.AbilityProvider;

import java.util.Optional;

public class WoodArmorItem extends ArmorItem
{
	public WoodArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings)
	{
		super(material, slot, settings);
	}

	@Override
	public Text getName(ItemStack stack)
	{
		Optional<WoodArmorAbility> optional = AbilityProvider.getAbility(stack, WoodArmorAbility.ID, WoodArmorAbility.class);
		if(optional.isPresent())
		{
			return new TranslatableText(this.getTranslationKey(), optional.get().getStack().getName());
		}
		return super.getName(stack);
	}
}
