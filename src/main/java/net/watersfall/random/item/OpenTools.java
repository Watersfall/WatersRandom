package net.watersfall.random.item;

import net.minecraft.item.ToolMaterial;

public class OpenTools
{
	public static class PickaxeItem extends net.minecraft.item.PickaxeItem
	{
		public PickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings)
		{
			super(material, attackDamage, attackSpeed, settings);
		}
	}

	public static class AxeItem extends net.minecraft.item.AxeItem
	{
		public AxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings)
		{
			super(material, attackDamage, attackSpeed, settings);
		}
	}

	public static class HoeItem extends net.minecraft.item.HoeItem
	{
		public HoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings)
		{
			super(material, attackDamage, attackSpeed, settings);
		}
	}
}
