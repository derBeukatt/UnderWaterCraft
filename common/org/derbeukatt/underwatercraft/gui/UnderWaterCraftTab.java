package org.derbeukatt.underwatercraft.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UnderWaterCraftTab {

    public static CreativeTabs tabUnderWaterCraft = new CreativeTabs("tabUnderWaterCraft") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Item.fishRaw);
        }
    };
}
