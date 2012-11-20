package com.martinbrook.tesseractuhc.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.martinbrook.tesseractuhc.MatchPhase;
import com.martinbrook.tesseractuhc.UhcMatch;
import com.martinbrook.tesseractuhc.UhcPlayer;

public class LoginListener implements Listener {
	private UhcMatch m;
	public LoginListener(UhcMatch m) { this.m = m; }


	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		m.setVanish(e.getPlayer());
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		m.setLastLogoutLocation(e.getPlayer().getLocation());
	}
	

	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		// If player not allowed to log in, do nothing
		if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
		
		// If we are in the pre-launch period, do nothing
		if (m.getMatchPhase() == MatchPhase.PRE_MATCH) return;
		
		// If match is over, put player in creative, do nothing else
		if (m.getMatchPhase() == MatchPhase.POST_MATCH) {
			e.getPlayer().setGameMode(GameMode.CREATIVE);
			return;
		}

		// If player is op, do nothing
		if (e.getPlayer().isOp()) return;
		
		// Get a uhcplayer if possible
		UhcPlayer up = m.getUhcPlayer(e.getPlayer());
		
		// If the match has not yet started, try to launch the player if necessary
		if (m.getMatchPhase() == MatchPhase.LAUNCHING) {
			if (up != null) {
				// Player is in the match, make sure they are launched
				m.launch(up);
			} else {
				// Player isn't in the match, so make sure they log in at spawn
				e.getPlayer().teleport(m.getStartingWorld().getSpawnLocation());
			}
			return;
		}

		
		// Match is in progress.

		// If player was not launched, don't allow them in.
		if (up == null || !up.isLaunched()) {
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "The match has already started");
			return;
		}
		
		// If player has died, don't allow them in, if deathban is in effect.
		if (m.getDeathban() && up.isDead()) {
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Dead players cannot rejoin!");
			return;
		}
	}
	
	
	
	
	
}
