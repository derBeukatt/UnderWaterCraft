package org.derbeukatt.underwatercraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			ItemStack itemstack = player.inventory.getCurrentItem();
	
	        if (itemstack == null)
	        {
	            return true;
	        }
	        else
	        {
	            int meta = world.getBlockMetadata(x, y, z);
	
	            if (itemstack.itemID == Item.bucketWater.itemID)
	            {
	                if (meta < 3)
	                {
	                    if (!player.capabilities.isCreativeMode)
	                    {
	                    	player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Item.bucketEmpty));
	                    }
	
	                    world.setBlockMetadataWithNotify(x, y, z, 3, 2);
	                    world.func_96440_m(x, y, z, BlockInfo.BOILER_ID);
	                }
	
	                return true;
	            }
	        }
		}
		
		return true;
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
