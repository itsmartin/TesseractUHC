package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcPlayerLocationUpdateEvent extends UhcMatchEvent {

	public UhcPlayerLocationUpdateEvent(UhcMatch match, Location location) {
		super(match, location);
	}

}
