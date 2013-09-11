package org.derbeukatt.underwatercraft.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.ByteArrayDataInput;

public class PlayerInputMap {
	protected static Map<String, PlayerInputMap> playerInputs = new HashMap<String, PlayerInputMap>();

	public static PlayerInputMap getInputMapFor(final String playerName) {
		PlayerInputMap map = playerInputs.get(playerName);
		if (map == null) {
			map = new PlayerInputMap(playerName);
		}
		return map;
	}

	public boolean downKey;

	public float forwardKey;
	public boolean jumpKey;
	public PlayerInputMap lastSentMap;
	public double motionX;
	public double motionY;
	public double motionZ;
	public boolean sneakKey;
	public float strafeKey;

	public PlayerInputMap(final PlayerInputMap master) {
		this.setTo(master);
	}

	public PlayerInputMap(final String playerName) {
		playerInputs.put(playerName, this);
		this.lastSentMap = new PlayerInputMap(this);
	}

	@Override
	public boolean equals(final Object obj) {
		try {
			final PlayerInputMap other = (PlayerInputMap) obj;
			return (other.forwardKey == this.forwardKey)
					&& (other.strafeKey == this.strafeKey)
					&& (other.jumpKey == this.jumpKey)
					&& (other.sneakKey == this.sneakKey)
					&& (other.downKey == this.downKey)
					&& (other.motionX == this.motionX)
					&& (other.motionY == this.motionY)
					&& (other.motionZ == this.motionZ);
		} catch (final ClassCastException e) {
		}
		return false;
	}

	public boolean hasChanged() {
		return !this.equals(this.lastSentMap);
	}

	public boolean readFromStream(final ByteArrayDataInput reader) {
		this.forwardKey = reader.readFloat();
		this.strafeKey = reader.readFloat();
		this.jumpKey = reader.readBoolean();
		this.sneakKey = reader.readBoolean();
		this.downKey = reader.readBoolean();
		this.motionX = reader.readDouble();
		this.motionY = reader.readDouble();
		this.motionZ = reader.readDouble();
		return true;
	}

	public void refresh() {
		this.lastSentMap.setTo(this);
	}

	public void setTo(final PlayerInputMap master) {
		this.forwardKey = master.forwardKey;
		this.strafeKey = master.strafeKey;
		this.jumpKey = master.jumpKey;
		this.sneakKey = master.sneakKey;
		this.downKey = master.downKey;
		this.motionX = master.motionX;
		this.motionY = master.motionY;
		this.motionZ = master.motionZ;
	}

	public boolean writeToStream(final DataOutputStream stream) {
		try {
			stream.writeFloat(this.forwardKey);
			stream.writeFloat(this.strafeKey);
			stream.writeBoolean(this.jumpKey);
			stream.writeBoolean(this.sneakKey);
			stream.writeBoolean(this.downKey);
			stream.writeDouble(this.motionX);
			stream.writeDouble(this.motionY);
			stream.writeDouble(this.motionZ);
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}