package com.martinbrook.tesseractuhc.customevent;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.martinbrook.tesseractuhc.UhcMatch;

public abstract class UhcMatchEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	protected Long matchTime;
	protected Location location; 
	protected UhcMatch match;
	protected HashMap<String, String> logData = new HashMap<String, String>();

	protected UhcMatchEvent(UhcMatch match, Location location) {
		this.match = match;
		this.matchTime = match.getMatchTime();
		this.location = location;
		this.logData.put("matchTime", matchTime.toString());
		this.logData.put("world", location.getWorld().getName());
		this.logData.put("x", String.valueOf(location.getX()));
		this.logData.put("y", String.valueOf(location.getY()));
		this.logData.put("z", String.valueOf(location.getZ()));
	}

	public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

	
	public Long getMatchTime() { return matchTime; }
	public Location getLocation() { return location; }

	public HashMap<String, String> getLogData() { return logData; }
	
}

