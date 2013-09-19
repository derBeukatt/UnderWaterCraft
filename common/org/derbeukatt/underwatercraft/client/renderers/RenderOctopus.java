package org.derbeukatt.underwatercraft.client.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.common.entities.hostile.EntityOctopus;
import org.lwjgl.opengl.GL11;

public class RenderOctopus extends RenderLiving {

	private static final ResourceLocation field_110901_a = new ResourceLocation(
			"textures/entity/squid.png");

	public RenderOctopus(final ModelBase par1ModelBase, final float par2) {
		super(par1ModelBase, par2);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final Entity par1Entity, final double par2,
			final double par4, final double par6, final float par8,
			final float par9) {
		this.renderLivingSquid((EntityOctopus) par1Entity, par2, par4, par6,
				par8, par9);
	}

	@Override
	public void doRenderLiving(final EntityLiving par1EntityLiving,
			final double par2, final double par4, final double par6,
			final float par8, final float par9) {
		this.renderLivingSquid((EntityOctopus) par1EntityLiving, par2, par4,
				par6, par8, par9);
	}

	@Override
	protected ResourceLocation func_110775_a(final Entity par1Entity) {
		return this.func_110900_a((EntityOctopus) par1Entity);
	}

	protected ResourceLocation func_110900_a(final EntityOctopus par1EntitySquid) {
		return field_110901_a;
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(
			final EntityLivingBase par1EntityLivingBase, final float par2) {
		return this.handleRotationFloat((EntityOctopus) par1EntityLivingBase,
				par2);
	}

	protected float handleRotationFloat(final EntityOctopus par1EntitySquid,
			final float par2) {
		return par1EntitySquid.prevTentacleAngle
				+ ((par1EntitySquid.tentacleAngle - par1EntitySquid.prevTentacleAngle) * par2);
	}

	/**
	 * Renders the Living Squid.
	 */
	public void renderLivingSquid(final EntityOctopus par1EntityOctopus,
			final double par2, final double par4, final double par6,
			final float par8, final float par9) {
		super.doRenderLiving(par1EntityOctopus, par2, par4, par6, par8, par9);
	}

	@Override
	public void renderPlayer(final EntityLivingBase par1EntityLivingBase,
			final double par2, final double par4, final double par6,
			final float par8, final float par9) {
		this.renderLivingSquid((EntityOctopus) par1EntityLivingBase, par2,
				par4, par6, par8, par9);
	}

	@Override
	protected void rotateCorpse(final EntityLivingBase par1EntityLivingBase,
			final float par2, final float par3, final float par4) {
		this.rotateSquidsCorpse((EntityOctopus) par1EntityLivingBase, par2,
				par3, par4);
	}

	/**
	 * Rotates the Squid's corpse.
	 */
	protected void rotateSquidsCorpse(final EntityOctopus par1EntityOctopus,
			final float par2, final float par3, final float par4) {
		final float f3 = par1EntityOctopus.prevSquidPitch
				+ ((par1EntityOctopus.squidPitch - par1EntityOctopus.prevSquidPitch) * par4);
		final float f4 = par1EntityOctopus.prevSquidYaw
				+ ((par1EntityOctopus.squidYaw - par1EntityOctopus.prevSquidYaw) * par4);
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}
}
