package org.derbeukatt.underwatercraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomBucket extends ItemBucket {

	private String iconName;

	public ItemCustomBucket(final int id, final int blockId) {
		super(id, blockId);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setContainerItem(Item.bucketEmpty);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.itemIcon = register.registerIcon(new ResourceLocation(
				ItemInfo.TEXTURE_LOCATION, this.iconName).toString());
	}

	@Override
	public Item setUnlocalizedName(final String name) {
		this.iconName = name;
		return super.setUnlocalizedName(name);
	}
}
