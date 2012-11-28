package com.martinbrook.tesseractuhc;

public class UhcSpectator {

	private String name;
	private boolean interacting = false; 

	public UhcSpectator(String name) { this.name = name; }
	
	public void setInteracting(boolean interacting) { this.interacting=interacting; }
	public boolean isInteracting() { return this.interacting; }
	
	public String getName() { return name; }

	
	
}
