package org.derbeukatt.underwatercraft.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.derbeukatt.underwatercraft.ModInfo;
import org.derbeukatt.underwatercraft.util.PlayerInputMap;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

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
		default:
			break;
		}
	}
}
