package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;

public class ItemScaleHat extends ItemScaleArmor {

	public ItemScaleHat(final int id) {
		super(id, 0);
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
			}
		}
	}

}
