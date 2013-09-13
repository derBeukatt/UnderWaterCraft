package org.derbeukatt.underwatercraft.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidRegistry;

import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderMixer implements ISimpleBlockRenderingHandler {
	public static void renderStandardInvBlock(final RenderBlocks renderblocks,
			final Block block, final int meta) {
		final Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D,
				block.getIcon(5, meta));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	private final int id;

	public RenderMixer() {
		this.id = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public int getRenderId() {
		return this.id;
	}

	@Override
	public void renderInventoryBlock(final Block block, final int metadata,
			final int modelID, final RenderBlocks renderer) {

		/* bottom */
		renderer.setRenderBounds(0.0F, 0.25F, 0.0F, 1.0F, 0.4F, 1.0F);
		renderStandardInvBlock(renderer, block, metadata);

		/* legs */
		renderer.setRenderBounds(0F, 0F, 0F, 0.25F, 0.25F, 0.25F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0.75F, 0F, 0F, 1F, 0.25F, 0.25F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0F, 0F, 0.75F, 0.25F, 0.25F, 1F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0.75F, 0F, 0.75F, 1F, 0.25F, 1F);
		renderStandardInvBlock(renderer, block, metadata);

		/* sides */
		renderer.setRenderBounds(0F, 0.4F, 0F, 1F, 1F, 0.0625F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0F, 0.4F, 0.0625F, 0.0625F, 1F, 0.9375F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0.9375F, 0.4F, 0.0625F, 1F, 1F, 0.9375F);
		renderStandardInvBlock(renderer, block, metadata);
		renderer.setRenderBounds(0F, 0.4F, 0.9375F, 1F, 1F, 1F);
		renderStandardInvBlock(renderer, block, metadata);
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x,
			final int y, final int z, final Block block, final int modelId,
			final RenderBlocks renderer) {

		final TileEntityMixer te = (TileEntityMixer) world.getBlockTileEntity(
				x, y, z);

		Tessellator.instance.setColorOpaque_F(1F, 1F, 1F);

		/* bottom */
		renderer.setRenderBounds(0.0F, 0.25F, 0.0F, 1.0F, 0.4F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);

		/* legs */
		renderer.setRenderBounds(0F, 0F, 0F, 0.25F, 0.25F, 0.25F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.75F, 0F, 0F, 1F, 0.25F, 0.25F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0F, 0F, 0.75F, 0.25F, 0.25F, 1F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.75F, 0F, 0.75F, 1F, 0.25F, 1F);
		renderer.renderStandardBlock(block, x, y, z);

		/* sides */
		renderer.setRenderBounds(0F, 0.4F, 0F, 1F, 1F, 0.0625F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0F, 0.4F, 0.0625F, 0.0625F, 1F, 0.9375F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.9375F, 0.4F, 0.0625F, 1F, 1F, 0.9375F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0F, 0.4F, 0.9375F, 1F, 1F, 1F);
		renderer.renderStandardBlock(block, x, y, z);

		if (te.renderHeight > 0) {
			/* render water tank */
			renderer.setRenderBounds(0.0625F, 0.4F, 0.0625F, 0.9375F,
					0.4F + (te.renderHeight * 0.00003125F), 0.9375F);
			renderer.renderFaceYPos(block, x, y, z,
					FluidRegistry.WATER.getIcon());
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

}
