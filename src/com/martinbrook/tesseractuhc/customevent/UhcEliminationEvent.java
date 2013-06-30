package com.martinbrook.tesseractuhc.customevent;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcEliminationEvent extends UhcMatchEvent {

	private String teamIdentifier;
	private String teamName;
	private int teamsRemaining;
	
	public UhcEliminationEvent(UhcMatch match, Location location, String teamIdentifier, String teamName, int teamsRemaining) {
		super(match, location);
		this.teamIdentifier = teamIdentifier;
		this.logData.put("teamIdentifier", teamIdentifier);
		this.teamName = teamName;
		this.logData.put("teamName", teamName);
		this.teamsRemaining = teamsRemaining;
		this.logData.put("teamsRemaining", Integer.toString(teamsRemaining));
	}

	public String getTeamIdentifier() {
		return teamIdentifier;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getTeamsRemaining() {
		return teamsRemaining;
	}

}
