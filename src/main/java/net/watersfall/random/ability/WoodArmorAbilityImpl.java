package net.watersfall.random.ability;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.watersfall.random.api.ability.WoodArmorAbility;

public class WoodArmorAbilityImpl implements WoodArmorAbility
{
	private ItemStack stack = Items.OAK_LOG.getDefaultStack();

	public WoodArmorAbilityImpl() {}

	public WoodArmorAbilityImpl(ItemStack stack)
	{
		this.stack = stack;
	}

	public WoodArmorAbilityImpl(NbtCompound tag, ItemStack stack)
	{
		this.fromNbt(tag, stack);
	}

	@Override
	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public ItemStack getStack()
	{
		return stack;
	}

	@Override
	public NbtCompound toNbt(NbtCompound tag, ItemStack stack)
	{
		Identifier id = Registry.ITEM.getId(this.stack.getItem());
		tag.putString("id", id.toString());
		tag.putByte("Count", (byte)1);
		return tag;
	}

	@Override
	public void fromNbt(NbtCompound tag, ItemStack stack)
	{
		this.stack = ItemStack.fromNbt(tag);
	}
}
