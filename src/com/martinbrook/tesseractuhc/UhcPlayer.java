package com.martinbrook.tesseractuhc;

import org.bukkit.OfflinePlayer;

/**
 * Represents a player who is, or has ever been, on the server.
 *
 */
public class UhcPlayer {
	private OfflinePlayer player;
	private UhcParticipant participant;
	private UhcSpectator spectator;
	

	public UhcPlayer(OfflinePlayer player) {
		this.player = player;
	}

	public String getName() { return player.getName(); }
	public boolean isOnline() { return player.isOnline(); }
	private boolean isOp() { return player.isOp(); }
	
	public void setParticipant(UhcParticipant participant) { this.participant = participant; }
	public void setSpectator(UhcSpectator spectator) { this.spectator = spectator; }
	
	public boolean isActiveParticipant() {
		if (participant == null)
			return false;
		else
			return (participant.isLaunched() && !participant.isDead());
	}
	
	public boolean isSpectator() {
		return (spectator != null);
	}
	
	public UhcParticipant getParticipant() {
		return participant;
	}
	
	public UhcSpectator getSpectator() {
		return spectator;
	}
	
	public boolean isAdmin() {
		return isOp();
	}
}
