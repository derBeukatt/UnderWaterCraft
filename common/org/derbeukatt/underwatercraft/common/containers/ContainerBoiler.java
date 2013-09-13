package org.derbeukatt.underwatercraft.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import org.derbeukatt.underwatercraft.client.gui.SlotFluidContainer;
import org.derbeukatt.underwatercraft.client.gui.SlotFluidOutput;
import org.derbeukatt.underwatercraft.client.gui.SlotRawFish;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBoiler extends Container {

	private final TileEntityBoiler boiler;
	private int lastCookTime;
	private int lastHeatUpTime;

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
		this.addSlotToContainer(new SlotFluidContainer(this.boiler, 1, 137, 19));
		this.addSlotToContainer(new SlotFluidOutput(this.boiler, 2, 137, 61));
	}

	@Override
	public void addCraftingToCrafters(final ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.boiler.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.boiler.heatUpTime);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer entityplayer) {
		return this.boiler.isUseableByPlayer(entityplayer);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			final ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.boiler.cookTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.boiler.cookTime);
			}

			if (this.lastHeatUpTime != this.boiler.heatUpTime) {
				icrafting
						.sendProgressBarUpdate(this, 1, this.boiler.heatUpTime);
			}
		}

		this.lastCookTime = this.boiler.cookTime;
		this.lastHeatUpTime = this.boiler.heatUpTime;
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
			} else if (stack.itemID == Item.fishRaw.itemID) {
				if (!this.mergeItemStack(stack, 36, 37, false)) {
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

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(final int id, final int value) {
		if (id == 0) {
			this.boiler.cookTime = value;
		} else if (id == 1) {
			this.boiler.heatUpTime = value;
		}
	}
}
