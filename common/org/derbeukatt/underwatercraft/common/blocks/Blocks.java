package org.derbeukatt.underwatercraft.common.blocks;

import org.derbeukatt.underwatercraft.common.items.ItemBoilerBlock;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Blocks {

	public static BlockFluidBlubber blubber;
	public static BlockBoiler boiler;

	public static void addNames() {
		LanguageRegistry.addName(boiler, BlockInfo.BOILER_NAME);
		LanguageRegistry.addName(blubber, BlockInfo.BLUBBER_NAME);
	}

	public static void init() {
		boiler = new BlockBoiler(BlockInfo.BOILER_ID);
		GameRegistry.registerBlock(boiler, ItemBoilerBlock.class,
				BlockInfo.BOILER_KEY);
		blubber = new BlockFluidBlubber(BlockInfo.BLUBBER_ID);
		GameRegistry.registerBlock(blubber, BlockInfo.BLUBBER_KEY);
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBoiler.class,
				BlockInfo.BOILER_TE_KEY);
	}

}
