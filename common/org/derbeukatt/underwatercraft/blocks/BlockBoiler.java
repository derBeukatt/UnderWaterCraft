package org.derbeukatt.underwatercraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.gui.UnderWaterCraftTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoiler extends Block {

	public static ItemStack consumeItem(final ItemStack stack) {
		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem()) {
				return stack.getItem().getContainerItemStack(stack);
			} else {
				return null;
			}
		} else {
			stack.splitStack(1);

			return stack;
		}
	}

	@SideOnly(Side.CLIENT)
	private Icon botIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;

	@SideOnly(Side.CLIENT)
	private Icon topIcon;

	protected BlockBoiler(final int id) {
		super(id, Material.iron);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setHardness(2F);
		this.setStepSound(Block.soundStoneFootstep);
		this.setUnlocalizedName(BlockInfo.BOILER_UNLOCALIZED_NAME);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(final World world, final int x,
			final int y, final int z, final AxisAlignedBB aabb,
			final List list, final Entity entity) {

		/* bottom */
		this.setBlockBounds(0.0F, 0.2F, 0.0F, 1.0F, 0.4F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

		/* legs */
		this.setBlockBounds(0F, 0F, 0F, 0.25F, 0.2F, 0.25F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.75F, 0F, 0F, 1F, 0.2F, 0.25F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0F, 0F, 0.75F, 0.25F, 0.2F, 1F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.75F, 0F, 0.75F, 1F, 0.2F, 1F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

		/* sides */
		this.setBlockBounds(0F, 0.4F, 0F, 1F, 1F, 0.1F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0F, 0.4F, 0.1F, 0.1F, 1F, 0.9F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.9F, 0.4F, 0.1F, 1F, 1F, 0.9F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0F, 0.4F, 0.9F, 1F, 1F, 1F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

		this.setBlockBoundsForItemRender();
	}

	@Override
	public int damageDropped(final int meta) {
		return meta;
	}

	@Override
	public Icon getIcon(final int side, final int meta) {
		if (side == 0) {
			return this.botIcon;
		} else if (side == 1) {
			return this.topIcon;
		} else {
			return this.sideIcon;
		}
	}

	@Override
	public int getRenderType() {
		return BlockInfo.BOILER_RENDER_ID;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final int id, final CreativeTabs tab,
			final List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(final World world, final int x,
			final int y, final int z, final EntityPlayer player,
			final int side, final float hitX, final float hitY, final float hitZ) {
		if (!world.isRemote) {
			final ItemStack heldItem = player.inventory.getCurrentItem();
			if (heldItem != null) {
				final FluidStack fluid = FluidContainerRegistry
						.getFluidForFilledItem(player.getCurrentEquippedItem());

				if (fluid != null) {
					if (world.getBlockMetadata(x, y, z) == 0) {
						if (!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(
									player.inventory.currentItem,
									consumeItem(heldItem));

						}
						world.setBlockMetadataWithNotify(x, y, z, 1, 3);

					}
				} else if (FluidContainerRegistry.isBucket(heldItem)) {
					if (world.getBlockMetadata(x, y, z) > 0) {
						final ItemStack fillStack = new ItemStack(
								Item.bucketWater);

						if (!player.capabilities.isCreativeMode) {
							if (heldItem.stackSize == 1) {
								player.inventory
										.setInventorySlotContents(
												player.inventory.currentItem,
												fillStack);
							} else {
								player.inventory.setInventorySlotContents(
										player.inventory.currentItem,
										consumeItem(heldItem));

								if (!player.inventory
										.addItemStackToInventory(fillStack)) {
									player.dropPlayerItem(fillStack);
								}
							}
						}

						world.setBlockMetadataWithNotify(x, y, z, 0, 3);
					}
				} else {
					System.out.println("Opening GUI!!!");
				}

				return true;
			} else {
				System.out.println("Opening GUI!!!");

				return true;
			}
		} else {
			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(final IconRegister register) {
		this.topIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BOILER_TOP).toString());
		this.botIcon = register
				.registerIcon(new ResourceLocation(BlockInfo.TEXTURE_LOCATION,
						BlockInfo.BOILER_BOTTOM).toString());
		this.sideIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BOILER_SIDE).toString());
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess par1iBlockAccess,
			final int par2, final int par3, final int par4, final int par5) {
		return true;
	}

}
