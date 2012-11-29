package com.martinbrook.tesseractuhc;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.util.TeleportUtils;

public class UhcSpectator {

	private String name;
	private boolean interacting = false;
	private Integer cyclePoint = null;
	private Location tpBackLocation = null;
	private UhcMatch m;

	public UhcSpectator(String name, UhcMatch m) { 
		this.name = name;
		this.m = m;
		this.tpBackLocation = m.getStartingWorld().getSpawnLocation();
	}
	
	public void setInteracting(boolean interacting) { this.interacting=interacting; }
	public boolean isInteracting() { return this.interacting; }

	public String getName() { return name; }

	public int nextCyclePoint(int numberOfPlayers) {
		if (cyclePoint == null) {
			cyclePoint = 0;
		} else {
			if (++cyclePoint >= numberOfPlayers) cyclePoint = 0;
		}
		return cyclePoint;
	}
	
	public boolean teleport(Player p) { return this.teleport(p, "You have been teleported!"); }
	public boolean teleport(Location l) { return this.teleport(l, "You have been teleported!"); }
	public boolean teleport(Player p, String message) {	return this.teleport(TeleportUtils.getSpectatorTeleportLocation(p.getLocation()), message); }
	public boolean teleport(Location l, String message) {
		Player p = getPlayer();
		if (p == null) return false;
		
		Location oldLocation = p.getLocation();
		
		// Ensure the chunk is loaded before teleporting
		World w = l.getWorld();
		Chunk chunk = w.getChunkAt(l);
		if (!w.isChunkLoaded(chunk))
			w.loadChunk(chunk);
				
		if (p.teleport(l)) {
			if (message != null && !message.isEmpty())
				p.sendMessage(TesseractUHC.OK_COLOR + message);
			tpBackLocation = oldLocation;
			p.setFlying(true);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean tpBack() {
		return teleport(tpBackLocation, "You have been teleported back!");
	}
	
	public Player getPlayer() { return m.getServer().getPlayerExact(name); }
	
}
