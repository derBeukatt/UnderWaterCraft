package org.derbeukatt.underwatercraft.util;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

import org.derbeukatt.underwatercraft.network.PacketHandler;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

	@Override
	public String getLabel() {
		return "UWC: Client Tick";
	}

	@Override
	public void tickEnd(final EnumSet<TickType> type, final Object... tickData) {
		// TODO Auto-generated method stub
		final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		if (player != null) {
			final PlayerInputMap inputmap = PlayerInputMap
					.getInputMapFor(player.username);
			inputmap.forwardKey = Math.signum(player.movementInput.moveForward);
			// if (inputmap.forwardKey > 0) {
			// System.out.println("Forward: " + inputmap.forwardKey);
			// System.out.println("Old Forward: "
			// + inputmap.lastSentMap.forwardKey);
			// }
			inputmap.strafeKey = Math.signum(player.movementInput.moveStrafe);
			inputmap.jumpKey = player.movementInput.jump;
			inputmap.sneakKey = player.movementInput.sneak;
			inputmap.motionX = player.motionX;
			inputmap.motionY = player.motionY;
			inputmap.motionZ = player.motionZ;

			if (inputmap.hasChanged()) {
				inputmap.refresh();

				PacketHandler.sendInputMap(inputmap);
			}
		}

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public void tickStart(final EnumSet<TickType> type,
			final Object... tickData) {
		// TODO Auto-generated method stub

	}

}
