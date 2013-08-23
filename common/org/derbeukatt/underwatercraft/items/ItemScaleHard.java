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
 * Date: 05.08.13s
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class ItemScaleHard extends Item {

    public ItemScaleHard(int id)
    {
        super(id);
        setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
        setUnlocalizedName(ItemInfo.HARD_SCALE_UNLOCALIZED_NAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon(new ResourceLocation(ItemInfo.TEXTURE_LOCATION, ItemInfo.HARD_SCALE_ICON).toString());
    }
}
