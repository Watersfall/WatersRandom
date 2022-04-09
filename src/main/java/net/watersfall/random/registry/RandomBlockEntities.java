package net.watersfall.random.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.block.entity.AnimatedJukeboxBlockEntity;
import net.watersfall.random.block.entity.DrawbridgeBlockEntity;
import net.watersfall.random.block.entity.ProjectTableBlockEntity;
import net.watersfall.random.block.entity.TinyChestBlockEntity;

public class RandomBlockEntities
{
	public static BlockEntityType<DrawbridgeBlockEntity> DRAWBRIDGE;
	public static BlockEntityType<TinyChestBlockEntity> TINY_CHEST;
	public static BlockEntityType<ProjectTableBlockEntity> PROJECT_TABLE;
	public static BlockEntityType<AnimatedJukeboxBlockEntity> ANIMATED_JUKEBOX;

	public static void register()
	{
		DRAWBRIDGE = register("drawbridge", DrawbridgeBlockEntity::new, RandomBlocks.DRAWBRIDGE);
		TINY_CHEST = register("tiny_chest", TinyChestBlockEntity::new, RandomBlocks.TINY_CHEST);
		PROJECT_TABLE = register("project_table", ProjectTableBlockEntity::new, RandomBlocks.PROJECT_TABLE);
		ANIMATED_JUKEBOX = register("animated_jukebox", AnimatedJukeboxBlockEntity::new, RandomBlocks.ANIMATED_JUKEBOX);
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks)
	{
		BlockEntityType<T> type = FabricBlockEntityTypeBuilder.create(factory, blocks).build();
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, WatersRandom.getId(id), type);
	}

	public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker)
	{
		return expectedType == givenType ? (BlockEntityTicker<A>)ticker : null;
	}
}
