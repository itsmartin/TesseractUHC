package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.World;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcDimensionChangeEvent extends UhcMatchEvent {

	private World.Environment dimensionType;
	
	public UhcDimensionChangeEvent(UhcMatch match, Location location, World.Environment dimensionType) {
		super(match, location);
		this.dimensionType = dimensionType;
	}

	public World.Environment getDimensionType() { return dimensionType; }
}
