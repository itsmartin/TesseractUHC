package com.martinbrook.tesseractuhc.notification;

public abstract class UhcNotification {

	
	public UhcNotification() {
	}
	
	public abstract String formatForPlayers();
	
	public String formatForStreamers() {
		return this.formatForPlayers();
	}
	
	public String toString() {
		return this.formatForPlayers();
	}

}
