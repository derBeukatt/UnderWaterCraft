package org.derbeukatt.underwatercraft.util;

import java.io.File;

import net.minecraftforge.common.Configuration;

import org.derbeukatt.underwatercraft.common.blocks.BlockInfo;
import org.derbeukatt.underwatercraft.common.items.ItemInfo;

public class ConfigHandler {

	private static Configuration configuration;

	private static int getItemFromConfig(final String itemKey,
			final int itemDefault) {
		return configuration.getItem(itemKey, itemDefault).getInt() - 256;
	}

	public static void init(final File file) {
		configuration = new Configuration(file);

		configuration.load();

		ItemInfo.SCALE_ID = getItemFromConfig(ItemInfo.SCALE_KEY,
				ItemInfo.SCALE_DEFAULT);
		ItemInfo.HARD_SCALE_ID = getItemFromConfig(ItemInfo.HARD_SCALE_KEY,
				ItemInfo.HARD_SCALE_DEFAULT);
		ItemInfo.SCALE_PLATE_ID = getItemFromConfig(ItemInfo.SCALE_PLATE_KEY,
				ItemInfo.SCALE_PLATE_DEFAULT);
		ItemInfo.WHETSTONE_ID = getItemFromConfig(ItemInfo.WHETSTONE_KEY,
				ItemInfo.WHETSTONE_DEFAULT);
		ItemInfo.SCALING_KNIFE_ID = getItemFromConfig(
				ItemInfo.SCALING_KNIFE_KEY, ItemInfo.SCALING_KNIFE_DEFAULT);
		ItemInfo.BLUBBER_BUCKET_ID = getItemFromConfig(
				ItemInfo.BLUBBER_BUCKET_KEY, ItemInfo.BLUBBER_BUCKET_DEFAULT);
		ItemInfo.RAINBOW_BLUBBER_BUCKET_ID = getItemFromConfig(
				ItemInfo.RAINBOW_BLUBBER_BUCKET_KEY,
				ItemInfo.RAINBOW_BLUBBER_BUCKET_DEFAULT);
		ItemInfo.BLUBBER_BOTTLE_ID = getItemFromConfig(
				ItemInfo.BLUBBER_BOTTLE_KEY, ItemInfo.BLUBBER_BOTTLE_DEFAULT);
		ItemInfo.RAINBOW_BLUBBER_BOTTLE_ID = getItemFromConfig(
				ItemInfo.RAINBOW_BLUBBER_BOTTLE_KEY,
				ItemInfo.RAINBOW_BLUBBER_BOTTLE_DEFAULT);
		ItemInfo.SCALE_HAT_ID = getItemFromConfig(ItemInfo.SCALE_HAT_KEY,
				ItemInfo.SCALE_HAT_DEFAULT);
		ItemInfo.SCALE_HARNESS_ID = getItemFromConfig(
				ItemInfo.SCALE_HARNESS_KEY, ItemInfo.SCALE_HARNESS_DEFAULT);
		ItemInfo.SCALE_LEGGINGS_ID = getItemFromConfig(
				ItemInfo.SCALE_LEGGINGS_KEY, ItemInfo.SCALE_LEGGINGS_DEFAULT);
		ItemInfo.SCALE_BOOTS_ID = getItemFromConfig(ItemInfo.SCALE_BOOTS_KEY,
				ItemInfo.SCALE_BOOTS_DEFAULT);

		BlockInfo.BOILER_ID = configuration.getBlock(BlockInfo.BOILER_KEY,
				BlockInfo.BOILER_DEFAULT).getInt();
		BlockInfo.BOILER_WALL_ID = configuration.getBlock(
				BlockInfo.BOILER_WALL_KEY, BlockInfo.BOILER_WALL_DEFAULT)
				.getInt();
		BlockInfo.MIXER_ID = configuration.getBlock(BlockInfo.MIXER_KEY,
				BlockInfo.MIXER_DEFAULT).getInt();
		BlockInfo.BLUBBER_ID = configuration.getBlock(BlockInfo.BLUBBER_KEY,
				BlockInfo.BLUBBER_DEFAULT).getInt();
		BlockInfo.RAINBOW_BLUBBER_ID = configuration.getBlock(
				BlockInfo.RAINBOW_BLUBBER_KEY,
				BlockInfo.RAINBOW_BLUBBER_DEFAULT).getInt();

		configuration.save();
	}

}
