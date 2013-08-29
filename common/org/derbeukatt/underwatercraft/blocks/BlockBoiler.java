package org.derbeukatt.underwatercraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoiler extends Block {

	protected BlockBoiler(int id, Material material) 
	{
		super(id, material);
		setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		setHardness(2F);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName(BlockInfo.BOILER_UNLOCALIZED_NAME);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote)
		{
			ItemStack heldItem = player.inventory.getCurrentItem();
			if (heldItem != null)
	        {
	            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
	            
	            if (fluid != null)
	            {
	            	if(world.getBlockMetadata(x, y, z) == 0)
	            	{
			        	if (!player.capabilities.isCreativeMode)
			        	{
			        		player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));

			        	}
			            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
			            
	            	}
	            }
	            else if (FluidContainerRegistry.isBucket(heldItem))
	            {
	            	if(world.getBlockMetadata(x, y, z) > 0)
	            	{
		                ItemStack fillStack = new ItemStack(Item.bucketWater);
		                
		                if (!player.capabilities.isCreativeMode)
		                {
		                    if (heldItem.stackSize == 1)
		                    {
		                        player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
		                    }
		                    else
		                    {
		                        player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));
		
		                        if (!player.inventory.addItemStackToInventory(fillStack))
		                        {
		                            player.dropPlayerItem(fillStack);
		                        }
		                    }
		                }
	                
		                world.setBlockMetadataWithNotify(x, y, z, 0, 2);
	            	}
	            }
	            else
	            {
	            	System.out.println("Opening GUI!!!");
	            }
	            
	            return true;
	        }
	        else
	        {
	        	System.out.println("Opening GUI!!!");
	        	        	
	        	return true;
	        }
		}
		else
		{
			return true;
		}
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		// TODO Auto-generated method stub
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		
		/* bottom */
		setBlockBounds(0.0F, 0.2F, 0.0F, 1.0F, 0.4F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        
        /* legs */
        setBlockBounds(0F, 0F, 0F, 0.25F, 0.2F, 0.25F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0.75F, 0F, 0F, 1F, 0.2F, 0.25F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0F, 0F, 0.75F, 0.25F, 0.2F, 1F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0.75F, 0F, 0.75F, 1F, 0.2F, 1F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        
        /* sides */
        setBlockBounds(0F, 0.4F, 0F, 1F, 1F, 0.1F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0F, 0.4F, 0.1F, 0.1F, 1F, 0.9F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0.9F, 0.4F, 0.1F, 1F, 1F, 0.9F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        setBlockBounds(0F, 0.4F, 0.9F, 1F, 1F, 1F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		
        this.setBlockBoundsForItemRender();
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
	
	public static ItemStack consumeItem (ItemStack stack)
    {
        if (stack.stackSize == 1)
        {
            if (stack.getItem().hasContainerItem())
                return stack.getItem().getContainerItemStack(stack);
            else
                return null;
        }
        else
        {
            stack.splitStack(1);

            return stack;
        }
    }

}
