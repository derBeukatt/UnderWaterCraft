package org.derbeukatt.underwatercraft.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

public class ContainerBoiler extends Container {

	private final TileEntityBoiler boiler;

	public ContainerBoiler(final InventoryPlayer inventory,
			final TileEntityBoiler te) {
		this.boiler = te;
	}

	@Override
	public boolean canInteractWith(final EntityPlayer entityplayer) {
		return this.boiler.isUseableByPlayer(entityplayer);
	}

}
