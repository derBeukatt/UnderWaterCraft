package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ISpecialArmor;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

public class ItemScaleHat extends ItemArmor implements ISpecialArmor {

	public ItemScaleHat(final int id) {
		super(id, EnumHelper.addArmorMaterial("SCALEHAT", 10, new int[] { 2, 5,
				3, 1 }, 25), 0, 0);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
	}

	@Override
	public void damageArmor(final EntityLivingBase entity,
			final ItemStack stack, final DamageSource source, final int damage,
			final int slot) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getArmorDisplay(final EntityPlayer player,
			final ItemStack armor, final int slot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArmorProperties getProperties(final EntityLivingBase player,
			final ItemStack armor, final DamageSource source,
			final double damage, final int slot) {
		// TODO Auto-generated method stub
		return null;
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
