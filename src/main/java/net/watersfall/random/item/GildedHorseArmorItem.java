package net.watersfall.random.item;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.Identifier;
import net.watersfall.random.WatersRandom;

public class GildedHorseArmorItem extends HorseArmorItem
{
	private final Identifier texture;

	public GildedHorseArmorItem(int bonus, String name, Settings settings)
	{
		super(bonus, name, settings);
		this.texture = WatersRandom.getId(super.getEntityTexture().getPath());
	}

	@Override
	public Identifier getEntityTexture()
	{
		return texture;
	}
}
