package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.lwjgl.opengl.GL11;

public class GuiBottle {

	private final int h;
	private final int w;
	private final int x;
	private final int y;

	public GuiBottle(final int x, final int y, final int w, final int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void draw(final GuiMixer gui, final TextureManager renderEngine,
			final int srcX, final int srcY) {
		renderEngine.func_110577_a(GuiMixer.BLOCK_TEXTURE);

		for (int i = 0; i < gui.getMixer().dyes.size(); i++) {
			final ItemStack itemStack = gui.getMixer().dyes.get(i);

			if (itemStack != null) {
				Gui.drawRect(gui.getLeft() + this.x + 5,
						(gui.getTop() + this.y + 48) - i, gui.getLeft() + 104,
						(gui.getTop() + this.y + 47) - i,
						0xFF000000 | ItemDye.dyeColors[itemStack
								.getItemDamage()]);
			}
		}

		GL11.glColor4f(1F, 1F, 1F, 1F);

		gui.drawTexturedModelRectFromIcon(gui.getLeft() + this.x + 5,
				gui.getTop() + this.y + 48, Fluids.blubber.getStillIcon(),
				this.w - 10, this.h - 53);

		renderEngine.func_110577_a(GuiMixer.TEXTURE);
		gui.drawTexturedModalRect(gui.getLeft() + this.x,
				gui.getTop() + this.y, srcX, srcY, this.w, this.h);

	}

	public boolean mouseClicked(final GuiMixer gui, int mouseX, int mouseY) {
		mouseX -= gui.getLeft();
		mouseY -= gui.getTop();

		return (this.x <= mouseX) && (mouseX <= (this.x + this.w))
				&& (this.y <= mouseY) && (mouseY <= (this.y + this.h));
	}

}
