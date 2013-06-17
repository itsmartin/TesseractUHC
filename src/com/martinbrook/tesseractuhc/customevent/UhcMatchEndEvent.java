package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcMatchEndEvent extends UhcMatchEvent {

	public UhcMatchEndEvent(UhcMatch match, Location location) {
		super(match, location);
	}

}
