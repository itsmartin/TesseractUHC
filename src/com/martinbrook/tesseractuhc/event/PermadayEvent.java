package com.martinbrook.tesseractuhc.event;

import com.martinbrook.tesseractuhc.UhcMatch;

public class PermadayEvent extends UhcEvent {
	
	private Boolean value;

	public PermadayEvent(long time, UhcMatch match, int param) {
		super(time,match);
		this.value = (param != 0);
	}
	
	
	@Override
	protected void startEventCountdown(int countdownLength) {
		match.startPermadayCountdown(countdownLength, value);
	}

	@Override
	public String getDescription() {
		return "Permaday will be " + (this.value ? "enabled":"disabled");
	}

	@Override
	public String toConfigString() {
		return this.time + ",permaday," + (this.value ? "1" : "0");
	}

}
