package org.derbeukatt.underwatercraft.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBoiler implements ISimpleBlockRenderingHandler
{
	private int id;
	
	public RenderBoiler() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}
	
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator.instance.setColorOpaque_F(1F, 1F, 1F);
		
		block.setBlockBounds(0F, 0.8F, 0F, 1F, 1F, 1F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);

		block.setBlockBounds(0F, 0F, 0F, 0.2F, 0.8F, 1F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		
		block.setBlockBounds(0.8F, 0F, 0F, 1F, 0.8F, 1F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}

}
