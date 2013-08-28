package org.derbeukatt.underwatercraft.proxies;

import org.derbeukatt.underwatercraft.blocks.BlockInfo;
import org.derbeukatt.underwatercraft.client.RenderBoiler;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderers() {
		RenderBoiler boilerRenderer = new RenderBoiler();
		BlockInfo.BOILER_RENDER_ID = boilerRenderer.getRenderId();
		RenderingRegistry.registerBlockHandler(boilerRenderer);
	}
	
}
