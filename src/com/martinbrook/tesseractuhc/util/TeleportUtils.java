package com.martinbrook.tesseractuhc.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TeleportUtils {

	private TeleportUtils() { }

	/**
	 * Finds a teleport location for a spectator NEAR to another location
	 * 
	 * If possible, they will be placed 5 blocks away, facing towards the
	 * destination player.
	 * 
	 * @param l the location they are to be teleported near to
	 */
	public static Location getSpectatorTeleportLocation(Location l) {
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
		return tpl;
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
