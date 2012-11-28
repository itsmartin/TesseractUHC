package com.martinbrook.tesseractuhc;

public class UhcSpectator {

	private String name;
	private boolean interacting = false;
	private Integer cyclePoint = null;

	public UhcSpectator(String name) { this.name = name; }
	
	public void setInteracting(boolean interacting) { this.interacting=interacting; }
	public boolean isInteracting() { return this.interacting; }
	
	public String getName() { return name; }

	public int nextCyclePoint(int numberOfPlayers) {
		if (cyclePoint == null) {
			cyclePoint = 0;
		} else {
			if (++cyclePoint >= numberOfPlayers) cyclePoint = 0;
		}
		return cyclePoint;
	}
	
}
