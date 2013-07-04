package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcMatchStartEvent extends UhcMatchEvent {

	public UhcMatchStartEvent(UhcMatch match, Location location) {
		super(match, location);
	}

}
