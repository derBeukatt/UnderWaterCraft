package org.derbeukatt.underwatercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

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

	@Override
	public void onPacketData(final INetworkManager manager,
			final Packet250CustomPayload packet, final Player player) {
		final ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		final EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		final String playerName = entityPlayerMP.username;
		final byte id = reader.readByte();

		switch (id) {
		case 0:
			final PlayerInputMap inputMap = PlayerInputMap
					.getInputMapFor(playerName);
			inputMap.readFromStream(reader);
			entityPlayerMP.motionX = inputMap.motionX;
			entityPlayerMP.motionY = inputMap.motionY;
			entityPlayerMP.motionZ = inputMap.motionZ;

			// entityPlayerMP.velocityChanged = true;

			// System.out.println("read input map!!!");
			break;
		case 1:
			int xCoord = reader.readInt();
			int yCoord = reader.readInt();
			int zCoord = reader.readInt();
			TileEntity blockTileEntity = entityPlayerMP.worldObj
					.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (blockTileEntity != null) {
				if (blockTileEntity instanceof TileEntityMixer) {
					final TileEntityMixer te = (TileEntityMixer) blockTileEntity;
					final int dmg = reader.readInt();
					final ItemStack itemStack = new ItemStack(Item.dyePowder,
							1, dmg);
					if (!te.dyes.contains(itemStack)) {
						te.dyes.add(itemStack);
						te.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
			}
			break;
		case 2:
			xCoord = reader.readInt();
			yCoord = reader.readInt();
			zCoord = reader.readInt();
			blockTileEntity = entityPlayerMP.worldObj.getBlockTileEntity(
					xCoord, yCoord, zCoord);
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
}
