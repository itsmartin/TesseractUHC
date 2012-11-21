package com.martinbrook.tesseractuhc;

import java.util.HashSet;

import com.martinbrook.tesseractuhc.startpoint.UhcStartPoint;

public class UhcTeam {
	private String identifier;
	private String name;
	private UhcStartPoint startPoint;
	private HashSet<UhcPlayer> players = new HashSet<UhcPlayer>();
	
	
	/**
	 * Create a new team
	 * 
	 * @param identifier The team's short string identifier
	 * @param name Full name for the team
	 * @param startPoint Start point of this team
	 */
	public UhcTeam(String identifier, String name, UhcStartPoint startPoint) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.startPoint = startPoint;
	}
	
	/**
	 * @return The team's short string identifier
	 */
	public String getIdentifier() { return identifier; }

	/**
	 * @return Full name for the team
	 */
	public String getName() { return name; }

	/**
	 * @return The team's start point
	 */
	public UhcStartPoint getStartPoint() { return startPoint; }

	/**
	 * Add a new player to this team
	 * 
	 * @param p The player to be added
	 */
	public void addPlayer(UhcPlayer p) {
		players.add(p);
	}
	
	/**
	 * Remove a player from this team
	 * 
	 * @param p The player to be removed
	 */
	public void removePlayer(UhcPlayer p) {
		players.remove(p);
	}
	
	/**
	 * Get the size of the team
	 * 
	 * @return The full size of this team (including dead players)
	 */
	public int playerCount() {
		return players.size();
	}

	/**
	 * Get the number of players still alive
	 * 
	 * @return Number of players alive
	 */
	public int aliveCount() {
		int alive = 0;
		
		for(UhcPlayer p : players)
			if (!p.isDead()) alive ++;
		
		return alive;
	}
	
	public HashSet<UhcPlayer> getPlayers() {
		return players;
	}

}
