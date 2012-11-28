package com.martinbrook.tesseractuhc.util;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.TesseractUHC;

public class TeleportUtils {

	private TeleportUtils() { }

	/**
	 * Teleport one player to another. If player is opped, fancy
	 * teleport will be done. Adds a custom message to be displayed.
	 * 
	 * @param p1 player to be teleported
	 * @param p2 player to be teleported to
	 * @param message the message to be displayed
	 */
	public static void doTeleport(Player p1, Player p2, String message, boolean fancy) {
		//saveTpLocation(p1);
	
		// if the first player is a spectator, do fancy teleport.
		if (!fancy)
			p1.teleport(p2);
		else
			doFancyTeleport(p1, p2);
	
		// If player is in creative, set them to be in flight
		if (p1.getGameMode() == GameMode.CREATIVE)
			p1.setFlying(true);
	
		// Send the teleport message, if provided
		if (message != null && !message.isEmpty())
			p1.sendMessage(TesseractUHC.OK_COLOR + message);
	}

	/**
	 * Teleport one player to another. If player is opped, fancy
	 * teleport will be done.
	 * 
	 * @param p1 player to be teleported
	 * @param p2 player to be teleported to
	 */
	public static void doTeleport(Player p1, Player p2, boolean fancy) {
		doTeleport(p1, p2, "You have been teleported!", fancy);
	}

	/**
	 * Teleport a player to a specific location
	 * 
	 * @param p1 player to be teleported
	 * @param l location to be teleported to
	 */
	public static void doTeleport(Player p1, Location l) {
		//saveTpLocation(p1);
		// Check if the location is loaded
		World w = l.getWorld();
		Chunk chunk = w.getChunkAt(l);
		if (!w.isChunkLoaded(chunk))
			w.loadChunk(chunk);
		p1.teleport(l);
		if (p1.getGameMode() == GameMode.CREATIVE)
			p1.setFlying(true);
		p1.sendMessage(TesseractUHC.OK_COLOR + "You have been teleported!");
	}

	/**
	 * Teleports a player NEAR to another player
	 * 
	 * If possible, they will be placed 5 blocks away, facing towards the
	 * destination player.
	 * 
	 * @param streamer the Player who will be fancy-teleported
	 * @param p the Player they are to be teleported to
	 */
	private static void doFancyTeleport(Player streamer, Player p) {
		Location l = p.getLocation();
	
		Location lp = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		Location lxp = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		Location lxn = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		Location lzp = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		Location lzn = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		Location tpl = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		boolean xp = true, xn = true, zp = true, zn = true;
	
		for (int i = 0; i < 5; i++) {
			if (xp) {
				lxp.setX(lxp.getX() + 1);
				if (!TeleportUtils.isSpaceForPlayer(lxp))
					xp = false;
			}
			if (xn) {
				lxn.setX(lxn.getX() - 1);
				if (!TeleportUtils.isSpaceForPlayer(lxn))
					xn = false;
			}
			if (zp) {
				lzp.setZ(lzp.getZ() + 1);
				if (!TeleportUtils.isSpaceForPlayer(lzp))
					zp = false;
			}
			if (zn) {
				lzn.setZ(lzn.getZ() - 1);
				if (!TeleportUtils.isSpaceForPlayer(lzn))
					zn = false;
			}
		}
	
		if (!xp)
			lxp.setX(lxp.getX() - 1);
		if (!xn)
			lxn.setX(lxn.getX() + 1);
		if (!zp)
			lzp.setZ(lzp.getZ() - 1);
		if (!zn)
			lzn.setZ(lzn.getZ() + 1);
	
		tpl.setYaw(90);
		tpl.setPitch(0);
	
		if (lxp.distanceSquared(lp) > tpl.distanceSquared(lp)) {
			tpl = lxp;
			tpl.setYaw(90);
		}
		if (lxn.distanceSquared(lp) > tpl.distanceSquared(lp)) {
			tpl = lxn;
			tpl.setYaw(270);
		}
		if (lzp.distanceSquared(lp) > tpl.distanceSquared(lp)) {
			tpl = lzp;
			tpl.setYaw(180);
		}
		if (lzn.distanceSquared(lp) > tpl.distanceSquared(lp)) {
			tpl = lzn;
			tpl.setYaw(0);
		}
		streamer.teleport(tpl);
	}

	/**
	 * Calculates if it's possible for a player to fit in a certain spot.
	 * 
	 * @param feetLocation the location where the feet should be
	 * @return wheter or not there's place for both his feet and head
	 */
	public static boolean isSpaceForPlayer(Location feetLocation) {
		World w = feetLocation.getWorld();
		int x = feetLocation.getBlockX(), y = feetLocation.getBlockY(), z = feetLocation.getBlockZ();
		Block b1 = w.getBlockAt(x, y, z);
		Block b2 = w.getBlockAt(x, y + 1, z);
		return isSpaceForPlayer(b1) && isSpaceForPlayer(b2);
	}

	/**
	 * Calculates if it's possible for a player to fit in a certain spot.
	 * 
	 * @param w the world in which we need to check if there's space for the
	 *            player
	 * @param x the x coordinate of the block to check
	 * @param y the y coordinate of the block (at the feet) to check
	 * @param z the z coordinate of the block to check
	 * @return whether or not there's place for both his feet and head
	 */
	public static boolean isSpaceForPlayer(World w, int x, int y, int z) {
		Block b1 = w.getBlockAt(x, y, z);
		Block b2 = w.getBlockAt(x, y + 1, z);
		return isSpaceForPlayer(b1) && isSpaceForPlayer(b2);
	}

	/**
	 * Determine whether a given block is a either empty or liquid (but not
	 * lava)
	 * 
	 * @param b the block to check
	 * @return whether the block is suitable
	 */
	public static boolean isSpaceForPlayer(Block b) {
		return (b.isEmpty() || b.isLiquid()) && b.getType() != Material.LAVA && b.getType() != Material.STATIONARY_LAVA;
	}

}
