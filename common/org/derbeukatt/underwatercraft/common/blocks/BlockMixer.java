package org.derbeukatt.underwatercraft.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.UnderWaterCraft;
import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMixer extends BlockContainer {

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

	protected BlockMixer(final int id) {
		super(id, Material.iron);
		this.setCreativeTab(UnderWaterCraftTab.tabUnderWaterCraft);
		this.setHardness(2F);
		this.setStepSound(Block.soundStoneFootstep);
		this.setUnlocalizedName(BlockInfo.MIXER_UNLOCALIZED_NAME);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(final World world, final int x,
			final int y, final int z, final AxisAlignedBB aabb,
			final List list, final Entity entity) {
		this.setBlockBoundsForItemRender();
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

		this.setBlockBoundsForItemRender();
	}

	@Override
	public void breakBlock(final World world, final int x, final int y,
			final int z, final int id, final int meta) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);

		if ((te != null) && (te instanceof TileEntityMixer)) {
			final TileEntityMixer inventory = (TileEntityMixer) te;

			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				final ItemStack stack = inventory.getStackInSlotOnClosing(i);

				if (stack != null) {
					final float spawnX = x + world.rand.nextFloat();
					final float spawnY = y + world.rand.nextFloat();
					final float spawnZ = z + world.rand.nextFloat();

					final EntityItem droppedItem = new EntityItem(world,
							spawnX, spawnY, spawnZ, stack);

					final float mult = 0.05F;

					droppedItem.motionX = (-0.5F + world.rand.nextFloat())
							* mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat())
							* mult;

					world.spawnEntityInWorld(droppedItem);
				}
			}

			for (final ItemStack stack : inventory.dyes) {
				if (stack != null) {
					final float spawnX = x + world.rand.nextFloat();
					final float spawnY = y + world.rand.nextFloat();
					final float spawnZ = z + world.rand.nextFloat();

					final EntityItem droppedItem = new EntityItem(world,
							spawnX, spawnY, spawnZ, stack);

					final float mult = 0.05F;

					droppedItem.motionX = (-0.5F + world.rand.nextFloat())
							* mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat())
							* mult;

					world.spawnEntityInWorld(droppedItem);
				}
			}
		}

		super.breakBlock(world, x, y, z, id, meta);
	}

	@Override
	public TileEntity createNewTileEntity(final World world) {
		return new TileEntityMixer();
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
		return BlockInfo.MIXER_RENDER_ID;
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

			final TileEntityMixer te = (TileEntityMixer) world
					.getBlockTileEntity(x, y, z);

			final ItemStack heldItem = player.inventory.getCurrentItem();
			if (heldItem != null) {
				final FluidStack fluid = FluidContainerRegistry
						.getFluidForFilledItem(player.getCurrentEquippedItem());

				if (fluid != null) {

					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(
								player.inventory.currentItem,
								consumeItem(heldItem));

					}
					te.fill(ForgeDirection.getOrientation(side), fluid, true);
				} else if (FluidContainerRegistry.isContainer(heldItem)) {
					final FluidStack fillFluid = te.getBlubberTank().getFluid();
					final ItemStack fillStack = FluidContainerRegistry
							.fillFluidContainer(fillFluid, heldItem);
					if (fillStack != null) {
						te.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry
								.getFluidForFilledItem(fillStack), true);
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
					}
				} else {
					FMLNetworkHandler.openGui(player, UnderWaterCraft.instance,
							1, world, x, y, z);
				}
				return true;
			} else {
				FMLNetworkHandler.openGui(player, UnderWaterCraft.instance, 1,
						world, x, y, z);
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public void onEntityCollidedWithBlock(final World world, final int x,
			final int y, final int z, final Entity entity) {

		if (world.getBlockMetadata(x, y, z) == 2) {
			if (entity instanceof EntityPlayerMP) {
				entity.attackEntityFrom(DamageSource.inFire, 1F);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(final IconRegister register) {
		this.topIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.MIXER_TOP).toString());
		this.botIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.MIXER_BOTTOM).toString());
		this.sideIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.MIXER_SIDE).toString());
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
