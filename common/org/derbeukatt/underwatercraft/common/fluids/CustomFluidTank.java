package org.derbeukatt.underwatercraft.common.fluids;

import java.util.Locale;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTip;
import org.derbeukatt.underwatercraft.client.gui.tooltips.ToolTipLine;

public class CustomFluidTank extends FluidTank {

	protected final ToolTip toolTip = new ToolTip() {
		@Override
		public void refresh() {
			CustomFluidTank.this.refreshTooltip();
		}
	};

	public CustomFluidTank(final Fluid fluid, final int amount,
			final int capacity) {
		super(fluid, amount, capacity);
		// TODO Auto-generated constructor stub
	}

	public CustomFluidTank(final FluidStack stack, final int capacity) {
		super(stack, capacity);
		// TODO Auto-generated constructor stub
	}

	public CustomFluidTank(final int capacity) {
		super(capacity);
		// TODO Auto-generated constructor stub
	}

	public int getScaledFluidAmount(final int i) {
		return this.getFluidAmount() != 0 ? (int) (((float) this
				.getFluidAmount() / this.getCapacity()) * i) : 0;
	}

	public ToolTip getToolTip() {
		return this.toolTip;
	}

	public void refreshTooltip() {
		this.toolTip.clear();
		int amount = 0;
		if ((this.getFluid() != null) && (this.getFluid().amount > 0)) {
			final ToolTipLine fluidName = new ToolTipLine(this.getFluid()
					.getFluid().getName());
			fluidName.setSpacing(2);
			this.toolTip.add(fluidName);
			amount = this.getFluid().amount;
		}
		this.toolTip.add(new ToolTipLine(String.format(Locale.ENGLISH,
				"%,d / %,d", amount, this.getCapacity())));
	}

}
