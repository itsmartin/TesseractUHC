package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcDeathEvent extends UhcMatchEvent {

	private String deathMessage;
	private Player player;
	private Player killer;
	
	public UhcDeathEvent(UhcMatch match, Location location, String deathMessage, Player player, Player killer) {
		super(match, location);
		this.deathMessage = deathMessage;
		this.player = player;
		this.killer = killer;
	}

	public String getDeathMessage() {
		return deathMessage;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Player getKiller() {
		return killer;
	}

	
}
