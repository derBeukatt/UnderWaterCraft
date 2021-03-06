package org.derbeukatt.underwatercraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.derbeukatt.underwatercraft.UnderWaterCraft;
import org.derbeukatt.underwatercraft.common.containers.ContainerBoiler;
import org.derbeukatt.underwatercraft.common.containers.ContainerMixer;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityBoiler;
import org.derbeukatt.underwatercraft.common.tileentity.TileEntityMixer;

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
		final TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID) {
		case 0:
			if ((te != null) && (te instanceof TileEntityBoiler)) {
				return new GuiBoiler(player.inventory, (TileEntityBoiler) te);
			}
			break;
		case 1:
			if ((te != null) && (te instanceof TileEntityMixer)) {
				return new GuiMixer(player.inventory, (TileEntityMixer) te);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(final int ID, final EntityPlayer player,
			final World world, final int x, final int y, final int z) {
		final TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID) {
		case 0:
			if ((te != null) && (te instanceof TileEntityBoiler)) {
				return new ContainerBoiler(player.inventory,
						(TileEntityBoiler) te);
			}
			break;
		case 1:
			if ((te != null) && (te instanceof TileEntityMixer)) {
				return new ContainerMixer(player.inventory,
						(TileEntityMixer) te);
			}
			break;
		}
		return null;
	}
}
