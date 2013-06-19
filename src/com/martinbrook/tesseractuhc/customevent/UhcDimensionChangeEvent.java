package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcDimensionChangeEvent extends UhcMatchEvent {

	private Player player;
	private World.Environment dimensionType;
	
	public UhcDimensionChangeEvent(UhcMatch match, Location location, Player player, World.Environment dimensionType) {
		super(match, location);
		this.player = player;
		this.logData.put("player", player.getName());
		this.dimensionType = dimensionType;
		this.logData.put("dimensionType", dimensionType.name());
	}

	public Player getPlayer() { return player; }
	public World.Environment getDimensionType() { return dimensionType; }
}
