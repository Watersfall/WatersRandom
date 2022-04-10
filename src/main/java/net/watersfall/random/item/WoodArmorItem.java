package net.watersfall.random.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WoodArmorItem extends ArmorItem
{
	public WoodArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings)
	{
		super(material, slot, settings);
	}

	@Override
	public Text getName(ItemStack stack)
	{
		ItemStack log = Items.OAK_LOG.getDefaultStack();
		if(stack.hasNbt() && stack.getNbt().contains("type"))
		{
			Identifier id = Identifier.tryParse(stack.getNbt().getString("type"));
			Item check = Registry.ITEM.get(id);
			if(check != Items.AIR)
			{
				log = check.getDefaultStack();
			}
		}
		return new TranslatableText(this.getTranslationKey(), log.getName());
	}
}
