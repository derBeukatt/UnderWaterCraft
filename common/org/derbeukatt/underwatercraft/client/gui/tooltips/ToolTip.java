package org.derbeukatt.underwatercraft.client.gui.tooltips;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ForwardingList;

/**
 * 
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class ToolTip extends ForwardingList<ToolTipLine> {

	private final long delay;
	private final List<ToolTipLine> delegate = new ArrayList<ToolTipLine>();
	private long mouseOverStart;

	public ToolTip() {
		this.delay = 0;
	}

	public ToolTip(final int delay) {
		this.delay = delay;
	}

	@Override
	protected final List<ToolTipLine> delegate() {
		return this.delegate;
	}

	public boolean isReady() {
		if (this.delay == 0) {
			return true;
		}
		if (this.mouseOverStart == 0) {
			return false;
		}
		return (System.currentTimeMillis() - this.mouseOverStart) >= this.delay;
	}

	public void onTick(final boolean mouseOver) {
		if (this.delay == 0) {
			return;
		}
		if (mouseOver) {
			if (this.mouseOverStart == 0) {
				this.mouseOverStart = System.currentTimeMillis();
			}
		} else {
			this.mouseOverStart = 0;
		}
	}

	public void refresh() {
	}
}
