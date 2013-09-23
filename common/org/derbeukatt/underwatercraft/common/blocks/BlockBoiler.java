package org.derbeukatt.underwatercraft.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.derbeukatt.underwatercraft.util.CoordHelper;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoiler extends BlockContainer {

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
	private Icon frontIcon;

	@SideOnly(Side.CLIENT)
	private Icon frontIconLit;
	@SideOnly(Side.CLIENT)
	public Icon particleIcon;

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
		this.setBlockBoundsForItemRender();
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);

		this.setBlockBoundsForItemRender();
	}

	@Override
	public void breakBlock(final World world, final int x, final int y,
			final int z, final int id, final int meta) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);

		if ((te != null) && (te instanceof TileEntityBoiler)) {
			final TileEntityBoiler inventory = (TileEntityBoiler) te;

			inventory.destroyMultiBlock();

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
		}

		super.breakBlock(world, x, y, z, id, meta);
	}

	@Override
	public TileEntity createNewTileEntity(final World world) {
		return new TileEntityBoiler();
	}

	@Override
	public Icon getIcon(final int side, final int meta) {

		final boolean isActive = ((meta >> 2) == 1);
		final int facing = (meta & CoordHelper.META_MASK_DIR);

		if (side == CoordHelper.getSideForBlockFace(facing)) {
			if (isActive) {
				return this.frontIconLit;
			}
			return this.frontIcon;
		} else {
			return this.blockIcon;
		}
	}

	@Override
	public int getLightValue(final IBlockAccess world, final int x,
			final int y, final int z) {
		return ((world.getBlockMetadata(x, y, z) >> 2) == 0 ? 0 : 15);
	}

	@Override
	public int getRenderType() {
		return BlockInfo.BOILER_RENDER_ID;
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

			final TileEntityBoiler te = (TileEntityBoiler) world
					.getBlockTileEntity(x, y, z);
			if (!te.isValidMultiBlock) {
				if (te.checkIfMultiBlock()) {
					te.makeMultiBlock();
				}
			} else {
				final ItemStack heldItem = player.inventory.getCurrentItem();
				if (heldItem != null) {
					final FluidStack fluid = FluidContainerRegistry
							.getFluidForFilledItem(player
									.getCurrentEquippedItem());

					if (fluid != null) {

						if (!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(
									player.inventory.currentItem,
									consumeItem(heldItem));

						}
						te.fill(ForgeDirection.getOrientation(side), fluid,
								true);
					} else if (FluidContainerRegistry.isContainer(heldItem)) {
						FluidStack fillFluid = null;
						if (te.getBlubberTank().getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME) {
							fillFluid = te.getBlubberTank().getFluid();
						} else {
							fillFluid = te.getWaterTank().getFluid();
						}
						final ItemStack fillStack = FluidContainerRegistry
								.fillFluidContainer(fillFluid, heldItem);
						if (fillStack != null) {
							te.drain(ForgeDirection.UNKNOWN,
									FluidContainerRegistry
											.getFluidForFilledItem(fillStack),
									true);
							if (!player.capabilities.isCreativeMode) {
								if (heldItem.stackSize == 1) {
									player.inventory.setInventorySlotContents(
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
						FMLNetworkHandler.openGui(player,
								UnderWaterCraft.instance, 0, world, x, y, z);
					}

				} else {
					FMLNetworkHandler.openGui(player, UnderWaterCraft.instance,
							0, world, x, y, z);
				}
			}
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void onBlockPlacedBy(final World world, final int x, final int y,
			final int z, final EntityLivingBase entity,
			final ItemStack itemStack) {
		final int metadata = CoordHelper.getBlockFacing(entity);

		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World world, final int x, final int y,
			final int z, final Random rand) {
		final int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & CoordHelper.META_ISACTIVE) == 0) {
			return;
		}

		final int facing = metadata & CoordHelper.META_MASK_DIR;

		final double yMod = (0.3 * rand.nextDouble());
		double xMod = -0.02;
		double zMod = (0.75 - (0.5 * rand.nextDouble()));
		double temp = 0.0;

		switch (facing) {
		case CoordHelper.META_DIR_EAST:
			xMod += 1.04;
			break;

		case CoordHelper.META_DIR_NORTH:
			temp = xMod;
			xMod = zMod;
			zMod = temp;
			break;

		case CoordHelper.META_DIR_SOUTH:
			temp = xMod;
			xMod = zMod;
			zMod = temp + 1.04;
			break;

		default:
			break;
		}

		world.spawnParticle("smoke", x + xMod, y + yMod, z + zMod, 0, 0, 0);
		world.spawnParticle("flame", x + xMod, y + yMod, z + zMod, 0, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(final IconRegister register) {
		this.blockIcon = Block.brick.getIcon(0, 0);
		this.frontIconLit = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BOILER_FRONT_LIT)
				.toString());
		this.frontIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BOILER_FRONT).toString());
		this.particleIcon = register.registerIcon(new ResourceLocation(
				BlockInfo.TEXTURE_LOCATION, BlockInfo.BOILER_PARTICLE)
				.toString());
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
