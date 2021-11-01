package net.watersfall.random.block;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class WatersAttachedStemBlock extends AttachedStemBlock
{
	public WatersAttachedStemBlock(GourdBlock gourdBlock, Supplier<Item> pickBlockItem, Settings settings)
	{
		super(gourdBlock, pickBlockItem, settings);
	}
}
