package org.derbeukatt.underwatercraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoilerWall;

public class BlockBoilerWall extends BlockContainer {

	public BlockBoilerWall(final int id) {
		super(id, Material.rock);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setStepSound(Block.soundStoneFootstep);
		this.setHardness(3.5F);
		this.setUnlocalizedName(BlockInfo.BOILER_WALL_UNLOCALIZED_NAME);
	}

	@Override
	public void breakBlock(final World world, final int x, final int y,
			final int z, final int id, final int meta) {
		final TileEntityBoilerWall te = (TileEntityBoilerWall) world
				.getBlockTileEntity(x, y, z);

		if ((te != null) && (te.getBoiler() != null)) {
			te.getBoiler().destroyMultiBlock();
		}

		super.breakBlock(world, x, y, z, id, meta);
	}

	@Override
	public TileEntity createNewTileEntity(final World world) {
		// TODO Auto-generated method stub
		return new TileEntityBoilerWall();
	}

	@Override
	public int idDropped(final int par1, final Random par2Random, final int par3) {
		// TODO Auto-generated method stub
		return Block.brick.blockID;
	}

	@Override
	public boolean onBlockActivated(final World world, final int x,
			final int y, final int z, final EntityPlayer player,
			final int side, final float hitX, final float hitY, final float hitZ) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				return false;
			}

			final TileEntityBoilerWall dummy = (TileEntityBoilerWall) world
					.getBlockTileEntity(x, y, z);

			if ((dummy != null) && (dummy.getBoiler() != null)) {
				final TileEntityBoiler boiler = dummy.getBoiler();
				return boiler.getBlockType().onBlockActivated(world,
						boiler.xCoord, boiler.yCoord, boiler.zCoord, player,
						side, hitX, hitY, hitZ);
			}
		}

		return true;
	}

	@Override
	public void registerIcons(final IconRegister register) {
		this.blockIcon = Block.brick.getIcon(0, 0);
	}

}
