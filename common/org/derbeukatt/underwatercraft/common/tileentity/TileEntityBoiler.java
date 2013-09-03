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

import org.derbeukatt.underwatercraft.network.PacketHandler;

public class TileEntityBoiler extends TileEntity implements IFluidHandler,
		ISidedInventory {

	private static final int MAX_CAPACITY = 16 * FluidContainerRegistry.BUCKET_VOLUME;
	private final ItemStack[] items;
	public int renderHeight;
	// private FluidTank blubberTank;
	private FluidTank waterTank;

	public TileEntityBoiler() {
		this.items = new ItemStack[3];
		// this.blubberTank = new FluidTank(fluid, amount, capacity)
		this.waterTank = new FluidTank(FluidRegistry.WATER, 0, MAX_CAPACITY);
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

		PacketHandler.sendRenderHeight((short) this.renderHeight, this.xCoord,
				this.yCoord, this.zCoord);

		return amount;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FluidStack getFluid() {
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

		this.waterTank = this.waterTank.readFromNBT(compound);
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

				if (meta == 1) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord,
							this.yCoord, this.zCoord, 2, 3);
				}
			} else {
				if (meta > 1) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord,
							this.yCoord, this.zCoord, 1, 3);
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

		this.waterTank.writeToNBT(compound);
	}
}
