package org.derbeukatt.underwatercraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * Helper for coord stuff. Facing are always related to block not to player.
 * 
 * So:
 * 
 * Player - Block SOUTH - NORTH NORTH - SOUTH WEST - EAST EAST - WEST
 * 
 * For coord handling this means, that it is relative to block facing, but coord
 * offsets are given relative to player facing.
 * 
 * Example:
 * 
 * dest air This would be +2 vert and -1 horiz air air air block
 * 
 * @author derBeukatt
 * 
 */
public class CoordHelper {

	public static class CoordTuple {
		private int depthMultiplier = 1;
		private boolean forwardZ = false;

		private int x;
		private int z;

		public CoordTuple(final int dir, final int xCoord, final int zCoord,
				final int horiz, final int vert) {
			if (dir == META_DIR_NORTH) {
				this.x = xCoord - horiz;
				this.z = zCoord + vert;
				this.forwardZ = true;
			} else if (dir == META_DIR_WEST) {
				this.x = xCoord + vert;
				this.z = zCoord + horiz;
			} else if (dir == META_DIR_EAST) {
				this.x = xCoord - vert;
				this.z = zCoord - horiz;
				this.depthMultiplier = -1;
			} else if (dir == META_DIR_SOUTH) {
				this.x = xCoord + horiz;
				this.z = zCoord - vert;
				this.forwardZ = true;
				this.depthMultiplier = -1;
			}
		}

		public int getDepthMultiplier() {
			return this.depthMultiplier;
		}

		public int getX() {
			return this.x;
		}

		public int getZ() {
			return this.z;
		}

		public boolean isForwardZ() {
			return this.forwardZ;
		}

	}

	public static final int META_DIR_EAST = 0x00000003;
	public static final int META_DIR_NORTH = 0x00000001;
	public static final int META_DIR_SOUTH = 0x00000002;
	public static final int META_DIR_WEST = 0x00000000;

	public static final int META_ISACTIVE = 0x00000004;
	public static final int META_MASK_DIR = 0x00000003;

	public static int getBlockFacing(final Entity entity) {

		int facing = META_DIR_WEST;
		final int dir = MathHelper
				.floor_double(((entity.rotationYaw * 4f) / 360f) + 0.5)
				& META_MASK_DIR;
		if (dir == 0) {
			facing = META_DIR_NORTH;
		}
		if (dir == 1) {
			facing = META_DIR_EAST;
		}
		if (dir == 2) {
			facing = META_DIR_SOUTH;
		}
		if (dir == 3) {
			facing = META_DIR_WEST;
		}

		return facing;
	}

	public static CoordTuple getDirectionSensitiveCoordTuple(final int meta,
			final int xCoord, final int zCoord, final int horiz, final int vert) {
		final int dir = meta & META_MASK_DIR;
		return new CoordTuple(dir, xCoord, zCoord, horiz, vert);
	}

	public static int getSideForBlockFace(final int face) {
		switch (face) {
		case CoordHelper.META_DIR_WEST:
			return 4;

		case CoordHelper.META_DIR_EAST:
			return 5;

		case CoordHelper.META_DIR_NORTH:
			return 2;

		case CoordHelper.META_DIR_SOUTH:
			return 3;

		default:
			return 4;
		}
	}

}
