package net.watersfall.random.registry;

import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.recipe.ProjectTableRecipe;
import net.watersfall.random.recipe.WoodArmorRecipe;

public class RandomRecipes
{
	public static WoodArmorRecipe.Serializer WOOD_ARMOR_SERIALIZER;
	public static ProjectTableRecipe.Serializer PROJECT_TABLE_RECIPE_SERIALIZER;

	public static void register()
	{
		WOOD_ARMOR_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, WatersRandom.getId("wood_armor"), new WoodArmorRecipe.Serializer());
		PROJECT_TABLE_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, WatersRandom.getId("project_table"), new ProjectTableRecipe.Serializer());
	}
}
