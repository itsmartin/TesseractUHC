package com.martinbrook.tesseractuhc;

import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import com.martinbrook.tesseractuhc.startpoint.UhcStartPoint;

public class UhcParticipant implements PlayerTarget {
	private String name;
	private boolean launched = false;
	private UhcTeam team;
	private HashSet<PlayerTarget> nearbyTargets = new HashSet<PlayerTarget>();
	private UhcMatch m;

	private boolean dead = false;
	
	public UhcParticipant(String name, UhcTeam team, UhcMatch m) {
		this.name = name;
		this.team = team;
		this.m = m;
	}
		
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isLaunched() {
		return launched;
	}


	public void setLaunched(boolean launched) {
		this.launched = launched;
	}


	public UhcStartPoint getStartPoint() {
		return team.getStartPoint();
	}


	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public UhcTeam getTeam() {
		return team;
	}

	public boolean isNearTo(PlayerTarget target) {
		return nearbyTargets.contains(target);
	}

	public void setNearTo(PlayerTarget target, boolean b) {
		if (b)
			nearbyTargets.add(target);
		else
			nearbyTargets.remove(target);
		
	}

	public boolean teleport(Player p) { return this.teleport(p, "You have been teleported!"); }
	public boolean teleport(Location l) { return this.teleport(l, "You have been teleported!"); }
	public boolean teleport(Player p, String message) { return this.teleport(p.getLocation(), message); }
	public boolean teleport(Location l, String message) {
		Player p = getPlayer();
		if (p == null) return false;
		
		// Ensure the chunk is loaded before teleporting
		World w = l.getWorld();
		Chunk chunk = w.getChunkAt(l);
		if (!w.isChunkLoaded(chunk))
			w.loadChunk(chunk);
		
		if (p.teleport(l)) {
			if (message != null && !message.isEmpty())
				p.sendMessage(TesseractUHC.OK_COLOR + message);
			return true;
		} else {
			return false;
		}
	}
	
	public Player getPlayer() { return m.getServer().getPlayerExact(name); }

	public boolean sendToStartPoint() {
		return (setGameMode(GameMode.ADVENTURE) && teleport(getStartPoint().getLocation()) && renew());
	}
	
	public boolean start() {
		return (feed() && clearXP() && clearPotionEffects() && heal() && setGameMode(GameMode.SURVIVAL));
	}

	public boolean renew() {
		return (heal() && feed() && clearXP() && clearPotionEffects() && clearInventory());
	}
	
	/**
	 * Heal the player
	 */
	public boolean heal() {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		p.setHealth(20);
		return true;
	}

	/**
	 * Feed the player
	 */
	public boolean feed() {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		p.setFoodLevel(20);
		p.setExhaustion(0.0F);
		p.setSaturation(5.0F);
		return true;
	}

	/**
	 * Reset XP of the given player
	 */
	public boolean clearXP() {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		p.setTotalExperience(0);
		p.setExp(0);
		p.setLevel(0);
		return true;
	}

	/**
	 * Clear potion effects of the given player
	 */
	public boolean clearPotionEffects() {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		for (PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		return true;
	}

	/**
	 * Clear inventory and ender chest of the given player
	 */
	public boolean clearInventory() {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		PlayerInventory i = p.getInventory();
		i.clear();
		i.setHelmet(null);
		i.setChestplate(null);
		i.setLeggings(null);
		i.setBoots(null);
		
		p.getEnderChest().clear();
		return true;
		
	}
	
	public boolean setGameMode(GameMode g) {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		p.setGameMode(g);
		return true;
	}

	public boolean sendMessage(String message) {
		Player p = getPlayer();
		if (p==null || !p.isOnline()) return false;
		
		p.sendMessage(message);
		return true;
	}

}
