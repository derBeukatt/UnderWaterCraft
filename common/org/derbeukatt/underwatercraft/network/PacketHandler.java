package org.derbeukatt.underwatercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import org.derbeukatt.underwatercraft.ModInfo;
import org.derbeukatt.underwatercraft.common.fluids.Fluids;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;
import org.derbeukatt.underwatercraft.util.PlayerInputMap;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler {

	public static void sendAddedDye(final ItemStack dye, final int xCoord,
			final int yCoord, final int zCoord) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 1);
			dataStream.writeInt(xCoord);
			dataStream.writeInt(yCoord);
			dataStream.writeInt(zCoord);
			dataStream.writeInt(dye.getItemDamage());
			dataStream.writeInt(dye.stackSize);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
					ModInfo.MOD_CHANNELS, byteStream.toByteArray()));

		} catch (final IOException ex) {
			System.err.append("Failed to send dyes!");
		}
	}

	public static void sendHasBottleFluid(final boolean hasBottleFluid,
			final int amount, final int xCoord, final int yCoord,
			final int zCoord) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 2);
			dataStream.writeInt(xCoord);
			dataStream.writeInt(yCoord);
			dataStream.writeInt(zCoord);
			dataStream.writeBoolean(hasBottleFluid);
			dataStream.writeInt(amount);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
					ModInfo.MOD_CHANNELS, byteStream.toByteArray()));

		} catch (final IOException ex) {
			System.err.append("Failed to send has bottle fluid!");
		}
	}

	public static void sendInputMap(final PlayerInputMap inputmap) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 0);
			inputmap.writeToStream(dataStream);

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
					ModInfo.MOD_CHANNELS, byteStream.toByteArray()));

		} catch (final IOException ex) {
			System.err.append("Failed to send input map!");
		}
	}

	private void handleClient(final INetworkManager manager,
			final Packet250CustomPayload packet,
			final EntityClientPlayerMP player) {
		final ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		final String playerName = player.username;
		final byte id = reader.readByte();

		switch (id) {
		case 0:
			final PlayerInputMap inputMap = PlayerInputMap
					.getInputMapFor(playerName);
			inputMap.readFromStream(reader);
			break;
		default:
			break;
		}
	}

	private void handleServer(final INetworkManager manager,
			final Packet250CustomPayload packet, final EntityPlayerMP player) {
		final ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		final String playerName = player.username;
		final byte id = reader.readByte();

		switch (id) {
		case 0:
			final PlayerInputMap inputMap = PlayerInputMap
					.getInputMapFor(playerName);
			inputMap.readFromStream(reader);
			player.motionX = inputMap.motionX;
			player.motionY = inputMap.motionY;
			player.motionZ = inputMap.motionZ;
			PacketDispatcher.sendPacketToAllAround(player.posX, player.posY,
					player.posZ, 128, player.dimension, packet);
			break;
		case 1:
			int xCoord = reader.readInt();
			int yCoord = reader.readInt();
			int zCoord = reader.readInt();
			TileEntity blockTileEntity = player.worldObj.getBlockTileEntity(
					xCoord, yCoord, zCoord);
			if (blockTileEntity != null) {
				if (blockTileEntity instanceof TileEntityMixer) {
					final TileEntityMixer te = (TileEntityMixer) blockTileEntity;
					final int dmg = reader.readInt();
					final int stackSize = reader.readInt();
					player.inventory.setItemStack(new ItemStack(Item.dyePowder,
							stackSize, dmg));
					final ItemStack itemStack = new ItemStack(Item.dyePowder,
							1, dmg);
					if (!te.dyes.containsKey(dmg)) {
						te.dyes.put(dmg, itemStack);
						te.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
			}
			break;
		case 2:
			xCoord = reader.readInt();
			yCoord = reader.readInt();
			zCoord = reader.readInt();
			blockTileEntity = player.worldObj.getBlockTileEntity(xCoord,
					yCoord, zCoord);
			if (blockTileEntity != null) {
				if (blockTileEntity instanceof TileEntityMixer) {
					final TileEntityMixer te = (TileEntityMixer) blockTileEntity;
					final boolean hasBottleFluid = reader.readBoolean();
					final int amount = reader.readInt();
					te.renderHeight = amount;
					te.getBlubberTank().setFluid(
							new FluidStack(Fluids.blubber, amount));
					te.hasBottleFluid = hasBottleFluid;
					te.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onPacketData(final INetworkManager manager,
			final Packet250CustomPayload packet, final Player player) {

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			this.handleClient(manager, packet, (EntityClientPlayerMP) player);
		} else {
			this.handleServer(manager, packet, (EntityPlayerMP) player);
		}

	}
}
