package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcHealthChangeEvent extends UhcMatchEvent {

	private int health;
	
	public UhcHealthChangeEvent(UhcMatch match, Location location, int health) {
		super(match, location);
		this.health = health;
	}


	public int getHealth() {
		return health;
	}

	
}
