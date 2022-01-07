package net.watersfall.random.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ProjectTableBlockItem extends BlockItem
{
	public ProjectTableBlockItem(Block block, Settings settings)
	{
		super(block, settings);
	}

	@Override
	public Text getName(ItemStack stack)
	{
		if(stack.getNbt() == null || stack.getNbt().isEmpty() || !stack.getNbt().contains("block"))
		{
			return new TranslatableText(getTranslationKey(stack), Blocks.OAK_PLANKS.getName());
		}
		return new TranslatableText(getTranslationKey(stack), Registry.BLOCK.get(new Identifier(stack.getNbt().getString("block"))).getName());
	}
}
