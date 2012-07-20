package com.martinbrook.uhctools;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UhcToolsListener implements Listener {
	private UhcTools t;

	public UhcToolsListener(UhcTools t) {
		this.t = t;
	}
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getDeathMessage().indexOf("fell out of the world") == -1)
			t.setLastDeathLocation(e.getEntity().getLocation());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		t.setLastLogoutLocation(e.getPlayer().getLocation());
	}
}
