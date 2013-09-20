package org.derbeukatt.underwatercraft.util;

public class CoordHelper {

	public static class CoordTuple {
		private int depthMultiplier = 1;
		private boolean forwardZ = false;

		private int x;
		private int z;

		public CoordTuple(final int dir, final int xCoord, final int zCoord) {
			if (dir == META_DIR_NORTH) {
				this.x = xCoord + 1;
				this.z = zCoord + 2;
				this.forwardZ = true;
			} else if (dir == META_DIR_WEST) {
				this.x = xCoord + 2;
				this.z = zCoord - 1;

			} else if (dir == META_DIR_EAST) {
				this.x = xCoord - 2;
				this.z = zCoord + 1;
				this.depthMultiplier = -1;
			} else if (dir == META_DIR_SOUTH) {
				this.x = xCoord - 1;
				this.z = zCoord - 2;
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

	public static CoordTuple getDirectionSensitiveCoordTuple(final int meta,
			final int xCoord, final int zCoord) {
		final int dir = meta & META_MASK_DIR;
		return new CoordTuple(dir, xCoord, zCoord);
	}

}
