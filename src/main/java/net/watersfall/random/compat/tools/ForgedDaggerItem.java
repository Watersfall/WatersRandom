package net.watersfall.random.compat.tools;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.watersfall.tools.api.abilities.item.ToolItemAbility;
import net.watersfall.tools.api.item.ForgedToolItem;
import net.watersfall.tools.item.ForgedSwordItem;
import net.watersfall.tools.tool.ToolHead;
import net.watersfall.tools.tool.ToolMaterial;
import net.watersfall.wet.api.abilities.AbilityProvider;

import java.util.Optional;

public class ForgedDaggerItem extends ForgedSwordItem implements ForgedToolItem
{
	@Override
	public Text getName(ItemStack stack)
	{
		Optional<ToolItemAbility> ability = AbilityProvider.getAbility(stack, ToolItemAbility.ID, ToolItemAbility.class);
		if (ability.isPresent())
		{
			ToolMaterial material = ToolMaterial.get(ToolHead.getId((ability.get()).getHead()));
			return new TranslatableText("item.watersrandom.dagger", new TranslatableText(material.getTranslationKey()));
		}
		else
		{
			return super.getName(stack);
		}
	}

	@Override
	public float getBaseAttackSpeed()
	{
		return -1;
	}

	@Override
	public float getBaseAttackDamage()
	{
		return 1F;
	}
}
