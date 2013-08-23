package org.derbeukatt.underwatercraft.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 10.08.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class ItemScalingKnife extends Item {

    public ItemScalingKnife(int id) {
        super(id);
        setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
        setUnlocalizedName(ItemInfo.SCALING_KNIFE_UNLOCALIZED_NAME);
        setMaxDamage(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon(new ResourceLocation(ItemInfo.TEXTURE_LOCATION, ItemInfo.SCALING_KNIFE_ICON).toString());
    }
}
