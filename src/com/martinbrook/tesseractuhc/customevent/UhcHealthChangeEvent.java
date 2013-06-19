package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcHealthChangeEvent extends UhcMatchEvent {

	private int health;
	private Player player;
	
	public UhcHealthChangeEvent(UhcMatch match, Location location, Player player, int health) {
		super(match, location);
		this.health = health;
		this.logData.put("health", String.valueOf(health));
		this.player = player;
		this.logData.put("player", player.getName());
	}


	public int getHealth() {
		return health;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
