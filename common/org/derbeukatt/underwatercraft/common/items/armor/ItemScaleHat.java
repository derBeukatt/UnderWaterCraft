package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;

public class ItemScaleHat extends ItemScaleArmor {

	public ItemScaleHat(final int id) {
		super(id, EnumHelper.addArmorMaterial("SCALEARMOR", 10, new int[] { 1,
				3, 2, 1 }, 25), 0);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(ItemInfo.SCALE_HAT_UNLOCALIZED_NAME);
	}

	@Override
	public void onArmorTickUpdate(final World world, final EntityPlayer player,
			final ItemStack itemStack) {

		if (!world.isRemote) {
			if (player.isInWater()) {
				final int playerAir = player.getAir();

				if (playerAir < 10) {
					player.setAir(300);
				}

				PotionEffect nightVision = null;
				if (player.isPotionActive(Potion.nightVision.id)) {
					nightVision = player
							.getActivePotionEffect(Potion.nightVision);
				}

				if ((nightVision == null) || (nightVision.getDuration() < 210)) {
					player.addPotionEffect(new PotionEffect(
							Potion.nightVision.id, 500, -3));
				}
			} else {
				PotionEffect nightVision = null;
				if (player.isPotionActive(Potion.nightVision.id)) {
					nightVision = player
							.getActivePotionEffect(Potion.nightVision);
				}
				if ((nightVision != null) && (nightVision.getAmplifier() == -3)) {

					player.removePotionEffect(Potion.nightVision.id);
				}
			}
		}
	}

}
