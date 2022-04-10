package net.watersfall.random.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.watersfall.random.registry.RandomRecipes;

public class WoodArmorRecipe implements CraftingRecipe
{
	private final ShapedRecipe recipe;

	public WoodArmorRecipe(ShapedRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public boolean matches(CraftingInventory inventory, World world)
	{
		boolean matches = recipe.matches(inventory, world);
		if(matches)
		{
			ItemStack first = null;
			for(int i = 0; i < inventory.size(); i++)
			{
				if(!inventory.getStack(i).isEmpty())
				{
					if(first == null)
					{
						first = inventory.getStack(i);
					}
					else if(!first.isItemEqual(inventory.getStack(i)))
					{
						return false;
					}
				}
			}
		}
		return matches;
	}

	@Override
	public ItemStack craft(CraftingInventory inventory)
	{
		ItemStack stack = recipe.craft(inventory);
		for(int i = 0; i < inventory.size(); i++)
		{
			if(!inventory.getStack(i).isEmpty())
			{
				Identifier id = Registry.ITEM.getId(inventory.getStack(i).getItem());
				stack.getOrCreateNbt().putString("type", id.toString());
				break;
			}
		}
		return stack;
	}

	@Override
	public boolean fits(int width, int height)
	{
		return recipe.fits(width, height);
	}

	@Override
	public ItemStack getOutput()
	{
		return recipe.getOutput();
	}

	@Override
	public Identifier getId()
	{
		return recipe.getId();
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return RandomRecipes.WOOD_ARMOR_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<WoodArmorRecipe>
	{
		@Override
		public WoodArmorRecipe read(Identifier id, JsonObject json)
		{
			return new WoodArmorRecipe(RecipeSerializer.SHAPED.read(id, json));
		}

		@Override
		public WoodArmorRecipe read(Identifier id, PacketByteBuf buf)
		{
			return new WoodArmorRecipe(RecipeSerializer.SHAPED.read(id, buf));
		}

		@Override
		public void write(PacketByteBuf buf, WoodArmorRecipe recipe)
		{
			RecipeSerializer.SHAPED.write(buf, recipe.recipe);
		}
	}
}
