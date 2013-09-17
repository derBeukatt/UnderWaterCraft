package org.derbeukatt.underwatercraft.common.fluids;

import net.minecraftforge.fluids.Fluid;

public class FluidRainbowBlubber extends Fluid {

	public FluidRainbowBlubber(final String fluidName) {
		super(fluidName);
		this.setDensity(1500);
		this.setViscosity(5000);
	}
}
