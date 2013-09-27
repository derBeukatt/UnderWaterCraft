package org.derbeukatt.underwatercraft.common.tileentity;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.common.fluids.CustomFluidTank;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;

public class TileEntityMixer extends TileEntityWithTanks implements
		ISidedInventory {

	public HashMap<Integer, ItemStack> dyes;
	public boolean hasBottleFluid;

	private final ItemStack[] items;

	public TileEntityMixer() {
		this.items = new ItemStack[2];
		this.dyes = new HashMap<Integer, ItemStack>();
		this.maxCapacity = 16 * FluidContainerRegistry.BUCKET_VOLUME;
		this.inputTank = new CustomFluidTank(Fluids.blubber, 0,
				this.maxCapacity);
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack,
			final int j) {
		return i == 1;
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack,
			final int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(final int i, final int count) {
		ItemStack itemStack = this.getStackInSlot(i);

		if (itemStack != null) {
			if (itemStack.stackSize <= count) {
				this.setInventorySlotContents(i, null);
			} else {
				itemStack = itemStack.splitStack(count);
				this.onInventoryChanged();
			}
		}

		return itemStack;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		final int[] slots = new int[2];
		slots[0] = 0;
		slots[1] = 1;

		return slots;
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("inputHeight", this.inputHeight);
		final NBTTagList dyes = new NBTTagList();

		for (final int dmg : this.dyes.keySet()) {
			final ItemStack stack = this.dyes.get(dmg);

			if (stack != null) {
				final NBTTagCompound dye = new NBTTagCompound();
				dye.setByte("Dye", (byte) dmg);
				stack.writeToNBT(dye);
				dyes.appendTag(dye);
			}
		}

		tag.setTag("Dyes", dyes);

		tag.setBoolean("hasBottleFluid", this.hasBottleFluid);
		return new Packet132TileEntityData(this.xCoord, this.yCoord,
				this.zCoord, 1, tag);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "InventoryMixer";
	}

	@Override
	public int getSizeInventory() {
		return this.items.length;
	}

	@Override
	public ItemStack getStackInSlot(final int i) {
		return this.items[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int i) {
		ItemStack item = null;

		item = this.getStackInSlot(i);
		this.setInventorySlotContents(i, null);

		return item;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		if (i == 0) {
			return FluidContainerRegistry.isEmptyContainer(itemstack);
		} else {
			return false;
		}
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5,
				this.zCoord + 0.5) <= 64;
	}

	@Override
	public void onDataPacket(final INetworkManager net,
			final Packet132TileEntityData packet) {
		final NBTTagCompound tag = packet.customParam1;

		this.inputHeight = tag.getInteger("inputHeight");

		this.inputTank
				.setFluid(new FluidStack(Fluids.blubber, this.inputHeight));

		final NBTTagList dyes = tag.getTagList("Dyes");

		if (dyes.tagCount() == 0) {
			this.dyes.clear();
		}

		for (int i = 0; i < dyes.tagCount(); i++) {
			final NBTTagCompound dye = (NBTTagCompound) dyes.tagAt(i);
			final int dmg = dye.getByte("Dye");

			if (!this.dyes.containsKey(dmg)) {
				this.dyes.put(dmg, ItemStack.loadItemStackFromNBT(dye));
			}
		}

		this.hasBottleFluid = tag.getBoolean("hasBottleFluid");

		this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord,
				this.zCoord);
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);

		final NBTTagList items = compound.getTagList("Items");

		for (int i = 0; i < items.tagCount(); i++) {
			final NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
			final int slot = item.getByte("Slot");

			if ((slot >= 0) && (slot < this.getSizeInventory())) {
				this.setInventorySlotContents(slot,
						ItemStack.loadItemStackFromNBT(item));
			}
		}

		final NBTTagList dyes = compound.getTagList("Dyes");

		for (int i = 0; i < dyes.tagCount(); i++) {
			final NBTTagCompound dye = (NBTTagCompound) dyes.tagAt(i);
			final int dmg = dye.getByte("Dye");

			this.dyes.put(dmg, ItemStack.loadItemStackFromNBT(dye));
		}

		final int idBlubber = compound.getInteger("itemIDBlubber");
		final int amountBlubber = compound.getInteger("amountBlubber");

		this.inputTank.setFluid(new FluidStack(idBlubber, amountBlubber));

		this.inputHeight = compound.getInteger("inputHeight");
		this.hasBottleFluid = compound.getBoolean("hasBottleFluid");
	}

	@Override
	public void setInventorySlotContents(final int i, final ItemStack itemstack) {
		this.items[i] = itemstack;

		if ((itemstack != null)
				&& (itemstack.stackSize > this.getInventoryStackLimit())) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {

			final ItemStack destStack = this.getStackInSlot(1);
			if (destStack == null) {
				final ItemStack stackInSlot = this.getStackInSlot(0);
				if ((stackInSlot != null) && this.hasBottleFluid
						&& (this.dyes.size() == 16)) {
					final Item item = stackInSlot.getItem();
					if (item != null) {
						this.decrStackSize(0, 1);

						final ItemStack filledContainer = FluidContainerRegistry
								.fillFluidContainer(new FluidStack(
										Fluids.rainbowBlubber,
										FluidContainerRegistry.BUCKET_VOLUME),
										new ItemStack(item));

						this.dyes.clear();
						this.hasBottleFluid = false;
						this.setInventorySlotContents(1, filledContainer);

						this.worldObj.markBlockForUpdate(this.xCoord,
								this.yCoord, this.zCoord);
					}
				}
			}

		}
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		final NBTTagList items = new NBTTagList();

		for (int i = 0; i < this.getSizeInventory(); i++) {
			final ItemStack stack = this.getStackInSlot(i);

			if (stack != null) {
				final NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}

		compound.setTag("Items", items);

		final NBTTagList dyes = new NBTTagList();

		for (final int dmg : this.dyes.keySet()) {
			final ItemStack stack = this.dyes.get(dmg);

			if (stack != null) {
				final NBTTagCompound dye = new NBTTagCompound();
				dye.setByte("Dye", (byte) dmg);
				stack.writeToNBT(dye);
				dyes.appendTag(dye);
			}
		}

		compound.setTag("Dyes", dyes);

		final FluidStack liquid = this.inputTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDBlubber", liquid.fluidID);
			compound.setInteger("amountBlubber", liquid.amount);
		}

		compound.setInteger("inputHeight", this.inputHeight);
		compound.setBoolean("hasBottleFluid", this.hasBottleFluid);
	}
}
