package net.watersfall.random.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.watersfall.random.WatersRandom;
import net.watersfall.random.gui.TinyChestScreenHandler;

public class TinyChestScreen extends HandledScreen<TinyChestScreenHandler>
{
	public static final Identifier TINY_BACKGROUND = WatersRandom.getId("textures/gui/tiny_chest_tiny.png");
	public static final Identifier LARGE_BACKGROUND = WatersRandom.getId("textures/gui/tiny_chest_large.png");

	public TinyChestScreen(TinyChestScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void init()
	{
		if(handler.isTiny())
		{
			this.backgroundHeight = 132;
		}
		else
		{
			this.backgroundHeight = 272;
			this.backgroundWidth = 230;
			this.playerInventoryTitleX += 27;
		}
		super.init();
		this.playerInventoryTitleY = backgroundHeight - 92;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY)
	{
		this.renderBackground(matrices);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		if(handler.isTiny())
		{
			RenderSystem.setShaderTexture(0, TINY_BACKGROUND);
			drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
		}
		else
		{
			RenderSystem.setShaderTexture(0, LARGE_BACKGROUND);
			drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 512);
		}
	}
}
