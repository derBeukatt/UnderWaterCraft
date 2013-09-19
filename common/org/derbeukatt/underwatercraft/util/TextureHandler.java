package org.derbeukatt.underwatercraft.util;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.derbeukatt.underwatercraft.common.blocks.Blocks;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TextureHandler {

	public TextureHandler() {
	}

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void textureHook(final TextureStitchEvent.Post event) {
		if (event.map.textureType == 0) {
			Fluids.fluidBlubber.setIcons(
					Blocks.blubber.getBlockTextureFromSide(1),
					Blocks.blubber.getBlockTextureFromSide(2));

			Fluids.fluidRainbowBlubber.setIcons(
					Blocks.rainbowBlubber.getBlockTextureFromSide(1),
					Blocks.rainbowBlubber.getBlockTextureFromSide(2));
		}
	}

}
