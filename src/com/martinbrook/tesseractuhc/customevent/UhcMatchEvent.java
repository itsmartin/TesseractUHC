package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.martinbrook.tesseractuhc.UhcMatch;

public abstract class UhcMatchEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	protected Long matchTime;
	protected Location location; 
	protected UhcMatch match;

	protected UhcMatchEvent(UhcMatch match, Location location) {
		this.match = match;
		this.matchTime = match.getMatchTime();
		this.location = location;
	}

	public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

	
	public Long getMatchTime() { return matchTime; }
	public Location getLocation() { return location; }

	
}

