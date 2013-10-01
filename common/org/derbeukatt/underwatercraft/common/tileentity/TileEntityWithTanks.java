package org.derbeukatt.underwatercraft.common.tileentity;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.derbeukatt.underwatercraft.common.fluids.CustomFluidTank;

public class TileEntityWithTanks extends TileEntity implements IFluidHandler {

	protected int inputHeight;
	protected CustomFluidTank inputTank;
	protected int maxCapacity;
	protected int outputHeight;
	protected CustomFluidTank outputTank;

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		return true;
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		return true;
	}

	@Override
	public FluidStack drain(final ForgeDirection from,
			final FluidStack resource, final boolean doDrain) {
		FluidTank tankToDrain = null;
		FluidStack amount = null;

		if (this.inputTank != null) {
			if (resource.getFluid().getID() == this.inputTank.getFluid().fluidID) {
				tankToDrain = this.inputTank;
			}
		}
		if (this.outputTank != null) {
			if (resource.getFluid().getID() == this.outputTank.getFluid().fluidID) {
				tankToDrain = this.outputTank;
			}
		}

		if (tankToDrain != null) {
			amount = tankToDrain.drain(resource.amount, doDrain);

			if (this.inputTank != null) {
				this.inputHeight = this.inputTank.getFluidAmount();
			}
			if (this.outputTank != null) {
				this.outputHeight = this.outputTank.getFluidAmount();
			}

			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
					this.zCoord);
		}

		return amount;
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain,
			final boolean doDrain) {
		if (this.outputTank != null) {
			final FluidStack amount = this.outputTank.drain(maxDrain, doDrain);
			this.outputHeight = this.outputTank.getFluidAmount();
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
					this.zCoord);
			return amount;
		} else {
			if (this.inputTank != null) {
				final FluidStack amount = this.inputTank.drain(maxDrain,
						doDrain);
				this.inputHeight = this.inputTank.getFluidAmount();
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
						this.zCoord);
				return amount;
			}
		}

		return null;
	}

	@Override
	public int fill(final ForgeDirection from, final FluidStack resource,
			final boolean doFill) {

		FluidTank tankToFill = null;
		int amount = 0;

		if (this.inputTank != null) {
			if (resource.getFluid().getID() == this.inputTank.getFluid().fluidID) {
				tankToFill = this.inputTank;
			}
		}
		if (this.outputTank != null) {
			if (resource.getFluid().getID() == this.outputTank.getFluid().fluidID) {
				tankToFill = this.outputTank;
			}
		}

		if (tankToFill != null) {
			amount = tankToFill.fill(resource, doFill);
			if (this.inputTank != null) {
				this.inputHeight = this.inputTank.getFluidAmount();
			}
			if (this.outputTank != null) {
				this.outputHeight = this.outputTank.getFluidAmount();
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord,
					this.zCoord);
		}

		return amount;
	}

	public int getInputHeight() {
		return this.inputHeight;
	}

	public CustomFluidTank getInputTank() {
		return this.inputTank;
	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	public int getOutputHeight() {
		return this.outputHeight;
	}

	public CustomFluidTank getOutputTank() {
		return this.outputTank;
	}

	public int getScaledInputFluidAmount(final int i, final int max) {
		return this.inputHeight != 0 ? (int) (((float) this.inputHeight / (max)) * i)
				: 0;
	}

	public int getScaledOutputFluidAmount(final int i, final int max) {
		return this.outputHeight != 0 ? (int) (((float) this.outputHeight / (max)) * i)
				: 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		final ArrayList<FluidTankInfo> info = new ArrayList<FluidTankInfo>();
		if (this.inputTank != null) {
			info.add(this.inputTank.getInfo());
		}

		if (this.outputTank != null) {
			info.add(this.outputTank.getInfo());
		}

		return info.toArray(new FluidTankInfo[] {});
	}

	public void setInputHeight(final int inputHeight) {
		this.inputHeight = inputHeight;
	}

	public void setOutputHeight(final int outputHeight) {
		this.outputHeight = outputHeight;
	}

}
