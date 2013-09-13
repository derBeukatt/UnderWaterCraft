package org.derbeukatt.underwatercraft.common.blocks;

import net.minecraft.block.Block;

import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Blocks {

	public static Block blubber;
	public static BlockBoiler boiler;
	public static Block mixer;

	public static void addNames() {
		LanguageRegistry.addName(boiler, BlockInfo.BOILER_NAME);
		LanguageRegistry.addName(mixer, BlockInfo.MIXER_NAME);
		LanguageRegistry.addName(blubber, BlockInfo.BLUBBER_NAME);
	}

	public static void init() {
		boiler = new BlockBoiler(BlockInfo.BOILER_ID);
		GameRegistry.registerBlock(boiler, BlockInfo.BOILER_KEY);
		mixer = new BlockMixer(BlockInfo.MIXER_ID);
		GameRegistry.registerBlock(mixer, BlockInfo.MIXER_KEY);

		blubber = new BlockFluidBlubber(BlockInfo.BLUBBER_ID);
		GameRegistry.registerBlock(blubber, BlockInfo.BLUBBER_KEY);
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBoiler.class,
				BlockInfo.BOILER_TE_KEY);
		GameRegistry.registerTileEntity(TileEntityMixer.class,
				BlockInfo.MIXER_TE_KEY);
	}

}
