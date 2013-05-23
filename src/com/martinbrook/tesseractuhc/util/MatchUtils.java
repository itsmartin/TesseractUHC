package com.martinbrook.tesseractuhc.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;

public class MatchUtils {

	private static Method mAffectsSpawning = null;
	private static Method mCollidesWithEntities = null;

	static {
		try {
			mAffectsSpawning = HumanEntity.class.getDeclaredMethod("setAffectsSpawning", boolean.class);
			mCollidesWithEntities = Player.class.getDeclaredMethod("setCollidesWithEntities", boolean.class);
		}
		catch (Exception e) {  }
	}
	

	private MatchUtils() { }
	
	
	public static String formatDuration(long d, boolean precise) {
		if (precise) {
			long seconds = d % 60;
			d = d / 60;
			long minutes = d % 60;
			long hours = d / 60;
			
			// The string
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			long minutes = d / 60;
			return minutes + " minute" + (minutes != 1 ? "s" : "");
			
		}
		
	}
	
	public static String formatDuration(Calendar t1, Calendar t2, boolean precise) {
		// Convert to duration in seconds
		return formatDuration(getDuration(t1, t2), precise);
	}
	
	public static long getDuration(Calendar t1, Calendar t2) {
		return (t2.getTimeInMillis() - t1.getTimeInMillis()) / 1000;
	}

	/**
     * Get a BorderData from the WorldBorder plugin
     * 
     * @param w The world to get borders for
     * @return The WorldBorder BorderData object
     */
    private static BorderData getWorldBorder(World w) {
        Plugin plugin = TesseractUHC.getInstance().getServer().getPluginManager().getPlugin("WorldBorder");

        // Check if the plugin is loaded
        if (plugin == null || !(plugin instanceof WorldBorder))
        	return null;

        WorldBorder wb = (WorldBorder) plugin;
        return wb.GetWorldBorder(w.getName());
    }
    
    /**
     * Change the WorldBorder radius for a world
     * 
     * @param w The world to be changed
     * @param radius The new radius
     * @return Whether the operation succeeded
     */
    public static boolean setWorldRadius(World w, int radius) {
    	BorderData border = getWorldBorder(w);
    	if (border != null) {
    		border.setRadius(radius);
    		return true;
    	}
    	return false;
    }

	/**
	 * Attempt to parse a calcstarts command and return a list of start points
	 * 
	 * @param args The arguments which were passed to the command
	 * @return List of start locations, or null if failed
	 */
	public static ArrayList<Location> calculateStarts(String[] args) {
		if (args.length < 1) return null;
		String method = args[0];
		if ("radial".equalsIgnoreCase(method)) {
			if (args.length != 3) return null;
			int count;
			int radius;
			try {
				count = Integer.parseInt(args[1]);
				radius = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return null;
			}
			return calculateRadialStarts(count, radius);
			
		}
		return null;
		

	}

	/**
	 * Generate a list of radial start points
	 * 
	 * @param count Number of starts to generate
	 * @param radius Radius of circle
	 * @return List of starts
	 */
	private static ArrayList<Location> calculateRadialStarts(int count, int radius) {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		double arc = (2*Math.PI) / count;
		World w = TesseractUHC.getInstance().getMatch().getStartingWorld();
		
		for(int i = 0; i < count; i++) {
			int x = (int) (radius * Math.cos(i*arc));
			int z = (int) (radius * Math.sin(i*arc));
			
			int y = w.getHighestBlockYAt(x, z);
			locations.add(new Location(w,x,y,z));
		}
		return locations;
	
	}
	
	/**
	 * Convert a string to a boolean.
	 * 
	 * true, on, yes, y, 1 => True
	 * false, off, no, n, 0 => False
	 * 
	 * @param s The string to check
	 * @return Boolean value, or null if not parsable
	 */
	public static Boolean stringToBoolean(String s) {
		if ("true".equalsIgnoreCase(s) || "on".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s) || "y".equalsIgnoreCase(s) || "1".equals(s))
			return true;
		if ("false".equalsIgnoreCase(s) || "off".equalsIgnoreCase(s) || "no".equalsIgnoreCase(s) || "n".equalsIgnoreCase(s) || "0".equals(s))
			return false;
		return null;
		
	}
	
	/**
	 * Format a string for display on a sign
	 * 
	 * @param s The string to be formatted
	 * @return An array with 4 elements, containing the 4 lines to go on the sign
	 */
	public static String[] signWrite(String s) {
		// Create array to hold the lines, and initialise with empty strings
		String[] lines = new String[4];
		int currentLine = 0;
		for (int i = 0; i < 4; i++) lines[i] = "";
		
		// Split the message into strings on whitespace
		String[] words = s.split("\\s");
		
		// Loop through words, adding them to lines as they fit
		int currentWord = 0;
		while(currentLine < 4 && currentWord < words.length) {
			if (lines[currentLine].length() + words[currentWord].length() <= 14)
				lines[currentLine] += " " + words[currentWord++];
			else
				currentLine++;
		}
		
		// If we have only used one or two lines, move everything down by one.
		if (currentLine < 2) {
			lines[2]=lines[1];
			lines[1]=lines[0];
			lines[0]="";
		}
		
		return lines;
		
	}
	/**
	 * Gets a copy of a player's current inventory, including armor/health/hunger details.
	 *
	 * @author AuthorBlues
	 * @param player The player to be viewed
	 * @return inventory The player's inventory
	 *
	 */
	public static Inventory getInventoryView(Player player)
	{

		PlayerInventory pInventory = player.getInventory();
		Inventory inventoryView = Bukkit.getServer().createInventory(null,
			pInventory.getSize() + 9, player.getDisplayName() + "'s Inventory");

		ItemStack[] oldContents = pInventory.getContents();
		ItemStack[] newContents = inventoryView.getContents();

		for (int i = 0; i < oldContents.length; ++i)
			if (oldContents[i] != null) newContents[i] = oldContents[i];

		newContents[oldContents.length + 0] = pInventory.getHelmet();
		newContents[oldContents.length + 1] = pInventory.getChestplate();
		newContents[oldContents.length + 2] = pInventory.getLeggings();
		newContents[oldContents.length + 3] = pInventory.getBoots();

		newContents[oldContents.length + 7] = new ItemStack(Material.APPLE, player.getHealth());
		newContents[oldContents.length + 8] = new ItemStack(Material.COOKED_BEEF, player.getFoodLevel());

		for (int i = 0; i < oldContents.length; ++i)
			if (newContents[i] != null) newContents[i] = newContents[i].clone();

		inventoryView.setContents(newContents);
		return inventoryView;
	}
	


	/**
	 * Checks if the SportBukkit API is available
	 *
	 * @author AuthorBlues
	 * @return true if SportBukkit is installed, false otherwise
	 * @see http://www.github.com/rmct/SportBukkit
	 */
	public static boolean hasSportBukkitApi() {
		return mAffectsSpawning != null && mCollidesWithEntities != null;
	}

	/**
	 * Sets whether player affects spawning via natural spawn and mob spawners.
	 * Uses last_username's affects-spawning API from SportBukkit
	 *
	 * @author AuthorBlues
	 * @param affectsSpawning Set whether player affects spawning
	 * @see http://www.github.com/rmct/SportBukkit
	 */
	public static void setAffectsSpawning(Player player, boolean affectsSpawning) {
		if (mAffectsSpawning != null) try {
			mAffectsSpawning.invoke(player, affectsSpawning);
		} catch (Exception e) { }
	}

	/**
	 * Sets whether player collides with entities, including items and arrows.
	 * Uses last_username's collides-with-entities API from SportBukkit
	 *
	 * @author AuthorBlues
	 * @param collidesWithEntities Set whether player collides with entities
	 * @see http://www.github.com/rmct/SportBukkit
	 */
	public static void setCollidesWithEntities(Player player, boolean collidesWithEntities) {
		if (mCollidesWithEntities != null) try {
			mCollidesWithEntities.invoke(player, collidesWithEntities);
		}
		catch (Exception e) { }
	}
	
	
	public static BlockFace getBlockFaceDirection(Location l) {
        double rotation = (l.getYaw()+180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 11.25) {
            return BlockFace.NORTH;
        } else if (11.25 <= rotation && rotation < 33.75) {
            return BlockFace.NORTH_NORTH_EAST;
        } else if (33.75 <= rotation && rotation < 56.25) {
            return BlockFace.NORTH_EAST;
        } else if (56.25 <= rotation && rotation < 78.75) {
            return BlockFace.EAST_NORTH_EAST;
        } else if (78.75 <= rotation && rotation < 101.25) {
            return BlockFace.EAST;
        } else if (101.25 <= rotation && rotation < 123.75) {
            return BlockFace.EAST_SOUTH_EAST;
        } else if (123.75 <= rotation && rotation < 146.25) {
            return BlockFace.SOUTH_EAST;
        } else if (146.25 <= rotation && rotation < 168.75) {
            return BlockFace.SOUTH_SOUTH_EAST;
        } else if (168.75 <= rotation && rotation < 191.25) {
            return BlockFace.SOUTH;
        } else if (191.25 <= rotation && rotation < 213.75) {
            return BlockFace.SOUTH_SOUTH_WEST;
        } else if (213.75 <= rotation && rotation < 236.25) {
            return BlockFace.SOUTH_WEST;
        } else if (236.25 <= rotation && rotation < 258.75) {
            return BlockFace.WEST_SOUTH_WEST;
        } else if (258.75 <= rotation && rotation < 281.25) {
            return BlockFace.WEST;
        } else if (281.25 <= rotation && rotation < 303.75) {
            return BlockFace.WEST_NORTH_WEST;
        } else if (303.75 <= rotation && rotation < 326.25) {
            return BlockFace.NORTH_WEST;
        } else if (326.25 <= rotation && rotation < 348.75) {
            return BlockFace.NORTH_NORTH_WEST;
        } else if (348.75 <= rotation && rotation < 360.0) {
            return BlockFace.NORTH;
        } else {
            return null;
        }
    }
	
}
