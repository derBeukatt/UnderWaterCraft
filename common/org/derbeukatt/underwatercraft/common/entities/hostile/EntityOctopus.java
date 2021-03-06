package org.derbeukatt.underwatercraft.common.entities.hostile;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityOctopus extends EntityMob {

	// private static final UUID field_110189_bq = UUID
	// .fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	// private static final AttributeModifier field_110190_br = (new
	// AttributeModifier(
	// field_110189_bq, "Attacking speed boost", 0.25D, 0))
	// .func_111168_a(false);
	// private float field_70864_bA;
	// public float field_70867_h;
	// public float field_70868_i;
	// private float field_70871_bB;

	// private UnderWaterNavigate navigator;

	public float prevSquidPitch;
	public float prevSquidYaw;
	/** the last calculated angle of the tentacles in radians */
	public float prevTentacleAngle;
	// private float randomMotionSpeed;
	// private float randomMotionVecX;
	// private float randomMotionVecY;
	// private float randomMotionVecZ;

	public float squidPitch;

	public float squidYaw;

	/** angle of the tentacles in radians */
	public float tentacleAngle;

	public EntityOctopus(final World world) {
		super(world);
		// this.navigator = new PathNavigate(this, world);
		// this.navigator = new UnderWaterNavigate(this, world);
		// this.getNavigator().setBreakDoors(true);
		// this.getNavigator().setAvoidsWater(true);
		// this.getNavigator().setCanSwim(true);
		// this.getNavigator().setSpeed(0.25f);
		// this.getNavigator().setAvoidSun(false);

		// t

		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.0D));
		// this.tasks.addTask(2, new EntityOctopusSwimming(this));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this,
				EntityPlayer.class, 1.0D, false));

		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));

		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, 0, true));

		this.setAIMoveSpeed(0.25f);

		this.setSize(1.0f, 1.0f);
		// this.field_70864_bA = (1.0F / (this.rand.nextFloat() + 1.0F)) * 0.2F;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		// this.dataWatcher.addObject(16, new Byte((byte) 0));
	}

	@Override
	protected Entity findPlayerToAttack() {
		final double d0 = 16.0D;
		return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);

	}

	/**
	 * Takes a coordinate in and returns a weight to determine how likely this
	 * creature will try to path to the block. Args: x, y, z
	 */
	@Override
	public float getBlockPathWeight(final int par1, final int par2,
			final int par3) {
		if (this.worldObj.getBlockMaterial(par1, par2, par3) == Material.water) {
			return 1.0F;
		} else {
			return 0.5F - this.worldObj.getLightBrightness(par1, par2, par3);
		}

	}

	//
	// /**
	// * Checks if the entity's current position is a valid location to spawn
	// this
	// * entity.
	// */
	// @Override
	// public boolean getCanSpawnHere() {
	//
	// // return true;
	// return this.worldObj.checkNoEntityCollision(this.boundingBox)
	// && this.worldObj.getCollidingBoundingBoxes(this,
	// this.boundingBox).isEmpty()
	// && this.worldObj.isAnyLiquid(this.boundingBox);
	//
	// }
	//
	// // @Override
	// // protected void func_110147_ax() {
	// // super.func_110147_ax();
	// // this.func_110148_a(SharedMonsterAttributes.field_111267_a)
	// // .func_111128_a(10.0D);
	// // }
	//
	// @Override
	// public PathNavigate getNavigator() {
	// return this.navigator;
	// }

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	// /**
	// * Moves the entity based on the specified heading. Args: strafe, forward
	// */
	// @Override
	// public void moveEntityWithHeading(final float par1, final float par2) {
	// this.moveEntity(this.motionX, this.motionY, this.motionZ);
	// }
	//
	// /**
	// * Called frequently so the entity can update its state every tick as
	// * required. For example, zombies and skeletons use this to react to
	// * sunlight and start to burn.
	// */
	// @Override
	// public void onLivingUpdate() {
	// super.onLivingUpdate();
	// // this.prevSquidPitch = this.squidPitch;
	// // this.prevSquidYaw = this.squidYaw;
	// // this.field_70868_i = this.field_70867_h;
	// // this.prevTentacleAngle = this.tentacleAngle;
	// // this.field_70867_h += this.field_70864_bA;
	// //
	// // if (this.field_70867_h > ((float) Math.PI * 2F)) {
	// // this.field_70867_h -= ((float) Math.PI * 2F);
	// //
	// // if (this.rand.nextInt(10) == 0) {
	// // this.field_70864_bA = (1.0F / (this.rand.nextFloat() + 1.0F)) * 0.2F;
	// // }
	// // }
	// //
	// // if (this.isInWater()) {
	// // float f;
	// //
	// // if (this.field_70867_h < (float) Math.PI) {
	// // f = this.field_70867_h / (float) Math.PI;
	// // this.tentacleAngle = MathHelper.sin(f * f * (float) Math.PI)
	// // * (float) Math.PI * 0.25F;
	// //
	// // if (f > 0.75D) {
	// // this.randomMotionSpeed = 1.0F;
	// // this.field_70871_bB = 1.0F;
	// // } else {
	// // this.field_70871_bB *= 0.8F;
	// // }
	// // } else {
	// // this.tentacleAngle = 0.0F;
	// // this.randomMotionSpeed *= 0.9F;
	// // this.field_70871_bB *= 0.99F;
	// // }
	// //
	// // if (!this.worldObj.isRemote) {
	// // this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
	// // this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
	// // this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
	// // }
	// //
	// // f = MathHelper.sqrt_double((this.motionX * this.motionX)
	// // + (this.motionZ * this.motionZ));
	// // this.renderYawOffset += (((-((float) Math.atan2(this.motionX,
	// // this.motionZ)) * 180.0F) / (float) Math.PI) - this.renderYawOffset) *
	// // 0.1F;
	// // this.rotationYaw = this.renderYawOffset;
	// // this.squidYaw += (float) Math.PI * this.field_70871_bB * 1.5F;
	// // this.squidPitch += (((-((float) Math.atan2(f, this.motionY)) *
	// // 180.0F) / (float) Math.PI) - this.squidPitch) * 0.1F;
	// // } else {
	// // this.tentacleAngle = MathHelper.abs(MathHelper
	// // .sin(this.field_70867_h)) * (float) Math.PI * 0.25F;
	// //
	// // if (!this.worldObj.isRemote) {
	// // this.motionX = 0.0D;
	// // this.motionY -= 0.08D;
	// // this.motionY *= 0.9800000190734863D;
	// // this.motionZ = 0.0D;
	// // }
	// //
	// // this.squidPitch = (float) (this.squidPitch + ((-90.0F -
	// // this.squidPitch) * 0.02D));
	// // }
	// }

	// @Override
	// protected void updateEntityActionState() {
	// // // ++this.entityAge;
	// //
	// // if (this.entityAge > 100) {
	// // this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ
	// // = 0.0F;
	// // } else if ((this.rand.nextInt(50) == 0)
	// // || !this.inWater
	// // || ((this.randomMotionVecX == 0.0F)
	// // && (this.randomMotionVecY == 0.0F) && (this.randomMotionVecZ ==
	// // 0.0F))) {
	// // final float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
	// // this.randomMotionVecX = MathHelper.cos(f) * 0.2F;
	// // this.randomMotionVecY = -0.1F + (this.rand.nextFloat() * 0.2F);
	// // this.randomMotionVecZ = MathHelper.sin(f) * 0.2F;
	// // }
	// // this.despawnEntity();
	//
	// }

}
