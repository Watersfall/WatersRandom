package net.watersfall.random.block;

import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class WatersStemBlock extends StemBlock
{
	public WatersStemBlock(GourdBlock gourdBlock, Supplier<Item> pickBlockItem, Settings settings)
	{
		super(gourdBlock, pickBlockItem, settings);
	}
}
