package org.derbeukatt.underwatercraft.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Blocks {

	public static Block boiler;
	
	public static void init()
	{
		boiler = new BlockBlubberBoiler(BlockInfo.BOILER_ID, Material.wood);
		GameRegistry.registerBlock(boiler, BlockInfo.BOILER_KEY);
	}
	
	public static void addNames()
	{
		LanguageRegistry.addName(boiler, BlockInfo.BOILER_NAME);
	}
	
}
