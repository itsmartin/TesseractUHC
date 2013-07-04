package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcVictoryEvent extends UhcMatchEvent {

	private String teamIdentifier;
	private String teamName;
	
	public UhcVictoryEvent(UhcMatch match, Location location, String teamIdentifier, String teamName) {
		super(match, location);
		this.teamIdentifier = teamIdentifier;
		this.logData.put("teamIdentifier", teamIdentifier);
		this.teamName = teamName;
		this.logData.put("teamName", teamName);
	}

	public String getTeamIdentifier() {
		return teamIdentifier;
	}

	public String getTeamName() {
		return teamName;
	}


}
