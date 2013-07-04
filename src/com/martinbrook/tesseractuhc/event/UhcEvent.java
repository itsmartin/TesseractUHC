package com.martinbrook.tesseractuhc.event;

import com.martinbrook.tesseractuhc.UhcMatch;

public abstract class UhcEvent {
	protected long time;
	private Boolean handled;
	protected UhcMatch match;
	protected int countdownLength = 3;
	
	public UhcEvent(long time, UhcMatch match) {
		this.time = time;
		this.match = match;
		this.handled = false;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public Boolean isHandled() {
		return this.handled;
	}
	
	public int getCountdownLength() {
		return this.countdownLength;
	}
	
	public void startCountdown(int countdownLengthMinutes) {
		this.startEventCountdown(countdownLengthMinutes * 60);
		this.handled=true;
	}
	
	protected abstract void startEventCountdown(int countdownLength); 
	public abstract String getDescription();
	public abstract String toConfigString();
	
	
}
