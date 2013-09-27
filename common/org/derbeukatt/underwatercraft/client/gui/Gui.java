package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTipLine;

public class Gui extends GuiContainer {

	public static final ResourceLocation BLOCK_TEXTURE = TextureMap.field_110575_b;
	public ResourceLocation texture;
	private final TileEntity tileEntity;

	public Gui(final Container container, final TileEntity te) {
		super(container);
		this.tileEntity = te;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int i,
			final int j) {
		// TODO Auto-generated method stub

	}

	public void drawToolTips(final ToolTip toolTips, final int mouseX,
			final int mouseY) {
		if (toolTips.size() > 0) {
			final int left = this.guiLeft;
			final int top = this.guiTop;
			int lenght = 0;
			int x;
			int y;

			for (final ToolTipLine tip : toolTips) {
				y = this.fontRenderer.getStringWidth(tip.text);

				if (y > lenght) {
					lenght = y;
				}
			}

			x = (mouseX - left) + 12;
			y = mouseY - top - 12;
			int var14 = 8;

			if (toolTips.size() > 1) {
				var14 += 2 + ((toolTips.size() - 1) * 10);
			}

			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			final int var15 = -267386864;
			this.drawGradientRect(x - 3, y - 4, x + lenght + 3, y - 3, var15,
					var15);
			this.drawGradientRect(x - 3, y + var14 + 3, x + lenght + 3, y
					+ var14 + 4, var15, var15);
			this.drawGradientRect(x - 3, y - 3, x + lenght + 3, y + var14 + 3,
					var15, var15);
			this.drawGradientRect(x - 4, y - 3, x - 3, y + var14 + 3, var15,
					var15);
			this.drawGradientRect(x + lenght + 3, y - 3, x + lenght + 4, y
					+ var14 + 3, var15, var15);
			final int var16 = 1347420415;
			final int var17 = ((var16 & 16711422) >> 1) | (var16 & -16777216);
			this.drawGradientRect(x - 3, (y - 3) + 1, (x - 3) + 1,
					(y + var14 + 3) - 1, var16, var17);
			this.drawGradientRect(x + lenght + 2, (y - 3) + 1, x + lenght + 3,
					(y + var14 + 3) - 1, var16, var17);
			this.drawGradientRect(x - 3, y - 3, x + lenght + 3, (y - 3) + 1,
					var16, var16);
			this.drawGradientRect(x - 3, y + var14 + 2, x + lenght + 3, y
					+ var14 + 3, var17, var17);

			for (final ToolTipLine tip : toolTips) {
				String line = tip.text;

				if (tip.color == -1) {
					line = "\u00a77" + line;
				} else {
					line = "\u00a7" + Integer.toHexString(tip.color) + line;
				}

				this.fontRenderer.drawStringWithShadow(line, x, y, -1);

				y += 10 + tip.getSpacing();
			}

			this.zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;
		}
	}

	public int getLeft() {
		return this.guiLeft;
	}

	public TileEntity getTileEntity() {
		return this.tileEntity;
	}

	public int getTop() {
		return this.guiTop;
	}

}
