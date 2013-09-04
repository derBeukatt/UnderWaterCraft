package org.derbeukatt.underwatercraft.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.network.PacketHandler;

public class TileEntityBoiler extends TileEntity implements IFluidHandler,
		ISidedInventory {

	private static final int MAX_CAPACITY = 16 * FluidContainerRegistry.BUCKET_VOLUME;
	public short blubberAmount;
	private final FluidTank blubberTank;

	public int cookTime;
	private final ItemStack[] items;
	private int oldRenderHeight;
	public int renderHeight;
	private final FluidTank waterTank;

	public TileEntityBoiler() {
		this.items = new ItemStack[3];
		this.blubberTank = new FluidTank(MAX_CAPACITY);
		this.waterTank = new FluidTank(MAX_CAPACITY);
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack,
			final int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack,
			final int j) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean canSmelt() {
		if (this.getStackInSlot(0) == null) {
			return false;
		} else {
			/* TODO: check if there is space in second tank */
			if ((this.blubberTank.getFluidAmount() + FluidContainerRegistry.BUCKET_VOLUME) <= MAX_CAPACITY) {
				return true;
			}

			return false;
		}
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

	@Override
	public FluidStack drain(final ForgeDirection from,
			final FluidStack resource, final boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain,
			final boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill(final ForgeDirection from, final FluidStack resource,
			final boolean doFill) {
		final int amount = this.waterTank.fill(resource, doFill);
		this.renderHeight = this.waterTank.getFluidAmount();

		return amount;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FluidTank getBlubberTank() {
		return this.blubberTank;
	}

	public int getCookProgressScaled(final int i) {
		return (this.cookTime * i) / 200;
	}

	public FluidStack getInputFluid() {
		return this.waterTank.getFluid();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "InventoryBoiler";
	}

	public FluidStack getOutputFluid() {
		return this.blubberTank.getFluid();
	}

	public int getScaledBlubberAmount(final int i) {
		return this.blubberAmount != 0 ? (int) (((float) this.blubberAmount / (float) (MAX_CAPACITY)) * i)
				: 0;
	}

	public int getScaledWaterAmount(final int i) {
		return this.renderHeight != 0 ? (int) (((float) this.renderHeight / (float) (MAX_CAPACITY)) * i)
				: 0;
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

		if (i < 2) {
			item = this.getStackInSlot(i);
			this.setInventorySlotContents(i, null);
		}

		return item;
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}

	public FluidTank getWaterTank() {
		return this.waterTank;
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

	@Override
	public void onInventoryChanged() {
		final ItemStack destStack = this.getStackInSlot(2);
		if (destStack == null) {
			final ItemStack stackInSlot = this.getStackInSlot(1);
			if (stackInSlot != null) {
				final Item item = stackInSlot.getItem();
				if (item != null) {
					this.decrStackSize(1, 1);

					final ItemStack filledContainer = FluidContainerRegistry
							.fillFluidContainer(new FluidStack(
									FluidRegistry.WATER,
									FluidContainerRegistry.BUCKET_VOLUME),
									new ItemStack(item));

					this.setInventorySlotContents(2, filledContainer);
				}
			}
		}
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

		// this.waterTank = this.waterTank.readFromNBT(compound);
		// this.blubberTank = this.blubberTank.readFromNBT(compound);

		final int idWater = compound.getInteger("itemIDWater");
		final int amountWater = compound.getInteger("amountWater");

		System.out.println("Read water fluid id: " + idWater);
		System.out.println("Read water fluid amount: " + amountWater);

		this.waterTank.setFluid(new FluidStack(idWater, amountWater));

		final int idBlubber = compound.getInteger("itemIDBlubber");
		final int amountBlubber = compound.getInteger("amountBlubber");

		System.out.println("Read blubber fluid id: " + idBlubber);
		System.out.println("Read blubber fluid amount: " + amountBlubber);

		this.blubberTank.setFluid(new FluidStack(idBlubber, amountBlubber));

		this.renderHeight = compound.getShort("renderHeight");
		this.blubberAmount = compound.getShort("blubberAmount");

		System.out.println("Read gui blubber amount: " + this.blubberAmount);
		System.out.println("Read gui water amount: " + this.renderHeight);
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

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			final int meta = this.worldObj.getBlockMetadata(this.xCoord,
					this.yCoord, this.zCoord);

			final int blockId = this.worldObj.getBlockId(this.xCoord,
					this.yCoord - 1, this.zCoord);

			if ((blockId == Block.fire.blockID)
					|| (blockId == Block.lavaMoving.blockID)
					|| (blockId == Block.lavaStill.blockID)) {

				this.worldObj.setBlockMetadataWithNotify(this.xCoord,
						this.yCoord, this.zCoord, 2, 3);
			} else {
				if (meta > 1) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord,
							this.yCoord, this.zCoord, 1, 3);
				}
			}

			boolean inventoryChanged = false;

			if ((this.worldObj.getBlockMetadata(this.xCoord, this.yCoord,
					this.zCoord) == 2) && this.canSmelt()) {
				++this.cookTime;

				if (this.cookTime == 200) {
					this.cookTime = 0;
					this.decrStackSize(0, 1);
					this.blubberTank.fill(new FluidStack(Fluids.blubber,
							FluidContainerRegistry.BUCKET_VOLUME), true);
					this.blubberAmount = (short) this.blubberTank
							.getFluidAmount();
					inventoryChanged = true;
				}
			} else {
				this.cookTime = 0;
			}

			if (this.oldRenderHeight != this.renderHeight) {
				PacketHandler.sendRenderHeight((short) this.renderHeight,
						this.xCoord, this.yCoord, this.zCoord);
			}

			if (inventoryChanged) {
				// this.onInventoryChanged();
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

		FluidStack liquid = this.waterTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDWater", liquid.fluidID);
			compound.setInteger("amountWater", liquid.amount);

			System.out.println("Save water fluid id: " + liquid.fluidID);
			System.out.println("Save water fluid amount: " + liquid.amount);
		}

		liquid = this.blubberTank.getFluid();
		if (liquid != null) {
			compound.setInteger("itemIDBlubber", liquid.fluidID);
			compound.setInteger("amountBlubber", liquid.amount);

			System.out.println("Save blubber fluid id: " + liquid.fluidID);
			System.out.println("Save blubber fluid amount: " + liquid.amount);
		}

		compound.setShort("renderHeight", (short) this.renderHeight);
		compound.setShort("blubberAmount", this.blubberAmount);

		System.out.println("Save gui blubber amount: " + this.blubberAmount);
		System.out.println("Save gui water amount: " + this.renderHeight);
	}
}
