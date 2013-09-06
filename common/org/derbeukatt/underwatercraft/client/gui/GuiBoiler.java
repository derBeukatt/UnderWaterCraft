package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoiler extends GuiContainer {

	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.field_110575_b;

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			"underwatercraft", "textures/gui/boiler_gui.png");

	private final TileEntityBoiler boiler;

	public GuiBoiler(final InventoryPlayer inventory, final TileEntityBoiler te) {
		super(new ContainerBoiler(inventory, te));

		this.boiler = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	private void displayGauge(final int line, final int col, int squaled,
			final FluidStack liquid) {
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		final Fluid fluid = liquid.getFluid();
		if ((fluid != null) && (fluid.getIcon() != null)) {
			liquidIcon = fluid.getIcon();
		}
		this.mc.renderEngine.func_110577_a(BLOCK_TEXTURE);

		if (liquidIcon != null) {
			while (true) {
				int x;

				if (squaled > 16) {
					x = 16;
					squaled -= 16;
				} else {
					x = squaled;
					squaled = 0;
				}

				this.drawTexturedModelRectFromIcon(this.guiLeft + col,
						(this.guiTop + line + 58) - x - start, liquidIcon, 16,
						16 - (16 - x));
				start = start + 16;

				if ((x == 0) || (squaled == 0)) {
					break;
				}
			}
		}

		this.mc.renderEngine.func_110577_a(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft + col, this.guiTop + line, 176,
				30, 16, 58);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int x,
			final int y) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.func_110577_a(TEXTURE);

		/* Dray main gui */
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		/* Draw boiling animation */
		if (this.boiler.isBoiling()) {
			this.drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 60,
					176, 0, 14, 14);
		}

		/* Display water */
		this.displayGauge(19, 8, this.boiler.getScaledWaterAmount(58),
				this.boiler.getInputFluid());

		/* Display blubber */
		this.displayGauge(19, 116, this.boiler.getScaledBlubberAmount(58),
				this.boiler.getOutputFluid());

		final int scaledProgress = this.boiler.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 41, 176,
				14, scaledProgress + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int x, final int y) {
		this.fontRenderer.drawString("Blubber Boiler", 8, 6, 0x404040);
	}

}
