package org.derbeukatt.underwatercraft.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public enum Particles {
	BOILERBLUBBERBUBBLES, BOILERWATERBUBBLES;

	public void spawnParticle(final World world, final double x,
			final double y, final double z, final double motionX,
			final double motionY, final double motionZ) {
		final Minecraft mc = Minecraft.getMinecraft();
		if ((mc != null) && (mc.renderViewEntity != null)
				&& (mc.effectRenderer != null)) {
			final int particleSetting = mc.gameSettings.particleSetting;

			if ((particleSetting == 2)
					|| ((particleSetting == 1) && (world.rand.nextInt(3) == 0))) {
				return;
			}

			final double distanceX = mc.renderViewEntity.posX - x;
			final double distanceY = mc.renderViewEntity.posY - y;
			final double distanceZ = mc.renderViewEntity.posZ - z;

			final double maxDistance = 16;
			if (((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ)) > (maxDistance * maxDistance)) {
				return;
			}

			EntityFX particleEffect = null;
			switch (this) {
			case BOILERWATERBUBBLES:
				particleEffect = new EntityWaterBubblesFX(world, x, y, z,
						motionX, motionY, motionZ);
				break;
			case BOILERBLUBBERBUBBLES:
				particleEffect = new EntityBlubberBubblesFX(world, x, y, z,
						motionX, motionY, motionZ);
				break;
			}

			if (particleEffect != null) {
				Minecraft.getMinecraft().effectRenderer
						.addEffect(particleEffect);
			}
		}
	}
}
