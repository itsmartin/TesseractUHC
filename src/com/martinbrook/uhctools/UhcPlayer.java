package com.martinbrook.uhctools;

import org.bukkit.entity.Player;

public class UhcPlayer {
	private String name;
	private boolean launched = false;
	private UhcStartPoint startPoint;
	private int startPointIndex;
	private int mineCount = 0;
	private boolean dead = false;
	
	public UhcPlayer(Player p) {
		this.name = p.getName();
	}

	public UhcPlayer(String n) {
		this.name = n;
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


	public void setStartPoint(UhcStartPoint startPoint) {
		this.startPoint = startPoint;
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

	public int getStartPointIndex() {
		return startPointIndex;
	}

	public void setStartPointIndex(int startPointIndex) {
		this.startPointIndex = startPointIndex;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}



	

}
