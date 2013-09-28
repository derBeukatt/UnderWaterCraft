package org.derbeukatt.underwatercraft.client.gui.util;

public enum GuiColor {
	BLACK(0), RED(4), GREEN(2), BROWN(11), BLUE(1), PURPLE(5), CYAN(3), LIGHTGRAY(
			7), GRAY(8), PINK(13), LIME(10), YELLOW(14), LIGHTBLUE(9), MAGENTA(
			12), ORANGE(6), WHITE(15);

	private int value;

	GuiColor(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
