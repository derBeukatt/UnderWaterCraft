package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.Items;

public class ItemScaleArmor extends ItemArmor implements ISpecialArmor {

	private final EnumArmorMaterial material;

	public ItemScaleArmor(final int id, final EnumArmorMaterial material,
			final int type) {
		super(id, material, 0, type);
		this.material = material;
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
	}

	@Override
	public void damageArmor(final EntityLivingBase entity,
			final ItemStack stack, final DamageSource source, final int damage,
			final int slot) {
		if (!source.equals(DamageSource.drown)) {
			int divider = 1;

			if (entity.isInWater()) {
				divider = 2;
			}

			stack.damageItem(damage / divider, entity);
		}
	}

	@Override
	public int getArmorDisplay(final EntityPlayer player,
			final ItemStack armor, final int slot) {
		int multiplier = 1;

		if (player.isInWater()) {
			multiplier = 2;
		}

		if ((armor.itemID == Items.itemScaleHat.itemID)
				|| (armor.itemID == Items.itemScaleHarness.itemID)
				|| (armor.itemID == Items.itemScaleLeggings.itemID)
				|| (armor.itemID == Items.itemScaleBoots.itemID)) {
			return this.material.getDamageReductionAmount(slot) * multiplier;
		}

		return 0;
	}

	@Override
	public ArmorProperties getProperties(final EntityLivingBase player,
			final ItemStack armor, final DamageSource source,
			final double damage, final int slot) {
		int multiplier = 1;

		if (player.isInWater()) {
			multiplier = 2;
		}

		final double armorRatio = (this.material.getDamageReductionAmount(slot) * multiplier) / 25D;
		final int armorMax = (armor.getMaxDamage() + 1) - armor.getItemDamage();

		return new ArmorProperties(1, armorRatio, armorMax);
	}
}
