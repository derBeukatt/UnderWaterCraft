package org.derbeukatt.underwatercraft.client.gui.tooltips;

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

	public int getSpacing() {
		return this.spacing;
	}

	public void setSpacing(final int spacing) {
		this.spacing = spacing;
	}
}