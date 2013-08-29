package org.derbeukatt.underwatercraft.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBoiler implements ISimpleBlockRenderingHandler
{
	private int id;
	
	public RenderBoiler() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}
	
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
		/* bottom */
		renderer.setRenderBounds(0.0F, 0.2F, 0.0F, 1.0F, 0.4F, 1.0F);
		renderStandardInvBlock(renderer, block, metadata);
        
        /* legs */
        renderer.setRenderBounds(0F, 0F, 0F, 0.25F, 0.2F, 0.25F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0.75F, 0F, 0F, 1F, 0.2F, 0.25F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0F, 0F, 0.75F, 0.25F, 0.2F, 1F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0.75F, 0F, 0.75F, 1F, 0.2F, 1F);
        renderStandardInvBlock(renderer, block, metadata);
        
        /* sides */
        renderer.setRenderBounds(0F, 0.4F, 0F, 1F, 1F, 0.2F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0F, 0.4F, 0.2F, 0.2F, 1F, 0.8F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0.8F, 0.4F, 0.2F, 1F, 1F, 0.8F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0F, 0.4F, 0.8F, 1F, 1F, 1F);
        renderStandardInvBlock(renderer, block, metadata);
        
        if (metadata > 0)
        {
            if (metadata > 1)
            {
            	metadata = 1;
            }
            renderer.setRenderBounds(0.1f, 0.4f, 0.1f, 0.9f, 0.8f, 0.9f);
            renderStandardInvBlock(renderer, Block.waterStill, metadata);
        }
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator.instance.setColorOpaque_F(1F, 1F, 1F);
		
		/* bottom */
		renderer.setRenderBounds(0.0F, 0.2F, 0.0F, 1.0F, 0.4F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
        
        /* legs */
        renderer.setRenderBounds(0F, 0F, 0F, 0.25F, 0.2F, 0.25F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.75F, 0F, 0F, 1F, 0.2F, 0.25F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0F, 0F, 0.75F, 0.25F, 0.2F, 1F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.75F, 0F, 0.75F, 1F, 0.2F, 1F);
        renderer.renderStandardBlock(block, x, y, z);
        
        /* sides */
        renderer.setRenderBounds(0F, 0.4F, 0F, 1F, 1F, 0.1F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0F, 0.4F, 0.1F, 0.1F, 1F, 0.9F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.9F, 0.4F, 0.1F, 1F, 1F, 0.9F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0F, 0.4F, 0.9F, 1F, 1F, 1F);
        renderer.renderStandardBlock(block, x, y, z);
		
        int meta = world.getBlockMetadata(x, y, z);
        
        if (meta > 0)
        {
            if (meta > 1)
            {
                meta = 1;
            }
            renderer.setRenderBounds(0.1f, 0.4f, 0.1f, 0.9f, 0.8f, 0.9f);
            renderer.renderStandardBlock(Block.waterStill, x, y, z);
        }
        
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
	
	public static void renderStandardInvBlock (RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

}
