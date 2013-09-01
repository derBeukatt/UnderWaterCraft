package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.derbeukatt.underwatercraft.common.items.Items;

import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {
	@Override
	public void onCrafting(final EntityPlayer player, final ItemStack item,
			final IInventory craftMatrix) {
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			if (craftMatrix.getStackInSlot(i) != null) {
				final ItemStack j = craftMatrix.getStackInSlot(i);
				if ((j.getItem() != null)
						&& (j.getItem() == Items.itemWhetstone)) {
					final ItemStack k = new ItemStack(Items.itemWhetstone, 2,
							(j.getItemDamage() + 1));
					if (k.getItemDamage() < k.getMaxDamage()) {
						craftMatrix.setInventorySlotContents(i, k);
					} else {
						// ItemStack l = new ItemStack(Block.blockClay, 0);
						craftMatrix.setInventorySlotContents(i, j);
					}
				}

				else if ((j.getItem() != null)
						&& (j.getItem() == Items.itemScalingKnife)) {
					final ItemStack k = new ItemStack(Items.itemScalingKnife,
							2, (j.getItemDamage() + 1));
					if (k.getItemDamage() < k.getMaxDamage()) {
						craftMatrix.setInventorySlotContents(i, k);
					} else {
						// ItemStack l = new ItemStack(Block.blockClay, 0);
						craftMatrix.setInventorySlotContents(i, j);
					}
				}
			}
		}
	}

	@Override
	public void onSmelting(final EntityPlayer player, final ItemStack item) {

	}
}
