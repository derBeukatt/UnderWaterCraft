package org.derbeukatt.underwatercraft.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 16.08.13
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class UnderWaterCraftTab {

    public static CreativeTabs tabUnderWaterCraft = new CreativeTabs("tabUnderWaterCraft") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Item.fishRaw);
        }
    };
}
