package org.derbeukatt.underwatercraft.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.common.blocks.Blocks;

public class EntityBlubberBubblesFX extends EntityFX {

	public EntityBlubberBubblesFX(final World world, final double x,
			final double y, final double z, final double motionX,
			final double motionY, final double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);

		this.func_110125_a(Blocks.boiler.particleIcon);

		this.particleScale = 0.4F;
		this.particleAlpha = this.rand.nextFloat();
		this.particleRed = 0.48F;
		this.particleGreen = 0.44F;
		this.particleBlue = 0F;
		this.particleMaxAge = 2;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.particleScale = (1 - ((float) this.particleAge / this.particleMaxAge));
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

}
