package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoiler extends GuiContainer {

	public GuiBoiler(final InventoryPlayer inventory, final TileEntityBoiler te) {
		super(new ContainerBoiler(inventory, te));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int i,
			final int j) {
		// TODO Auto-generated method stub

	}

}
