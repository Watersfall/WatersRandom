package net.watersfall.random.item.material;

import net.minecraft.client.sound.Sound;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;

import java.util.function.Supplier;

public class RandomArmorMaterials implements ArmorMaterial
{
	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

	public static final ArmorMaterial GILDED = new RandomArmorMaterials(
			16,
			new int[]{2, 5, 6, 2},
			23,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON,
			() -> Ingredient.ofItems(Items.IRON_INGOT),
			"gilded",
			0,
			0
	);
	public static final ArmorMaterial WOOD = new RandomArmorMaterials(
			7,
			new int[]{1,1,1,1},
			10,
			SoundEvents.BLOCK_LADDER_STEP,
			() -> Ingredient.fromTag(ItemTags.LOGS),
			"wood",
			0,
			0
	);

	private final int durability;
	private final int[] protection;
	private final int enchantability;
	private final SoundEvent sound;
	private final Supplier<Ingredient> repairIngredient;
	private final String name;
	private final float toughness;
	private final float knockbackResistance;

	public RandomArmorMaterials(int durability, int[] protection, int enchantability, SoundEvent sound, Supplier<Ingredient> repairIngredient, String name, float toughness, float knockbackResistance)
	{
		this.durability = durability;
		this.protection = protection;
		this.enchantability = enchantability;
		this.sound = sound;
		this.repairIngredient = repairIngredient;
		this.name = name;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDurability(EquipmentSlot slot)
	{
		return BASE_DURABILITY[slot.getEntitySlotId()] * durability;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot)
	{
		return protection[slot.getEntitySlotId()];
	}

	@Override
	public int getEnchantability()
	{
		return enchantability;
	}

	@Override
	public SoundEvent getEquipSound()
	{
		return sound;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return repairIngredient.get();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public float getToughness()
	{
		return toughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return knockbackResistance;
	}
}
