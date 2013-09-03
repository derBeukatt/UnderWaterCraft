package org.derbeukatt.underwatercraft.common.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidBlubber extends Fluid {

	public FluidBlubber(final String fluidName) {
		super(fluidName);
		this.setDensity(1500);
		this.setViscosity(600);
		this.setStillIcon(FluidRegistry.LAVA.getStillIcon());
		this.setFlowingIcon(FluidRegistry.LAVA.getFlowingIcon());

		FluidRegistry.registerFluid(this);
	}

}
