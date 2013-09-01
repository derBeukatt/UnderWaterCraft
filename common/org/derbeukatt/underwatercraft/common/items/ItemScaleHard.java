package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScaleHard extends Item {

	public ItemScaleHard(final int id) {
		super(id);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(ItemInfo.HARD_SCALE_UNLOCALIZED_NAME);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.itemIcon = register
				.registerIcon(new ResourceLocation(ItemInfo.TEXTURE_LOCATION,
						ItemInfo.HARD_SCALE_ICON).toString());
	}
}
