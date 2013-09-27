package org.derbeukatt.underwatercraft.client.gui.elements;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.derbeukatt.underwatercraft.client.gui.Gui;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.lwjgl.opengl.GL11;

public class GuiBottle extends GuiElement {

	public GuiBottle(final int x, final int y, final int w, final int h) {
		super(x, y, w, h);
	}

	@Override
	public void drawBackground(final Gui gui,
			final TextureManager renderEngine, final int srcX, final int srcY) {
		final TileEntity te = gui.getTileEntity();

		if ((te != null) && (te instanceof TileEntityMixer)) {
			final TileEntityMixer mixer = (TileEntityMixer) te;

			renderEngine.func_110577_a(Gui.BLOCK_TEXTURE);

			int i = 0;
			for (final int dmg : mixer.dyes.keySet()) {
				final ItemStack itemStack = mixer.dyes.get(dmg);

				if (itemStack != null) {
					net.minecraft.client.gui.Gui.drawRect(gui.getLeft()
							+ this.x + 5, (gui.getTop() + this.y + 48) - i,
							gui.getLeft() + 104, (gui.getTop() + this.y + 47)
									- i, 0xFF000000 | ItemDye.dyeColors[dmg]);
					i++;
				}
			}

			GL11.glColor4f(1F, 1F, 1F, 1F);

			if (mixer.hasBottleFluid) {
				gui.drawTexturedModelRectFromIcon(gui.getLeft() + this.x + 5,
						gui.getTop() + this.y + 48,
						Fluids.blubber.getStillIcon(), this.w - 10, this.h - 53);
			}

			renderEngine.func_110577_a(gui.texture);
			gui.drawTexturedModalRect(gui.getLeft() + this.x, gui.getTop()
					+ this.y, srcX, srcY, this.w, this.h);
		}
	}

	@Override
	public void drawForeground(final Gui gui, final FontRenderer fontRenderer,
			final int srcX, final int srcY) {
		final TileEntity te = gui.getTileEntity();

		if ((te != null) && (te instanceof TileEntityMixer)) {
			final TileEntityMixer mixer = (TileEntityMixer) te;
			if (this.mouseActionInElement(gui, srcX, srcY)) {
				final ArrayList<String> list = new ArrayList<String>();
				list.add("Missing colors:");
				String colors = "";
				for (int i = 0; i < ItemDye.dyeColors.length; i++) {
					if ((i % 3) == 0) {
						list.add(colors);
						colors = "";
					}
					if (!mixer.dyes.containsKey(i)) {
						colors += ItemDye.dyeColorNames[i];
						if (i < (ItemDye.dyeColors.length - 1)) {
							colors += ", ";
						} else {
							colors += ".";
						}
					}
				}

				list.add(colors);

				// gui.drawToolTips(list, srcX, srcY);
			}
		}
	}

	@Override
	public void drawScreen(final Gui gui, final int x, final int y) {
		// TODO Auto-generated method stub

	}

	@Override
	protected ToolTip getToolTip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void refreshGuiToolTip() {
		// TODO Auto-generated method stub

	}
}
