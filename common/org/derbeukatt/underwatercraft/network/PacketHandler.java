package org.derbeukatt.underwatercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.derbeukatt.underwatercraft.ModInfo;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	public static void sendBlubberAmount(final short amount, final int x,
			final int y, final int z) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 1);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);
			dataStream.writeShort(amount);
			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(
					ModInfo.MOD_CHANNELS, byteStream.toByteArray()));
		} catch (final IOException ex) {
			System.err.append("Failed to send render height");
		}
	}

	public static void sendRenderHeight(final short renderHeight, final int x,
			final int y, final int z) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte) 0);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);
			dataStream.writeShort(renderHeight);
			PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket(
					ModInfo.MOD_CHANNELS, byteStream.toByteArray()));
		} catch (final IOException ex) {
			System.err.append("Failed to send render height");
		}
	}

	@Override
	public void onPacketData(final INetworkManager manager,
			final Packet250CustomPayload packet, final Player player) {
		final ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		final EntityPlayer entityPlayer = (EntityPlayer) player;

		final byte id = reader.readByte();

		final int x = reader.readInt();
		final int y = reader.readInt();
		final int z = reader.readInt();

		final TileEntityBoiler te = (TileEntityBoiler) entityPlayer.worldObj
				.getBlockTileEntity(x, y, z);

		switch (id) {
		case 0:
			final short renderHeight = reader.readShort();

			te.renderHeight = renderHeight;

			entityPlayer.worldObj.markBlockForRenderUpdate(x, y, z);
			break;
		case 1:

			final short amount = reader.readShort();

			te.blubberAmount = amount;

			break;
		}

	}
}
