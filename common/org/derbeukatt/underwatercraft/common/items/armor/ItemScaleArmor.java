package org.derbeukatt.underwatercraft.common.items.armor;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.EnumHelper;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

public class ItemScaleArmor extends ItemArmor {

	public ItemScaleArmor(final int id, final int type) {
		super(id, EnumHelper.addArmorMaterial("SCALEARMOR", 10, new int[] { 1,
				3, 2, 1 }, 25), 0, type);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
	}
}
