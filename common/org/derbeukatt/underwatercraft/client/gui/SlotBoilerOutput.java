package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBoilerOutput extends Slot {

	public SlotBoilerOutput(final IInventory inventory, final int id,
			final int x, final int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(final ItemStack itemstack) {
		return false;
	}

}
