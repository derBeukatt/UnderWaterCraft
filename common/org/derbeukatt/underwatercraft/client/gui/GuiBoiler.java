package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.client.gui.elements.GuiElement;
import org.derbeukatt.underwatercraft.client.gui.elements.GuiGauge;
import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoiler extends Gui {

	private final GuiElement blubberGauge;

	private final TileEntityBoiler boiler;
	private final GuiElement waterGauge;

	public GuiBoiler(final InventoryPlayer inventory, final TileEntityBoiler te) {
		super(new ContainerBoiler(inventory, te), te);

		this.boiler = te;

		this.waterGauge = new GuiGauge(8, 19, 16, 58, te.getInputTank());
		this.blubberGauge = new GuiGauge(116, 19, 16, 58, te.getOutputTank());

		this.texture = new ResourceLocation("underwatercraft",
				"textures/gui/boiler_gui.png");

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int x,
			final int y) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.func_110577_a(this.texture);

		/* Dray main gui */
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		/* Draw boiling animation */
		if (this.boiler.isBoiling()) {
			final int heatupProgressScaled = this.boiler
					.getHeatupProgressScaled(14);
			this.drawTexturedModalRect(this.guiLeft + 53,
					(this.guiTop + 60 + 14) - heatupProgressScaled, 176,
					14 - heatupProgressScaled, 14, heatupProgressScaled);
		}

		this.blubberGauge.drawBackground(this, this.mc.renderEngine, 176, 30);
		this.waterGauge.drawBackground(this, this.mc.renderEngine, 176, 30);

		final int scaledProgress = this.boiler.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 41, 176,
				14, scaledProgress + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int x, final int y) {
		this.fontRenderer.drawString("Blubber Boiler", 8, 6, 0x404040);
		this.blubberGauge.drawForeground(this, this.fontRenderer, x, y);
		this.waterGauge.drawForeground(this, this.fontRenderer, x, y);
	}

	@Override
	public void drawScreen(final int x, final int y, final float f) {
		super.drawScreen(x, y, f);
		this.blubberGauge.drawScreen(this, x, y);
		this.waterGauge.drawScreen(this, x, y);
	}

}
