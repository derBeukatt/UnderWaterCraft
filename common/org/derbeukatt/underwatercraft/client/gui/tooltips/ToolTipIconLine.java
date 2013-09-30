package org.derbeukatt.underwatercraft.client.gui.tooltips;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Icon;

import org.derbeukatt.underwatercraft.client.gui.Gui;

public class ToolTipIconLine extends ToolTipLine {

	public ArrayList<Icon> iconList;

	public ToolTipIconLine() {
		this.iconList = new ArrayList<Icon>();
	}

	public void addIcon(final Icon icon) {
		this.iconList.add(icon);
	}

	@Override
	public void draw(final Gui gui, final int x, final int y) {
		int i = 0;
		gui.getRenderEngine().func_110577_a(Gui.ITEM_TEXTURE);
		for (final Icon icon : this.iconList) {
			gui.drawTexturedModelRectFromIcon(x + (24 * i), y, icon, 16, 16);
			i++;
		}

		gui.getRenderEngine().func_110577_a(gui.texture);
	}

	@Override
	public int getHeight() {
		return 16;
	}

	@Override
	public int getHeightMultiplier() {
		return 16;
	}

	@Override
	public int getLength(final FontRenderer fontRenderer) {
		return this.iconList.size() * 24;
	}

}
