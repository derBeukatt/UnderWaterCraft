package org.derbeukatt.underwatercraft.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
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
import org.derbeukatt.underwatercraft.client.fx.Particles;
import org.derbeukatt.underwatercraft.client.gui.UnderWaterCraftTab;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoiler extends BlockContainer {

	private static final int NR_OF_PARTICLES = 2;

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
	public Icon particleIcon;

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
		// TODO: normal collision box
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
	public void breakBlock(final World world, final int x, final int y,
			final int z, final int id, final int meta) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);

		if ((te != null) && (te instanceof IInventory)) {
			final IInventory inventory = (IInventory) te;

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
	public int damageDropped(final int meta) {
		// TODO: do not need this anymore?
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
					FluidStack fillFluid = null;
					if (te.getBlubberTank().getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME) {
						fillFluid = te.getBlubberTank().getFluid();
					} else {
						fillFluid = te.getWaterTank().getFluid();
					}
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
							0, world, x, y, z);
				}
				return true;
			} else {
				FMLNetworkHandler.openGui(player, UnderWaterCraft.instance, 0,
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

	@Override
	public void onNeighborBlockChange(final World world, final int x,
			final int y, final int z, final int id) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World world, final int x, final int y,
			final int z, final Random rand) {

		final TileEntity blockTileEntity = world.getBlockTileEntity(x, y, z);

		if (blockTileEntity instanceof TileEntityBoiler) {
			final TileEntityBoiler tileBoiler = (TileEntityBoiler) blockTileEntity;

			if (tileBoiler != null) {
				if (tileBoiler.isBoiling()) {

					float fillStand = 0.0F;
					float particleX = 0.0F;
					float particleY = 0.0F;
					float particleZ = 0.0F;

					if (tileBoiler.renderHeight > 0) {

						fillStand = 0.00003125F * tileBoiler.renderHeight;

						particleX = x
								+ ((rand.nextFloat() * (0.4375F - 0.0625F)) + 0.0625F);
						particleY = y
								+ ((rand.nextFloat() * ((0.425F + fillStand) - (0.4F + fillStand))) + (0.4F + fillStand));
						particleZ = z
								+ ((rand.nextFloat() * (0.9375F - 0.0625F)) + 0.0625F);

						for (int i = 0; i < NR_OF_PARTICLES; i++) {
							Particles.BOILERWATERBUBBLES.spawnParticle(world,
									particleX, particleY, particleZ, 0.0D,
									0.0D, 0.0D);
						}
					}

					if (tileBoiler.blubberAmount > 0) {

						fillStand = 0.00003125F * tileBoiler.blubberAmount;

						particleX = x
								+ ((rand.nextFloat() * (0.9375F - 0.5625F)) + 0.5625F);
						particleY = y
								+ ((rand.nextFloat() * ((0.425F + fillStand) - (0.4F + fillStand))) + (0.4F + fillStand));
						particleZ = z
								+ ((rand.nextFloat() * (0.9375F - 0.0625F)) + 0.0625F);

						for (int i = 0; i < NR_OF_PARTICLES; i++) {
							Particles.BOILERBLUBBERBUBBLES.spawnParticle(world,
									particleX, particleY, particleZ, 0.0D,
									0.0D, 0.0D);
						}
					}
				}
			}
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
