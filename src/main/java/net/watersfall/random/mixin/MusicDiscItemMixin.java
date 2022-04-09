package net.watersfall.random.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.MusicDiscItem;
import net.watersfall.random.registry.RandomBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MusicDiscItem.class)
public class MusicDiscItemMixin
{
	@Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
	private boolean watersrandom$musicDiscCheck(BlockState instance, Block block)
	{
		return !instance.isOf(Blocks.JUKEBOX) || !instance.isOf(RandomBlocks.ANIMATED_JUKEBOX);
	}
}
