package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;
import org.derbeukatt.underwatercraft.util.PlayerInputMap;

public class ItemScaleBoots extends ItemScaleArmor {

	public ItemScaleBoots(final int id) {
		super(id, EnumHelper.addArmorMaterial("SCALEARMOR", 10, new int[] { 1,
				3, 2, 1 }, 25), 3);
		this.setUnlocalizedName(ItemInfo.SCALE_BOOTS_UNLOCALIZED_NAME);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
	}

	@Override
	public void onArmorTickUpdate(final World world, final EntityPlayer player,
			final ItemStack itemStack) {
		if (player.isInWater() && !player.isRiding()) {

			double thrust = 0.03F;

			final PlayerInputMap inputmap = PlayerInputMap
					.getInputMapFor(player.username);

			Vec3 desiredDirection = player.getLookVec().normalize();
			final double strafeX = desiredDirection.zCoord;
			final double strafeZ = -desiredDirection.xCoord;
			final double flightVerticality = 0.03F;

			desiredDirection.xCoord = (desiredDirection.xCoord * Math
					.signum(inputmap.forwardKey))
					+ (strafeX * Math.signum(inputmap.strafeKey));
			desiredDirection.yCoord = ((flightVerticality
					* desiredDirection.yCoord * Math
						.signum(inputmap.forwardKey)) + (inputmap.jumpKey ? 1
					: 0))
					- (inputmap.downKey ? 1 : 0);
			desiredDirection.zCoord = (desiredDirection.zCoord * Math
					.signum(inputmap.forwardKey))
					+ (strafeZ * Math.signum(inputmap.strafeKey));

			desiredDirection = desiredDirection.normalize();

			// Brakes
			if ((player.motionY < 0) && (desiredDirection.yCoord >= 0)) {
				if (-player.motionY > thrust) {
					player.motionY += thrust;
					thrust = 0;
				} else {
					thrust -= player.motionY;
					player.motionY = 0;
				}
			}
			if (player.motionY < -1) {
				thrust += 1 + player.motionY;
				player.motionY = -1;
			}
			if ((Math.abs(player.motionX) > 0)
					&& (desiredDirection.lengthVector() == 0)) {
				if (Math.abs(player.motionX) > thrust) {
					player.motionX -= Math.signum(player.motionX) * thrust;
					thrust = 0;
				} else {
					thrust -= Math.abs(player.motionX);
					player.motionX = 0;
				}
			}
			if ((Math.abs(player.motionZ) > 0)
					&& (desiredDirection.lengthVector() == 0)) {
				if (Math.abs(player.motionZ) > thrust) {
					player.motionZ -= Math.signum(player.motionZ) * thrust;
					thrust = 0;
				} else {
					thrust -= Math.abs(player.motionZ);
					player.motionZ = 0;
				}
			}

			// Thrusting, finally :V
			final double vx = thrust * desiredDirection.xCoord;
			final double vy = thrust * desiredDirection.yCoord;
			final double vz = thrust * desiredDirection.zCoord;
			player.motionX += vx;
			player.motionY += vy;
			player.motionZ += vz;

			// Slow the player if they are going too fast
			final double horzm2 = (player.motionX * player.motionX)
					+ (player.motionZ * player.motionZ);
			double horzmlim = (25.0F * 25.0F) / 400;
			if (inputmap.sneakKey && (horzmlim > 0.05)) {
				horzmlim = 0.05;
			}

			if (horzm2 > horzmlim) {
				final double ratio = Math.sqrt(horzmlim / horzm2);
				player.motionX *= ratio;
				player.motionZ *= ratio;
			}

			if (player instanceof EntityPlayerMP) {
				((EntityPlayerMP) player).playerNetServerHandler.ticksForFloatKick = 0;
			}
		}
	}
}
