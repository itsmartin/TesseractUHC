package com.martinbrook.uhctools;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;
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
		// If player not allowed to log in, do nothing
		if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
		
		// If we are in the pre-launch period, do nothing
		if (!t.getLaunchingPlayers()) return;
		
		// If the match has not yet started, launch the player if necessary
		if (!t.isMatchStarted()) {
			t.launch(e.getPlayer());
			return;
		}

		// Match is underway. If player was not launched, don't allow them in.
		UhcPlayer up = t.getUhcPlayer(e.getPlayer());
		if (up == null || !up.isLaunched()) {
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "The match has already started");
		}
	}
	
	
		
	/**
	 * Handle death events; add bonus items, if any.
	 * 
	 * @param pDeath
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		
		// If it's a pvp kill, drop bonus items
		if (p.getKiller() != null) {
			ItemStack bonus = t.getKillerBonus();
			if (bonus != null)
				e.getDrops().add(bonus);
		}
		
		// Make death message red
		String msg = e.getDeathMessage();
		e.setDeathMessage(ChatColor.RED + msg);
		
		// Save death point
		if (msg.indexOf("fell out of the world") == -1)
			t.setLastDeathLocation(p.getLocation());
		
		// Handle the death
		UhcPlayer up = t.getUhcPlayer(p);
		if (up != null)
			t.handlePlayerDeath(up);

		if (t.getDeathban()) {
			p.setWhitelisted(false);
		}
		
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.STONE) {
			t.doMiningFatigue(e.getPlayer(), e.getBlock().getLocation().getBlockY());
		}
	}
	
}
