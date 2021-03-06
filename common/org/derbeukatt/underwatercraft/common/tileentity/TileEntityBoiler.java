package org.derbeukatt.underwatercraft.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.common.blocks.Blocks;
import org.derbeukatt.underwatercraft.common.fluids.CustomFluidTank;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.util.CoordHelper;
import org.derbeukatt.underwatercraft.util.CoordHelper.CoordTuple;

public class TileEntityBoiler extends TileEntityWithTanks implements
		ISidedInventory {

	public int cookTime;
	public int heatUpTime;

	private boolean isBoiling;
	public boolean isValidMultiBlock;
	private final ItemStack[] items;

	public TileEntityBoiler() {
		this.items = new ItemStack[3];
		this.maxCapacity = 6 * FluidContainerRegistry.BUCKET_VOLUME;
		this.outputTank = new CustomFluidTank(Fluids.blubber, 0,
				this.maxCapacity);
		this.inputTank = new CustomFluidTank(FluidRegistry.WATER, 0,
				this.maxCapacity);
		this.isValidMultiBlock = false;
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack,
			final int j) {
		return i == 2;
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack,
			final int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	private boolean canSmelt() {
		if ((this.getStackInSlot(0) == null)
				|| (this.getInputTank().getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME)
				|| !this.isBoiling) {
			return false;
		} else {
			if ((this.outputTank.getFluidAmount() + FluidContainerRegistry.BUCKET_VOLUME) <= this.maxCapacity) {
				return true;
			}

			return false;
		}
	}

	private boolean checkBottomLayer(final int startX, final int startY,
			final int startZ, final int depthMultiplier, final boolean forwardZ) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = -1; horiz <= 1; horiz++) // Horizontal (X or Z)
		{
			for (int depth = -1; depth <= 1; depth++) // Depth (Z or X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				final int blockId = this.worldObj.getBlockId(x, y, z);
				if ((blockId != Block.brick.blockID)
						&& (blockId != Blocks.boilerWall.blockID)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkHeatSource(final int startX, final int startY,
			final int startZ, final int depthMultiplier, final boolean forwardZ) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = -1; horiz <= 1; horiz++) // Horizontal (X or Z)
		{
			for (int depth = -1; depth <= 1; depth++) // Depth (Z or X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				final int blockId = this.worldObj.getBlockId(x, y, z);
				if (blockId != Block.fire.blockID) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkIfMultiBlock() {
		boolean result = true;

		final int meta = this.worldObj.getBlockMetadata(this.xCoord,
				this.yCoord, this.zCoord);

		final CoordTuple coordTuple = CoordHelper
				.getDirectionSensitiveCoordTuple(meta, this.xCoord,
						this.zCoord, 0, 2);

		result = result
				&& this.checkHeatSource(coordTuple.getX(), this.yCoord - 2,
						coordTuple.getZ(), coordTuple.getDepthMultiplier(),
						coordTuple.isForwardZ());

		result = result
				&& this.checkBottomLayer(coordTuple.getX(), this.yCoord - 1,
						coordTuple.getZ(), coordTuple.getDepthMultiplier(),
						coordTuple.isForwardZ());

		result = result
				&& this.checkMiddleLayer(coordTuple.getX(), this.yCoord,
						coordTuple.getZ(), coordTuple.getDepthMultiplier(),
						coordTuple.isForwardZ());

		result = result
				&& this.checkTopLayer(coordTuple.getX(), this.yCoord + 1,
						coordTuple.getZ(), coordTuple.getDepthMultiplier(),
						coordTuple.isForwardZ());

		return result;
	}

	private boolean checkMiddleLayer(final int startX, final int startY,
			final int startZ, final int depthMultiplier, final boolean forwardZ) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = -2; horiz <= 2; horiz++) // Horizontal (X or Z)
		{
			for (int depth = -2; depth <= 2; depth++) // Depth (Z or X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				final int blockId = this.worldObj.getBlockId(x, y, z);

				if ((depth <= 1) && (depth >= -1) && (horiz <= 1)
						&& (horiz >= -1)) {
					if (x == startX) {
						if ((blockId != Block.brick.blockID)
								&& (blockId != Blocks.boilerWall.blockID)) {
							return false;
						}
					} else {
						if (!this.worldObj.isAirBlock(x, y, z)) {
							return false;
						}
					}
				} else if (((depth == 2) || (depth == -2))
						&& ((horiz == 2) || (horiz == -2))) {
					continue;
				} else {
					if ((x == this.xCoord) && (z == this.zCoord)) {
						continue;
					} else {
						if (forwardZ) {
							if (((x == (this.xCoord + 1)) || (x == (this.xCoord - 1)))
									&& (z == this.zCoord)) {
								if ((blockId != Block.brick.blockID)
										&& (blockId != Blocks.boilerWall.blockID)
										&& (blockId != Block.glass.blockID)) {
									return false;
								}
							} else {
								if ((blockId != Block.brick.blockID)
										&& (blockId != Blocks.boilerWall.blockID)) {
									return false;
								}
							}
						} else {
							if (((z == (this.zCoord + 1)) || (z == (this.zCoord - 1)))
									&& (x == this.xCoord)) {
								if ((blockId != Block.brick.blockID)
										&& (blockId != Blocks.boilerWall.blockID)
										&& (blockId != Block.glass.blockID)) {
									return false;
								}
							} else {
								if ((blockId != Block.brick.blockID)
										&& (blockId != Blocks.boilerWall.blockID)) {
									return false;
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	private boolean checkTopLayer(final int startX, final int startY,
			final int startZ, final int depthMultiplier, final boolean forwardZ) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = -2; horiz <= 2; horiz++) // Horizontal (X or Z)
		{
			for (int depth = -2; depth <= 2; depth++) // Depth (Z or X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				final int blockId = this.worldObj.getBlockId(x, y, z);

				if ((depth <= 1) && (depth >= -1) && (horiz <= 1)
						&& (horiz >= -1)) {
					if (x == startX) {
						if ((blockId != Block.brick.blockID)
								&& (blockId != Blocks.boilerWall.blockID)) {
							return false;
						}
					} else {
						if (!this.worldObj.isAirBlock(x, y, z)) {
							return false;
						}
					}
				} else if (((depth == 2) || (depth == -2))
						&& ((horiz == 2) || (horiz == -2))) {
					continue;
				} else {
					if (forwardZ) {
						if (((x == (this.xCoord + 1)) || (x == (this.xCoord - 1)))
								&& (z == this.zCoord)) {
							if ((blockId != Block.brick.blockID)
									&& (blockId != Blocks.boilerWall.blockID)
									&& (blockId != Block.glass.blockID)) {
								return false;
							}
						} else {
							if ((blockId != Block.brick.blockID)
									&& (blockId != Blocks.boilerWall.blockID)) {
								return false;
							}
						}
					} else {
						if (((z == (this.zCoord + 1)) || ((z == (this.zCoord - 1)) || (z == this.zCoord)))
								&& (x == this.xCoord)) {
							if ((blockId != Block.brick.blockID)
									&& (blockId != Blocks.boilerWall.blockID)
									&& (blockId != Block.glass.blockID)) {
								return false;
							}
						} else {
							if ((blockId != Block.brick.blockID)
									&& (blockId != Blocks.boilerWall.blockID)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack decrStackSize(final int i, final int count) {
		ItemStack itemStack = this.getStackInSlot(i);

		if (itemStack != null) {
			if (itemStack.stackSize <= count) {
				this.setInventorySlotContents(i, null);
			} else {
				itemStack = itemStack.splitStack(count);
				this.onInventoryChanged();
			}
		}

		return itemStack;
	}

	public void destroyMultiBlock() {
		this.isBoiling = false;
		this.heatUpTime = 0;
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord,
				this.zCoord);
		meta &= ~(1 << 2);
		this.isValidMultiBlock = false;
		this.transformDummies(Block.brick.blockID);
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
				this.zCoord, meta, 2);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		final int[] slots = new int[3];
		slots[0] = 0;
		slots[1] = 1;
		slots[2] = 2;

		return slots;
	}

	public int getCookProgressScaled(final int i) {
		return (this.cookTime * i) / 200;
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("inputHeight", this.inputHeight);
		tag.setInteger("outputHeight", this.outputHeight);
		tag.setBoolean("isBoiling", this.isBoiling);
		tag.setBoolean("isValidMultiBlock", this.isValidMultiBlock);
		return new Packet132TileEntityData(this.xCoord, this.yCoord,
				this.zCoord, 1, tag);
	}

	public int getHeatupProgressScaled(final int i) {
		return (this.heatUpTime * i) / 1000;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "InventoryBoiler";
	}

	@Override
	public int getSizeInventory() {
		return this.items.length;
	}

	@Override
	public ItemStack getStackInSlot(final int i) {
		return this.items[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int i) {
		ItemStack item = null;

		item = this.getStackInSlot(i);
		this.setInventorySlotContents(i, null);

		return item;
	}

	public boolean isBoiling() {
		return this.isBoiling;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
		if (i == 0) {
			return itemstack.itemID == Item.fishRaw.itemID;
		} else if (i == 1) {
			return FluidContainerRegistry.isEmptyContainer(itemstack);
		} else {
			return false;
		}
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5,
				this.zCoord + 0.5) <= 64;
	}

	public void makeMultiBlock() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataPacket(final INetworkManager net,
			final Packet132TileEntityData packet) {
		final NBTTagCompound tag = packet.customParam1;

		this.inputHeight = tag.getInteger("inputHeight");
		this.outputHeight = tag.getInteger("outputHeight");

		this.inputTank.setFluid(new FluidStack(FluidRegistry.WATER,
				this.inputHeight));
		this.outputTank.setFluid(new FluidStack(Fluids.blubber,
				this.outputHeight));

		this.isBoiling = tag.getBoolean("isBoiling");
		this.isValidMultiBlock = tag.getBoolean("isValidMultiBlock");
		this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord,
				this.zCoord);
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);

		final NBTTagList items = compound.getTagList("Items");

		for (int i = 0; i < items.tagCount(); i++) {
			final NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
			final int slot = item.getByte("Slot");

			if ((slot >= 0) && (slot < this.getSizeInventory())) {
				this.setInventorySlotContents(slot,
						ItemStack.loadItemStackFromNBT(item));
			}
		}

		final int idWater = compound.getInteger("itemIDWater");
		final int amountWater = compound.getInteger("amountWater");

		this.inputTank.setFluid(new FluidStack(idWater, amountWater));

		final int idBlubber = compound.getInteger("itemIDBlubber");
		final int amountBlubber = compound.getInteger("amountBlubber");

		this.outputTank.setFluid(new FluidStack(idBlubber, amountBlubber));

		this.inputHeight = compound.getInteger("inputHeight");
		this.outputHeight = compound.getInteger("outputHeight");

		this.cookTime = compound.getInteger("cooktime");
		this.heatUpTime = compound.getInteger("heatUpTime");
		this.isValidMultiBlock = compound.getBoolean("isValidMultiBlock");
	}

	@Override
	public void setInventorySlotContents(final int i, final ItemStack itemstack) {
		this.items[i] = itemstack;

		if ((itemstack != null)
				&& (itemstack.stackSize > this.getInventoryStackLimit())) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	public void transformDummies(final int id) {

		final int meta = this.worldObj.getBlockMetadata(this.xCoord,
				this.yCoord, this.zCoord);
		final CoordTuple coordTuple = CoordHelper
				.getDirectionSensitiveCoordTuple(meta, this.xCoord,
						this.zCoord, 0, 2);
		this.transformLayer(coordTuple.getX(), this.yCoord - 1,
				coordTuple.getZ(), coordTuple.getDepthMultiplier(),
				coordTuple.isForwardZ(), id, -1, 1, -1, 1);
		this.transformLayer(coordTuple.getX(), this.yCoord, coordTuple.getZ(),
				coordTuple.getDepthMultiplier(), coordTuple.isForwardZ(), id,
				-2, 2, -2, 2);
		this.transformLayer(coordTuple.getX(), this.yCoord + 1,
				coordTuple.getZ(), coordTuple.getDepthMultiplier(),
				coordTuple.isForwardZ(), id, -2, 2, -2, 2);
	}

	private void transformLayer(final int startX, final int startY,
			final int startZ, final int depthMultiplier,
			final boolean forwardZ, final int id, final int horizLimLo,
			final int horizLimHi, final int depthLimLo, final int depthLimHi) {
		/*
		 * FORWARD BACKWARD North: -z +z South: +z -z East: +x -x West: -x +x
		 * 
		 * Should move BACKWARD for depth (facing = direction of block face, not
		 * direction of player looking at face)
		 */

		for (int horiz = horizLimLo; horiz <= horizLimHi; horiz++) // Horizontal
																	// (X or Z)
		{
			for (int depth = depthLimLo; depth <= depthLimHi; depth++) // Depth
																		// (Z or
																		// X)
			{
				final int x = startX
						+ (forwardZ ? horiz : (depth * depthMultiplier));
				final int y = startY;
				final int z = startZ
						+ (forwardZ ? (depth * depthMultiplier) : horiz);

				final int blockId = this.worldObj.getBlockId(x, y, z);

				if (id == Block.brick.blockID) {
					if (blockId != Blocks.boilerWall.blockID) {
						continue;
					}
					this.worldObj.setBlock(x, y, z, id);
					this.worldObj.markBlockForUpdate(x, y, z);
				} else if (id == Blocks.boilerWall.blockID) {
					if (blockId != Block.brick.blockID) {
						continue;
					}
					this.worldObj.setBlock(x, y, z, id);
					this.worldObj.markBlockForUpdate(x, y, z);
					final TileEntityBoilerWall te = (TileEntityBoilerWall) this.worldObj
							.getBlockTileEntity(x, y, z);
					te.setBoiler(this);
				}

			}
		}
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord,
					this.zCoord);
			if (!this.isValidMultiBlock) {
				if (this.checkIfMultiBlock()) {
					this.isBoiling = true;
					meta |= (1 << 2);
					this.isValidMultiBlock = true;
					this.transformDummies(Blocks.boilerWall.blockID);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord,
							this.yCoord, this.zCoord, meta, 2);
				}
			} else {
				if (!this.checkIfMultiBlock()) {
					this.isBoiling = false;
					this.heatUpTime = 0;
					meta &= ~(1 << 2);
					this.isValidMultiBlock = false;
					this.transformDummies(Block.brick.blockID);
					this.worldObj.setBlockMetadataWithNotify(this.xCoord,
							this.yCoord, this.zCoord, meta, 2);
				} else {
					if (this.canSmelt() && (this.heatUpTime == 1000)) {
						++this.cookTime;

						if (this.cookTime == 200) {
							this.cookTime = 0;
							this.decrStackSize(0, 1);
							this.inputTank
									.setFluid(new FluidStack(
											FluidRegistry.WATER,
											this.inputTank.getFluidAmount()
													- FluidContainerRegistry.BUCKET_VOLUME));
							this.inputHeight = this.inputTank.getFluidAmount();
							this.outputTank
									.fill(new FluidStack(
											Fluids.blubber,
											FluidContainerRegistry.BUCKET_VOLUME),
											true);
							this.outputHeight = this.outputTank
									.getFluidAmount();

							this.worldObj.markBlockForUpdate(this.xCoord,
									this.yCoord, this.zCoord);
						}
					} else {
						if (this.heatUpTime < 1000) {
							++this.heatUpTime;
						}
						this.cookTime = 0;
					}

					final ItemStack destStack = this.getStackInSlot(2);
					if (destStack == null) {
						final ItemStack stackInSlot = this.getStackInSlot(1);
						if ((stackInSlot != null)
								&& !(this.outputTank.getFluidAmount() < FluidContainerRegistry.BUCKET_VOLUME)) {
							final Item item = stackInSlot.getItem();
							if (item != null) {
								this.decrStackSize(1, 1);

								final ItemStack filledContainer = FluidContainerRegistry
										.fillFluidContainer(
												new FluidStack(
														Fluids.blubber,
														FluidContainerRegistry.BUCKET_VOLUME),
												new ItemStack(item));

								this.outputTank
										.setFluid(new FluidStack(
												Fluids.blubber,
												this.outputTank
														.getFluidAmount()
														- FluidContainerRegistry.BUCKET_VOLUME));

								this.outputHeight = this.outputTank
										.getFluidAmount();

								this.setInventorySlotContents(2,
										filledContainer);

								this.worldObj.markBlockForUpdate(this.xCoord,
										this.yCoord, this.zCoord);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		final NBTTagList items = new NBTTagList();

		for (int i = 0; i < this.getSizeInventory(); i++) {
			final ItemStack stack = this.getStackInSlot(i);

			if (stack != null) {
				final NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}

		compound.setTag("Items", items);

		FluidStack liquid = this.inputTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDWater", liquid.fluidID);
			compound.setInteger("amountWater", liquid.amount);
		}

		liquid = this.outputTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDBlubber", liquid.fluidID);
			compound.setInteger("amountBlubber", liquid.amount);
		}

		compound.setInteger("inputHeight", (short) this.inputHeight);
		compound.setInteger("outputHeight", this.outputHeight);

		compound.setInteger("cooktime", this.cookTime);
		compound.setInteger("heatUpTime", this.heatUpTime);
		compound.setBoolean("isValidMultiBlock", this.isValidMultiBlock);
	}
}
