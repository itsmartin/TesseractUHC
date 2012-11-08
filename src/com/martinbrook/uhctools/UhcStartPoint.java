package com.martinbrook.uhctools;

import org.bukkit.Location;
import org.bukkit.World;

public class UhcStartPoint {

	private Location location;
	private UhcPlayer uhcPlayer = null;
	private int number;

	public UhcStartPoint(int number, Location location) {
		this.location=location;
		this.number=number;
	}
	
	public UhcStartPoint(int number, World w, double x, double y, double z) {
		this(number, new Location(w,x,y,z));
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UhcPlayer getUhcPlayer() {
		return uhcPlayer;
	}

	public void setUhcPlayer(UhcPlayer uhcPlayer) {
		this.uhcPlayer = uhcPlayer;
	}

	public double getX() { return location.getX(); }
	public double getY() { return location.getY(); }
	public double getZ() { return location.getZ(); }

	public int getNumber() {
		return number;
	}


}
