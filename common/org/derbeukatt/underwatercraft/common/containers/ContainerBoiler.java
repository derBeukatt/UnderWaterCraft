package org.derbeukatt.underwatercraft.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

public class ContainerBoiler extends Container {

	public ContainerBoiler(final InventoryPlayer inventory,
			final TileEntityBoiler te) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(final EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

}
