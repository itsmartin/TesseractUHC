package com.martinbrook.tesseractuhc.event;

import com.martinbrook.tesseractuhc.UhcMatch;

public class PVPEvent extends UhcEvent {

	private Boolean value;
	
	public PVPEvent(long time, UhcMatch match, int param) {
		super(time,match);
		this.value = (param != 0);
	}
	
	
	@Override
	protected void startEventCountdown(int countdownLength) {
		match.startPVPCountdown(countdownLength, value);
	}

	@Override
	public String getDescription() {
		return "PVP will be " + (this.value ? "enabled":"disabled");
	}

	@Override
	public String toConfigString() {
		return this.time + ",PVP," + (this.value ? "1" : "0");
	}

}
