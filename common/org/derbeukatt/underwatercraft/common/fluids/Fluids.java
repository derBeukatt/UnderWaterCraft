package org.derbeukatt.underwatercraft.common.fluids;

import net.minecraftforge.fluids.Fluid;

public class Fluids {

	public static Fluid blubber;

	public static void init() {
		blubber = new FluidBlubber("Blubber");
	}

}
