package com.martinbrook.uhctools;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.ChatColor;

public class UhcToolsListener implements Listener {
	private UhcTools t;

	public UhcToolsListener(UhcTools t) {
		this.t = t;
	}
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		String msg = e.getDeathMessage();
		e.setDeathMessage(ChatColor.RED + msg);
		if (msg.indexOf("fell out of the world") == -1)
			t.setLastDeathLocation(e.getEntity().getLocation());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		t.setLastLogoutLocation(e.getPlayer().getLocation());
	}
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
		if (t.isChatMuted() && !e.getPlayer().isOp())
			e.setCancelled(true);
		
		// ops get gold text automatically
		if (e.getPlayer().isOp()) {
			e.setMessage(ChatColor.GOLD + e.getMessage());
		}
		
	}
	
	@EventHandler
	public void onPlayerComandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if (t.isChatMuted() && !e.getPlayer().isOp() && e.getMessage().toLowerCase().startsWith("/me"))
			e.setCancelled(true);
	}
	
}
