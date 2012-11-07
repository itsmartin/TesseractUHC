package com.martinbrook.uhctools;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
		
		if (t.getDeathban()) {
			e.getEntity().setWhitelisted(false);
		}
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
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		UhcPlayer up = t.getUhcPlayer(p);
		if (!up.isLaunched())
			t.launch(p);
		
	}
	
	
		
	/**
	 * Handle death events; add bonus items, if any.
	 * 
	 * @param pDeath
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent pDeath){
		Player p = pDeath.getEntity();
		if (p.getKiller() != null) {
			ItemStack bonus = t.getKillerBonus();
			if (bonus != null)
				pDeath.getDrops().add(bonus);
		}
		
			
	}

	
}
