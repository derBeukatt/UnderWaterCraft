package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

public class ItemBlubberBottle extends ItemFood {

	public ItemBlubberBottle(final int id) {
		super(id, 2, 2, false);
		this.setAlwaysEdible();
		this.setMaxStackSize(1);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(ItemInfo.BLUBBER_BOTTLE_UNLOCALIZED_NAME);
	}

	@Override
	protected void onFoodEaten(final ItemStack item, final World world,
			final EntityPlayer player) {
		if (!world.isRemote) {
			player.addPotionEffect(new PotionEffect(Potion.confusion.getId(),
					300, 1));
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(),
					300, 0));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(),
					300, 0));
		}
	}

}
