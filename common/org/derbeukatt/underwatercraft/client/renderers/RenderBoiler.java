package org.derbeukatt.underwatercraft.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidRegistry;

import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.derbeukatt.underwatercraft.util.CoordHelper;
import org.derbeukatt.underwatercraft.util.CoordHelper.CoordTuple;
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

		/* block */
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderStandardInvBlock(renderer, block, 2);
	}

	private void renderLiquids(final int startX, final int startY,
			final int startZ, final int depthMultiplier,
			final boolean forwardZ, final RenderBlocks renderer,
			final int renderHeight, final boolean renderBlubber,
			final IBlockAccess world) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = 0; horiz < 1; horiz++) // Horizontal (X or Z)
		{
			for (int depth = -1; depth <= 1; depth++) // Depth (Z or X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				Icon stillIcon = FluidRegistry.WATER.getStillIcon();
				Icon flowingIcon = FluidRegistry.WATER.getFlowingIcon();

				if (renderBlubber) {
					stillIcon = Fluids.blubber.getStillIcon();
					flowingIcon = Fluids.blubber.getFlowingIcon();
				}

				if ((stillIcon != null) && (flowingIcon != null)) {
					renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F,
							(renderHeight * 0.00033F), 1.0F);
					BlockSkinRenderHelper.renderLiquidBlock(stillIcon,
							flowingIcon, x, y, z, renderer, world);
				}
			}
		}
	}

	@Override
	public boolean renderWorldBlock(final IBlockAccess world, final int x,
			final int y, final int z, final Block block, final int modelId,
			final RenderBlocks renderer) {

		final TileEntityBoiler te = (TileEntityBoiler) world
				.getBlockTileEntity(x, y, z);

		Tessellator.instance.setColorOpaque_F(1F, 1F, 1F);

		/* block */
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);

		final int meta = world.getBlockMetadata(x, y, z);

		if (te.isValidMultiBlock) {

			if (te.renderHeight > 0) {

				final CoordTuple coordTuple = CoordHelper
						.getDirectionSensitiveCoordTuple(meta, x, z, -1, 2);

				final int nrOfLayers = (te.renderHeight / 3000) + 1;
				int heightToRender = te.renderHeight;
				for (int i = 0; i < nrOfLayers; i++) {
					heightToRender = heightToRender - (3000 * i);
					this.renderLiquids(coordTuple.getX(), y + i,
							coordTuple.getZ(), coordTuple.getDepthMultiplier(),
							coordTuple.isForwardZ(), renderer, heightToRender,
							false, world);
				}
			}

			if (te.blubberAmount > 0) {
				final CoordTuple coordTuple = CoordHelper
						.getDirectionSensitiveCoordTuple(meta, x, z, 1, 2);
				final int nrOfLayers = (te.blubberAmount / 3000) + 1;
				int heightToRender = te.blubberAmount;
				for (int i = 0; i < nrOfLayers; i++) {
					heightToRender = heightToRender - (3000 * i);
					this.renderLiquids(coordTuple.getX(), y + i,
							coordTuple.getZ(), coordTuple.getDepthMultiplier(),
							coordTuple.isForwardZ(), renderer, heightToRender,
							true, world);
				}
			}
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

}
