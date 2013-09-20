package org.derbeukatt.underwatercraft.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityBoilerWall extends TileEntity implements IFluidHandler,
		ISidedInventory {

	TileEntityBoiler boiler;
	int boilerX;
	int boilerY;
	int boilerZ;

	public TileEntityBoilerWall() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		// TODO Auto-generated method stub
		return this.boiler.canDrain(from, fluid);
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack,
			final int j) {
		// TODO Auto-generated method stub
		return this.boiler.canExtractItem(i, itemstack, j);
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		// TODO Auto-generated method stub
		return this.boiler.canFill(from, fluid);
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack,
			final int j) {
		// TODO Auto-generated method stub
		return this.boiler.canInsertItem(i, itemstack, j);
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(final int i, final int count) {
		// TODO Auto-generated method stub
		return this.boiler.decrStackSize(i, count);
	}

	@Override
	public FluidStack drain(final ForgeDirection from,
			final FluidStack resource, final boolean doDrain) {
		// TODO Auto-generated method stub
		return this.boiler.drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain,
			final boolean doDrain) {
		// TODO Auto-generated method stub
		return this.boiler.drain(from, maxDrain, doDrain);
	}

	@Override
	public int fill(final ForgeDirection from, final FluidStack resource,
			final boolean doFill) {
		// TODO Auto-generated method stub
		return this.boiler.fill(from, resource, doFill);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int side) {
		// TODO Auto-generated method stub
		return this.boiler.getAccessibleSlotsFromSide(side);
	}

	public TileEntityBoiler getBoiler() {
		if (this.boiler == null) {
			this.boiler = (TileEntityBoiler) this.worldObj.getBlockTileEntity(
					this.boilerX, this.boilerY, this.boilerZ);
		}

		return this.boiler;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return this.boiler.getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return this.boiler.getInvName();
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return this.boiler.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		// TODO Auto-generated method stub
		return this.boiler.getStackInSlot(slot);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot) {
		// TODO Auto-generated method stub
		return this.boiler.getStackInSlotOnClosing(slot);
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		// TODO Auto-generated method stub
		return this.boiler.getTankInfo(from);
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return this.boiler.isInvNameLocalized();
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		// TODO Auto-generated method stub
		return this.boiler.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5,
				this.zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);

		this.boilerX = compound.getInteger("boilerX");
		this.boilerY = compound.getInteger("boilerY");
		this.boilerZ = compound.getInteger("boilerZ");
	}

	public void setBoiler(final TileEntityBoiler boiler) {
		this.boiler = boiler;
		this.boilerX = boiler.xCoord;
		this.boilerY = boiler.yCoord;
		this.boilerZ = boiler.zCoord;
	}

	@Override
	public void setInventorySlotContents(final int i, final ItemStack itemstack) {
		// TODO Auto-generated method stub
		this.boiler.setInventorySlotContents(i, itemstack);
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("boilerX", this.boilerX);
		compound.setInteger("boilerY", this.boilerY);
		compound.setInteger("boilerZ", this.boilerZ);
	}

}
