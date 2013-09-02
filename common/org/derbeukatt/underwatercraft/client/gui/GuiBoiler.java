package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoiler extends GuiContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			"underwatercraft", "textures/gui/boiler_gui.png");

	private final TileEntityBoiler boiler;

	public GuiBoiler(final InventoryPlayer inventory, final TileEntityBoiler te) {
		super(new ContainerBoiler(inventory, te));

		this.boiler = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int x,
			final int y) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.renderEngine.func_110577_a(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		if (this.boiler.worldObj.getBlockMetadata(this.boiler.xCoord,
				this.boiler.yCoord, this.boiler.zCoord) > 1) {
			this.drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 60,
					176, 0, 14, 14);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int x, final int y) {
		this.fontRenderer.drawString("Blubber Boiler", 8, 6, 0x404040);
	}

}
