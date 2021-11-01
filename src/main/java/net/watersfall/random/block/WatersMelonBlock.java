package net.watersfall.random.block;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;

import java.util.function.Supplier;

public class WatersMelonBlock extends GourdBlock
{
	private final Supplier<StemBlock> stem;
	private final Supplier<AttachedStemBlock> attachedStem;

	public WatersMelonBlock(Supplier<StemBlock> stem, Supplier<AttachedStemBlock> attachedStem, Settings settings)
	{
		super(settings);
		this.stem = stem;
		this.attachedStem = attachedStem;
	}

	@Override
	public StemBlock getStem()
	{
		return stem.get();
	}

	@Override
	public AttachedStemBlock getAttachedStem()
	{
		return attachedStem.get();
	}
}
