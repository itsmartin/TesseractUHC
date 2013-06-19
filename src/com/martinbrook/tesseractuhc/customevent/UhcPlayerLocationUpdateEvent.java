package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcPlayerLocationUpdateEvent extends UhcMatchEvent {

	private Player player;

	public UhcPlayerLocationUpdateEvent(UhcMatch match, Location location, Player player) {
		super(match, location);
		this.player = player;
		this.logData.put("player", player.getName());
	}

	public Player getPlayer() {
		return player;
	}

}
