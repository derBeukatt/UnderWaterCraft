package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraftforge.common.EnumHelper;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;

public class ItemScaleLeggings extends ItemScaleArmor {

	public ItemScaleLeggings(final int id) {
		super(id, EnumHelper.addArmorMaterial("SCALEARMOR", 10, new int[] { 1,
				3, 2, 1 }, 25), 2);
		this.setUnlocalizedName(ItemInfo.SCALE_LEGGINGS_UNLOCALIZED_NAME);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
	}
}
