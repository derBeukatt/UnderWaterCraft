package org.derbeukatt.underwatercraft.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.derbeukatt.underwatercraft.client.gui.Gui;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTipLine;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.lwjgl.opengl.GL11;

public class GuiBottle extends GuiElement {

	protected final ToolTip toolTip = new ToolTip() {
		@Override
		public void refresh() {
			GuiBottle.this.refreshGuiToolTip();
		}
	};

	public GuiBottle(final Gui gui, final int x, final int y, final int w,
			final int h) {
		super(gui, x, y, w, h);
	}

	@Override
	public void drawBackground(final TextureManager renderEngine,
			final int srcX, final int srcY) {
		final TileEntity te = this.gui.getTileEntity();

		if ((te != null) && (te instanceof TileEntityMixer)) {
			final TileEntityMixer mixer = (TileEntityMixer) te;

			renderEngine.func_110577_a(Gui.BLOCK_TEXTURE);

			int i = 0;
			for (final int dmg : mixer.dyes.keySet()) {
				final ItemStack itemStack = mixer.dyes.get(dmg);

				if (itemStack != null) {
					net.minecraft.client.gui.Gui.drawRect(this.gui.getLeft()
							+ this.x + 5,
							(this.gui.getTop() + this.y + 48) - i,
							this.gui.getLeft() + 104, (this.gui.getTop()
									+ this.y + 47)
									- i, 0xFF000000 | ItemDye.dyeColors[dmg]);
					i++;
				}
			}

			GL11.glColor4f(1F, 1F, 1F, 1F);

			if (mixer.hasBottleFluid) {
				this.gui.drawTexturedModelRectFromIcon(this.gui.getLeft()
						+ this.x + 5, this.gui.getTop() + this.y + 48,
						Fluids.blubber.getStillIcon(), this.w - 10, this.h - 53);
			}

			renderEngine.func_110577_a(this.gui.texture);
			this.gui.drawTexturedModalRect(this.gui.getLeft() + this.x,
					this.gui.getTop() + this.y, srcX, srcY, this.w, this.h);
		}
	}

	@Override
	public void drawForeground(final FontRenderer fontRenderer, final int srcX,
			final int srcY) {

	}

	@Override
	public void drawScreen(final int x, final int y) {
		final int left = this.gui.getLeft();
		final int top = this.gui.getTop();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(left, top, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();

		final ToolTip tips = this.getToolTip();
		if (tips == null) {
			return;
		}
		final boolean mouseOver = this.mouseActionInElement(x, y);
		tips.onTick(mouseOver);
		if (mouseOver && tips.isReady()) {
			tips.refresh();
			this.gui.drawToolTips(tips, x, y);
		}

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	protected ToolTip getToolTip() {
		return this.toolTip;
	}

	@Override
	protected void refreshGuiToolTip() {
		this.toolTip.clear();
		final TileEntity te = this.gui.getTileEntity();

		if ((te != null) && (te instanceof TileEntityMixer)) {
			final TileEntityMixer mixer = (TileEntityMixer) te;
			final ToolTipLine header = new ToolTipLine("Missing colors: ");
			header.setSpacing(5);
			this.toolTip.add(header);
			for (int i = 0; i < (ItemDye.dyeColors.length - 3); i += 4) {
				if (!mixer.dyes.containsKey(i)) {
					final ToolTipLine colorLine = new ToolTipLine(
							ItemDye.dyeColorNames[i] + ", "
									+ ItemDye.dyeColorNames[i + 1] + ", "
									+ ItemDye.dyeColorNames[i + 2] + ", "
									+ ItemDye.dyeColorNames[i + 3]);
					colorLine.setSpacing(1);
					this.toolTip.add(colorLine);
				}
			}

			this.toolTip.add(new ToolTipLine());
		}

	}
}
