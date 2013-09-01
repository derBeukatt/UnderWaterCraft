package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.UnderWaterCraft;
import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(UnderWaterCraft.instance,
				this);
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player,
			final World world, final int x, final int y, final int z) {
		switch (ID) {
		case 0:
			final TileEntity te = world.getBlockTileEntity(x, y, z);
			if ((te != null) && (te instanceof TileEntityBoiler)) {
				return new ContainerBoiler(player.inventory,
						(TileEntityBoiler) te);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(final int ID, final EntityPlayer player,
			final World world, final int x, final int y, final int z) {
		switch (ID) {
		case 0:
			final TileEntity te = world.getBlockTileEntity(x, y, z);
			if ((te != null) && (te instanceof TileEntityBoiler)) {
				return new GuiBoiler(player.inventory, (TileEntityBoiler) te);
			}

			break;
		}
		return null;
	}

}
