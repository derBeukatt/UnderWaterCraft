package org.derbeukatt.underwatercraft.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import org.derbeukatt.underwatercraft.client.gui.SlotFluidContainer;
import org.derbeukatt.underwatercraft.client.gui.SlotFluidOutput;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;

public class ContainerMixer extends Container {

	private final TileEntityMixer mixer;

	public ContainerMixer(final InventoryPlayer invPlayer,
			final TileEntityMixer te) {
		this.mixer = te;

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + (18 * x), 142));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, x + (y * 9) + 9,
						8 + (18 * x), 84 + (y * 18)));
			}
		}

		this.addSlotToContainer(new SlotFluidContainer(this.mixer, 0, 152, 19));
		this.addSlotToContainer(new SlotFluidOutput(this.mixer, 1, 152, 61));
	}

	@Override
	public boolean canInteractWith(final EntityPlayer entityplayer) {
		return this.mixer.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int i) {
		final Slot slot = this.getSlot(i);

		if ((slot != null) && slot.getHasStack()) {
			final ItemStack stack = slot.getStack();
			final ItemStack result = stack.copy();

			if (i >= 36) {
				if (!this.mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			} else if (FluidContainerRegistry.isEmptyContainer(stack)) {
				if (!this.mergeItemStack(stack, 37, 38, false)) {
					return null;
				}
			} else {
				return null;
			}

			if (stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			slot.onPickupFromSlot(player, stack);

			return result;
		}

		return null;
	}
}
