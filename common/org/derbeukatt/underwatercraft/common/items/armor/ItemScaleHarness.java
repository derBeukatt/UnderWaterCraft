package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraftforge.common.EnumHelper;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;

public class ItemScaleHarness extends ItemScaleArmor {

	public ItemScaleHarness(final int id) {
		super(id, EnumHelper.addArmorMaterial("SCALEARMOR", 10, new int[] { 1,
				3, 2, 1 }, 25), 1);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(ItemInfo.SCALE_HARNESS_UNLOCALIZED_NAME);
	}
}
