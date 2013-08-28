package org.derbeukatt.underwatercraft.util;

import net.minecraftforge.common.Configuration;

import org.derbeukatt.underwatercraft.blocks.BlockInfo;
import org.derbeukatt.underwatercraft.items.ItemInfo;

import java.io.File;

public class ConfigHandler {

    private static Configuration configuration;

    public static void init(File file)
    {
        configuration = new Configuration(file);

        configuration.load();

        ItemInfo.SCALE_ID = getItemFromConfig(ItemInfo.SCALE_KEY, ItemInfo.SCALE_DEFAULT);
        ItemInfo.HARD_SCALE_ID = getItemFromConfig(ItemInfo.HARD_SCALE_KEY, ItemInfo.HARD_SCALE_DEFAULT);
        ItemInfo.SCALE_PLATE_ID = getItemFromConfig(ItemInfo.SCALE_PLATE_KEY, ItemInfo.SCALE_PLATE_DEFAULT);
        ItemInfo.WHETSTONE_ID = getItemFromConfig(ItemInfo.WHETSTONE_KEY, ItemInfo.WHETSTONE_DEFAULT);
        ItemInfo.SCALING_KNIFE_ID = getItemFromConfig(ItemInfo.SCALING_KNIFE_KEY, ItemInfo.SCALING_KNIFE_DEFAULT);
        
        BlockInfo.BOILER_ID = configuration.getBlock(BlockInfo.BOILER_KEY, BlockInfo.BOILER_DEFAULT).getInt();

        configuration.save();
    }

    private static int getItemFromConfig(String itemKey, int itemDefault)
    {
        return configuration.getItem(itemKey, itemDefault).getInt() - 256;
    }

}
