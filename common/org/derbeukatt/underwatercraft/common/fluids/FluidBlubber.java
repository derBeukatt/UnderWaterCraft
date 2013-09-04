package org.derbeukatt.underwatercraft.common.fluids;

import net.minecraftforge.fluids.Fluid;

public class FluidBlubber extends Fluid {

	public FluidBlubber(final String fluidName) {
		super(fluidName);
		this.setDensity(1500);
		this.setViscosity(5000);
	}
}
