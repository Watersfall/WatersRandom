package net.watersfall.random.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.watersfall.random.entity.RailgunBulletEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RailgunItem extends Item
{
	public static void registerAmmo()
	{
		RailgunAmmo.register(Items.GOLD_NUGGET, 6.0F, 0.75F);
		RailgunAmmo.register(Items.IRON_NUGGET, 4.0F, 0.5F, true);
	}

	public RailgunItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack stack = getNextHotbarStack(user, hand);
		if(RailgunAmmo.containsKey(stack.getItem()))
		{
			if(!world.isClient)
			{
				RailgunBulletEntity bullet = new RailgunBulletEntity(user, world);
				bullet.setVelocity(user.getRotationVector());
				bullet.refreshPositionAndAngles(user.getPos().x, user.getEyePos().y - (bullet.getHeight() / 2), user.getPos().z, user.getHeadYaw(), user.getPitch());
				bullet.setItem(stack);
				bullet.setAmmo(RailgunAmmo.get(stack.getItem()));
				world.spawnEntity(bullet);
				stack.decrement(1);
				user.getItemCooldownManager().set(this, 20);
			}
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}

	private ItemStack getNextHotbarStack(PlayerEntity user, Hand hand)
	{
		if(hand == Hand.OFF_HAND)
		{
			return ItemStack.EMPTY;
		}
		int index = user.getInventory().selectedSlot + 1;
		if(!PlayerInventory.isValidHotbarIndex(index))
		{
			index = 0;
		}
		return user.getInventory().getStack(index);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(new TranslatableText("item.watersrandom.railgun.current_ammo").formatted(Formatting.DARK_GRAY));
		if(MinecraftClient.getInstance().player != null)
		{
			boolean foundAmmo = false;
			PlayerInventory inventory = MinecraftClient.getInstance().player.getInventory();
			for(int i = 0; i < 9; i++)
			{
				if(inventory.getStack(i) == stack)
				{
					i++;
					if(i == 9)
					{
						i = 0;
					}
					ItemStack ammoStack = inventory.getStack(i);
					if(RailgunAmmo.containsKey(ammoStack.getItem()))
					{
						RailgunAmmo ammo = RailgunAmmo.get(ammoStack.getItem());
						tooltip.add(new LiteralText(" ").append(ammo.item.getName()).formatted(Formatting.DARK_GRAY));
						foundAmmo = true;
					}
					break;
				}
			}
			if(!foundAmmo)
			{
				tooltip.add(new TranslatableText("item.watersrandom.railgun.current_ammo.none").formatted(Formatting.DARK_GRAY));
			}
		}
		tooltip.add(new LiteralText(""));
		if(InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.GLFW_KEY_LEFT_SHIFT))
		{
			tooltip.add(new TranslatableText("item.watersrandom.railgun.valid_ammo").formatted(Formatting.DARK_GRAY));
			RailgunAmmo.map.values().forEach(value -> {
				tooltip.add(new LiteralText(" ").append(value.item.getName()).formatted(Formatting.DARK_GRAY));
			});
		}
		else
		{
			tooltip.add(new TranslatableText("item.watersrandom.railgun.valid_ammo.press_shift").formatted(Formatting.DARK_GRAY));
		}
	}

	public static record RailgunAmmo(Item item, float damage, float knockback, boolean horizontal)
	{
		private static Map<Item, RailgunAmmo> map = new HashMap<>();

		//For normal nuggets
		public static void register(Item item, float damage, float knockback)
		{
			register(item, damage, knockback, false);
		}

		//For disgusting abominations like iron nuggets
		public static void register(Item item, float damage, float knockback, boolean horizontal)
		{
			map.put(item, new RailgunAmmo(item, damage, knockback, horizontal));
		}

		public static boolean containsKey(Item item)
		{
			return map.containsKey(item);
		}

		public static RailgunAmmo get(Item item)
		{
			return map.get(item);
		}
	}
}