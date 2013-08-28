package org.derbeukatt.underwatercraft.blocks;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlubberBoiler extends BlockContainer{

	protected BlockBlubberBoiler(int id, Material material) 
	{
		super(id, material);
		setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		setHardness(2F);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName(BlockInfo.BOILER_UNLOCALIZED_NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
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
