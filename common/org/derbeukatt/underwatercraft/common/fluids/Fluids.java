package org.derbeukatt.underwatercraft.common.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Fluids {

	public static Fluid blubber;
	public static Fluid fluidBlubber;

	public static Fluid fluidRainbowBlubber;
	public static Fluid rainbowBlubber;

	public static void init() {
		fluidBlubber = new FluidBlubber("blubber");
		FluidRegistry.registerFluid(fluidBlubber);
		blubber = FluidRegistry.getFluid("blubber");

		fluidRainbowBlubber = new FluidBlubber("rainbowblubber");
		FluidRegistry.registerFluid(fluidRainbowBlubber);
		rainbowBlubber = FluidRegistry.getFluid("rainbowblubber");
	}

}
