package org.derbeukatt.underwatercraft.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityMixer extends TileEntity implements IFluidHandler,
		ISidedInventory {

	private static final int MAX_CAPACITY = 16 * FluidContainerRegistry.BUCKET_VOLUME;

	private final ItemStack[] items;

	public int renderHeight;
	private final FluidTank waterTank;

	public TileEntityMixer() {
		this.items = new ItemStack[2];
		this.waterTank = new FluidTank(FluidRegistry.WATER, 0, MAX_CAPACITY);
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		return true;
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack,
			final int j) {
		return i == 1;
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		return true;
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
	public FluidStack drain(final ForgeDirection from,
			final FluidStack resource, final boolean doDrain) {

		final FluidStack amount = this.waterTank
				.drain(resource.amount, doDrain);
		this.renderHeight = this.waterTank.getFluidAmount();
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

		return amount;
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain,
			final boolean doDrain) {
		final FluidStack amount = this.waterTank.drain(maxDrain, doDrain);
		this.renderHeight = this.waterTank.getFluidAmount();
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

		return amount;
	}

	@Override
	public int fill(final ForgeDirection from, final FluidStack resource,
			final boolean doFill) {

		final int amount = this.waterTank.fill(resource, doFill);
		this.renderHeight = this.waterTank.getFluidAmount();
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

		return amount;
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
		tag.setInteger("renderHeight", this.renderHeight);
		return new Packet132TileEntityData(this.xCoord, this.yCoord,
				this.zCoord, 1, tag);
	}

	public FluidStack getInputFluid() {
		return this.waterTank.getFluid();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "InventoryMixer";
	}

	public int getScaledWaterAmount(final int i) {
		return this.renderHeight != 0 ? (int) (((float) this.renderHeight / (float) (MAX_CAPACITY)) * i)
				: 0;
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
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		final FluidTankInfo[] info = new FluidTankInfo[2];

		info[0] = this.waterTank.getInfo();

		return info;
	}

	public FluidTank getWaterTank() {
		return this.waterTank;
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

		this.renderHeight = tag.getInteger("renderHeight");

		this.waterTank.setFluid(new FluidStack(FluidRegistry.WATER,
				this.renderHeight));
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

		final int idWater = compound.getInteger("itemIDWater");
		final int amountWater = compound.getInteger("amountWater");

		this.waterTank.setFluid(new FluidStack(idWater, amountWater));

		this.renderHeight = compound.getShort("renderHeight");
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

		final FluidStack liquid = this.waterTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDWater", liquid.fluidID);
			compound.setInteger("amountWater", liquid.amount);
		}

		compound.setShort("renderHeight", (short) this.renderHeight);
	}
}
