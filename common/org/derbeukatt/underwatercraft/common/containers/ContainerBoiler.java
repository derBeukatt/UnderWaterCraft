package org.derbeukatt.underwatercraft.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import org.derbeukatt.underwatercraft.client.gui.SlotRawFish;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

public class ContainerBoiler extends Container {

	private final TileEntityBoiler boiler;

	public ContainerBoiler(final InventoryPlayer invPlayer,
			final TileEntityBoiler te) {
		this.boiler = te;

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + (18 * x), 142));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, x + (y * 9) + 9,
						8 + (18 * x), 84 + (y * 18)));
			}
		}

		this.addSlotToContainer(new SlotRawFish(this.boiler, 0, 52, 41));
		this.addSlotToContainer(new Slot(this.boiler, 1, 137, 19));
		this.addSlotToContainer(new Slot(this.boiler, 2, 137, 61));
	}

	@Override
	public boolean canInteractWith(final EntityPlayer entityplayer) {
		return this.boiler.isUseableByPlayer(entityplayer);
	}

}
