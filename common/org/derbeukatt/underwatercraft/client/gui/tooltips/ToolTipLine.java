package org.derbeukatt.underwatercraft.client.gui.tooltips;

import net.minecraft.client.gui.FontRenderer;

import org.derbeukatt.underwatercraft.client.gui.Gui;

/**
 * 
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ToolTipLine {

	public final int color;
	public int spacing;
	public String text;

	public ToolTipLine() {
		this("", -1);
	}

	public ToolTipLine(final String text) {
		this(text, -1);
	}

	public ToolTipLine(final String text, final int color) {
		this.text = text;
		this.color = color;
	}

	public void draw(final Gui gui, final int x, final int y) {
		String line = this.text;

		if (this.color == -1) {
			line = "\u00a77" + line;
		} else {
			line = "\u00a7" + Integer.toHexString(this.color) + line;
		}

		gui.getFontRenderer().drawStringWithShadow(line, x, y, -1);
	}

	public int getHeight() {
		return 8;
	}

	public int getHeightMultiplier() {
		return 10;
	}

	public int getLength(final FontRenderer fontRenderer) {
		return fontRenderer.getStringWidth(this.text);
	}

	public int getSpacing() {
		return this.spacing;
	}

	public void setSpacing(final int spacing) {
		this.spacing = spacing;
	}
}