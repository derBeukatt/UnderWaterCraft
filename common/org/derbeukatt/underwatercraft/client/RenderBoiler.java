package org.derbeukatt.underwatercraft.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBoiler implements ISimpleBlockRenderingHandler {
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

	public RenderBoiler() {
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

		if (metadata > 0) {
			final Icon icon = BlockFluid.func_94424_b("water_still");

			renderer.setRenderBounds(0.0625F, 0.4F, 0.0625F, 0.9375F, 0.9F, 0.9375F);

			final Tessellator tessellator = Tessellator.instance;

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
			tessellator.draw();

			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x,
			final int y, final int z, final Block block, final int modelId,
			final RenderBlocks renderer) {

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

		final int meta = world.getBlockMetadata(x, y, z);

		if (meta > 0) {
			final Icon icon = BlockFluid.func_94424_b("water_still");

			renderer.setRenderBounds(0.0625F, 0.4F, 0.0625F, 0.9375F, 0.9F, 0.9375F);
			renderer.renderFaceYPos(block, x, y, z, icon);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

}
