package org.derbeukatt.underwatercraft.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 04.08.13
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class PacketHandler implements IPacketHandler{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
