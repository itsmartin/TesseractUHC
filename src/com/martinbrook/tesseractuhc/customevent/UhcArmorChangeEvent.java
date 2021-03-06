package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcArmorChangeEvent extends UhcMatchEvent {

	private int armorPoints;
	private Player player;

	public UhcArmorChangeEvent(UhcMatch match, Location location, Player player, int armorPoints) {
		super(match, location);
		this.armorPoints = armorPoints;
		this.logData.put("armorPoints", String.valueOf(armorPoints));
		this.player = player;
		this.logData.put("player", player.getName());
	}

	public int getArmorPoints() {
		return armorPoints;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
