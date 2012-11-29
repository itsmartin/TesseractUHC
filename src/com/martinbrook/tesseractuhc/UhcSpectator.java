package com.martinbrook.tesseractuhc;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.martinbrook.tesseractuhc.util.MatchUtils;
import com.martinbrook.tesseractuhc.util.TeleportUtils;

public class UhcSpectator {

	private boolean interacting = false;
	private Integer cyclePoint = null;
	private Location tpBackLocation = null;
	private UhcPlayer player;

	public UhcSpectator(UhcPlayer pl) { 
		this.player = pl;
		this.tpBackLocation = player.getMatch().getStartingWorld().getSpawnLocation();
	}
	
	public void setInteracting(boolean interacting) { this.interacting=interacting; }
	public boolean isInteracting() { return this.interacting; }

	public String getName() { return player.getName(); }

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
		Player p = player.getPlayer();
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

	public void sendMessage(String message) { player.sendMessage(message); }
	public void toggleGameMode() {
		player.setGameMode((player.getGameMode() == GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE);
	}

	public void teleport(Double x, Double y, Double z) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Show a spectator the contents of a player's inventory.
	 * 
	 * @param viewed The player being observed
	 */
	public boolean showInventory(Player viewed) {

		Inventory i = MatchUtils.getInventoryView(viewed);
		if (i == null) return false;
		
		player.getPlayer().openInventory(i);
		return true;
	}
	
}
