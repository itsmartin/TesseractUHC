package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcArmorChangeEvent extends UhcMatchEvent {

	private int armorPoints;

	public UhcArmorChangeEvent(UhcMatch match, Location location, int armorPoints) {
		super(match, location);
		this.armorPoints = armorPoints;
	}

	public int getArmorPoints() {
		return armorPoints;
	}

	
}
