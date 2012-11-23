package com.martinbrook.tesseractuhc;

import org.bukkit.Location;
import org.bukkit.World;

public class UhcPOI implements PlayerTarget {

	private Location location;
	private String name;
	public Location getLocation() {
		return location;
	}
	public String getName() {
		return name;
	}
	public UhcPOI(Location location, String name) {
		super();
		this.location = location;
		this.name = name;
	}
	
	public double getX() { return location.getX(); }
	public double getY() { return location.getY(); }
	public double getZ() { return location.getZ(); }
	public World getWorld() { return location.getWorld(); }
	public String toString() {
		return location.getWorld().getName() + " " + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
	}
}
