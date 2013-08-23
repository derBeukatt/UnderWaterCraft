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
 * Date: 05.08.13
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class ItemScalePlate extends Item {

    public ItemScalePlate(int id)
    {
        super(id);
        setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
        setUnlocalizedName(ItemInfo.SCALE_PLATE_UNLOCALIZED_NAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon(new ResourceLocation(ItemInfo.TEXTURE_LOCATION, ItemInfo.SCALE_PLATE_ICON).toString());
    }
}
