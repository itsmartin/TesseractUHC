package com.martinbrook.tesseractuhc;

import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.startpoint.UhcStartPoint;

public class UhcPlayer {
	private String name;
	private boolean launched = false;
	private UhcTeam team;

	private boolean dead = false;
	
	public UhcPlayer(String name, UhcTeam team) {
		this.name = name;
		this.team = team;
	}
	
	public UhcPlayer(Player p, UhcTeam team) {
		this(p.getName(), team);
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isLaunched() {
		return launched;
	}


	public void setLaunched(boolean launched) {
		this.launched = launched;
	}


	public UhcStartPoint getStartPoint() {
		return team.getStartPoint();
	}


	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public UhcTeam getTeam() {
		return team;
	}

	

}
