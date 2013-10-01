package org.derbeukatt.underwatercraft.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.client.gui.Gui;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;
import org.derbeukatt.underwatercraft.common.fluids.CustomFluidTank;
import org.lwjgl.opengl.GL11;

public class GuiGauge extends GuiElement {

	private final CustomFluidTank tank;

	public GuiGauge(final Gui gui, final int x, final int y, final int w,
			final int h, final CustomFluidTank tank) {
		super(gui, x, y, w, h);
		this.tank = tank;
	}

	private void displayGauge(final TextureManager renderEngine,
			final int line, final int col, int squaled,
			final FluidStack liquid, final int srcX, final int srcY) {
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		final Fluid fluid = liquid.getFluid();
		if ((fluid != null) && (fluid.getIcon() != null)) {
			liquidIcon = fluid.getIcon();
		}
		renderEngine.func_110577_a(Gui.BLOCK_TEXTURE);

		if (liquidIcon != null) {
			while (true) {
				int x;

				if (squaled > this.w) {
					x = this.w;
					squaled -= this.w;
				} else {
					x = squaled;
					squaled = 0;
				}

				this.gui.drawTexturedModelRectFromIcon(
						this.gui.getLeft() + col,
						(this.gui.getTop() + line + this.h) - x - start,
						liquidIcon, this.w, this.w - (this.w - x));
				start = start + this.w;

				if ((x == 0) || (squaled == 0)) {
					break;
				}
			}
		}

		renderEngine.func_110577_a(this.gui.texture);
		this.gui.drawTexturedModalRect(this.gui.getLeft() + col,
				this.gui.getTop() + line, srcX, srcY, this.w, this.h);
	}

	@Override
	public void drawBackground(final TextureManager renderEngine,
			final int srcX, final int srcY) {
		this.displayGauge(renderEngine, this.y, this.x,
				this.tank.getScaledFluidAmount(58), this.tank.getFluid(), srcX,
				srcY);
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
		// TODO Auto-generated method stub
		return this.tank.getToolTip();
	}

	@Override
	protected void refreshGuiToolTip() {
		this.tank.refreshTooltip();
	}

}
