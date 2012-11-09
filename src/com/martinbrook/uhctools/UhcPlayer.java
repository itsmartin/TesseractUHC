package com.martinbrook.uhctools;

import org.bukkit.entity.Player;

public class UhcPlayer {
	private String name;
	private boolean launched = false;
	private UhcStartPoint startPoint;

	private int mineCount = 0;
	private boolean dead = false;
	
	public UhcPlayer(String n, UhcStartPoint sp) {
		this.name = n;
		this.startPoint = sp;
	}
	
	public UhcPlayer(Player p, UhcStartPoint sp) {
		this(p.getName(), sp);
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
		return startPoint;
	}

	public int getMineCount() {
		return mineCount;
	}
	
	public void resetMineCount() {
		mineCount = 0;
	}

	public void incMineCount() {
		mineCount ++;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	

}
