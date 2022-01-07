package net.watersfall.random.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.registry.RandomBlocks;
import net.watersfall.random.registry.RandomRecipes;

public class ProjectTableRecipe extends ShapedRecipe
{
	public ProjectTableRecipe(ShapedRecipe recipe)
	{
		super(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getOutput());
	}

	@Override
	public ItemStack craft(CraftingInventory inventory)
	{
		ItemStack stack = new ItemStack(RandomBlocks.PROJECT_TABLE);
		String id = Registry.ITEM.getId(inventory.getStack(0).getItem()).toString();
		stack.getOrCreateNbt().putString("block", id);
		return stack;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return RandomRecipes.PROJECT_TABLE_RECIPE_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<ProjectTableRecipe>
	{
		@Override
		public ProjectTableRecipe read(Identifier id, JsonObject json)
		{
			return new ProjectTableRecipe(RecipeSerializer.SHAPED.read(id, json));
		}

		@Override
		public ProjectTableRecipe read(Identifier id, PacketByteBuf buf)
		{
			return new ProjectTableRecipe(RecipeSerializer.SHAPED.read(id, buf));
		}

		@Override
		public void write(PacketByteBuf buf, ProjectTableRecipe recipe)
		{
			RecipeSerializer.SHAPED.write(buf, recipe);
		}
	}
}
