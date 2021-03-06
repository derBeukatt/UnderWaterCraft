package org.derbeukatt.underwatercraft.client;

import net.minecraft.client.model.ModelSquid;

import org.derbeukatt.underwatercraft.client.renderers.RenderBoiler;
import org.derbeukatt.underwatercraft.client.renderers.RenderMixer;
import org.derbeukatt.underwatercraft.client.renderers.RenderOctopus;
import org.derbeukatt.underwatercraft.common.CommonProxy;
import org.derbeukatt.underwatercraft.common.blocks.BlockInfo;
import org.derbeukatt.underwatercraft.common.entities.hostile.EntityOctopus;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void initRenderers() {
		final RenderBoiler boilerRenderer = new RenderBoiler();
		BlockInfo.BOILER_RENDER_ID = boilerRenderer.getRenderId();
		RenderingRegistry.registerBlockHandler(boilerRenderer);

		final RenderMixer mixerRenderer = new RenderMixer();
		BlockInfo.MIXER_RENDER_ID = mixerRenderer.getRenderId();
		RenderingRegistry.registerBlockHandler(mixerRenderer);

		RenderingRegistry.registerEntityRenderingHandler(EntityOctopus.class,
				new RenderOctopus(new ModelSquid(), 0.6f));

	}

}
