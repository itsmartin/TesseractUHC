package com.martinbrook.tesseractuhc.event;

import com.martinbrook.tesseractuhc.UhcMatch;

public class BorderEvent extends UhcEvent {

	private int value;
	
	public BorderEvent(long time, UhcMatch match, int param) {
		super(time,match);
		this.value=param;
	}
	
	
	@Override
	protected void startEventCountdown(int countdownLength) {
		match.startBorderCountdown(countdownLength, value);

	}

	@Override
	public String getDescription() {
		return "Border will be set to " + this.value;
	}

	@Override
	public String toConfigString() {
		return this.time + ",border," + this.value;
	}

}
