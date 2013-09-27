package org.derbeukatt.underwatercraft.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

import org.derbeukatt.underwatercraft.client.gui.Gui;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;

public abstract class GuiElement {

	protected final int h;
	protected final int w;
	protected final int x;
	protected final int y;

	protected GuiElement(final int x, final int y, final int w, final int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public abstract void drawBackground(final Gui gui,
			final TextureManager renderEngine, final int srcX, final int srcY);

	public abstract void drawForeground(final Gui gui,
			final FontRenderer fontRenderer, final int srcX, final int srcY);

	public abstract void drawScreen(final Gui gui, final int x, final int y);

	protected abstract ToolTip getToolTip();

	public boolean mouseActionInElement(final Gui gui, int mouseX, int mouseY) {
		mouseX -= gui.getLeft();
		mouseY -= gui.getTop();

		return (this.x <= mouseX) && (mouseX <= (this.x + this.w))
				&& (this.y <= mouseY) && (mouseY <= (this.y + this.h));
	}

	protected abstract void refreshGuiToolTip();

}
