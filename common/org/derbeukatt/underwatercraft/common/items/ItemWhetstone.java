package org.derbeukatt.underwatercraft.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWhetstone extends Item {

	public ItemWhetstone(final int id) {
		super(id);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setUnlocalizedName(ItemInfo.WHETSTONE_UNLOCALIZED_NAME);
		this.setHasSubtypes(true);
		this.setMaxDamage(4);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(final ItemStack itemStack,
			final EntityPlayer player, final List list,
			final boolean showAdvancedToolTip) {
		list.add((itemStack.getMaxDamage() - itemStack.getItemDamage())
				+ " uses left.");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister register) {
		this.itemIcon = register.registerIcon(new ResourceLocation(
				ItemInfo.TEXTURE_LOCATION, ItemInfo.WHETSTONE_ICON).toString());
	}
}
