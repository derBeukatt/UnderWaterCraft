package org.derbeukatt.underwatercraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBlubberBoiler extends Block{

	protected BlockBlubberBoiler(int id, Material material) 
	{
		super(id, material);
		setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		setHardness(2F);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName(BlockInfo.BOILER_UNLOCALIZED_NAME);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		return super.collisionRayTrace(world, x, y, z, start, end);
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return BlockInfo.BOILER_RENDER_ID;
	}

}
