package net.watersfall.random.registry;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.recipe.WoodArmorRecipe;

public class RandomRecipes
{
	public static WoodArmorRecipe.Serializer WOOD_ARMOR_SERIALIZER;

	public static void register()
	{
		WOOD_ARMOR_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, WatersRandom.getId("wood_armor"), new WoodArmorRecipe.Serializer());
	}
}
