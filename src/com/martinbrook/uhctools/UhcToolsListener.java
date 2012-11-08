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
		// Check if automatic player launching is running, if not, ignore
		if (!t.getLaunchingPlayers() || t.isMatchStarted()) return;
		
		// Check player who logged in has been launched
		t.launch(e.getPlayer());
		
	}
	
	
		
	/**
	 * Handle death events; add bonus items, if any.
	 * 
	 * @param pDeath
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if (p.getKiller() != null) {
			ItemStack bonus = t.getKillerBonus();
			if (bonus != null)
				e.getDrops().add(bonus);
		}
		UhcPlayer up = t.getUhcPlayer(p);
		
		if (up != null) up.setDead(true);
		
		String msg = e.getDeathMessage();
		e.setDeathMessage(ChatColor.RED + msg);
		
		if (msg.indexOf("fell out of the world") == -1)
			t.setLastDeathLocation(p.getLocation());
		
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
