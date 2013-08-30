package org.derbeukatt.underwatercraft.blocks;

import org.derbeukatt.underwatercraft.items.ItemBoilerBlock;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Blocks {

	public static BlockBoiler boiler;

	public static void addNames() {
		LanguageRegistry.addName(boiler, BlockInfo.BOILER_NAME);
	}

	public static void init() {
		boiler = new BlockBoiler(BlockInfo.BOILER_ID);
		GameRegistry.registerBlock(boiler, ItemBoilerBlock.class,
				BlockInfo.BOILER_KEY);
	}

}
