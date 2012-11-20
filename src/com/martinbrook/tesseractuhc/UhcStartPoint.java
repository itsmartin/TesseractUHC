package com.martinbrook.tesseractuhc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UhcStartPoint {

	private Location location;
	private UhcTeam team = null;
	private int number;

	public UhcStartPoint(int number, Location location) {
		this.location=location;
		this.number=number;
	}
	
	public UhcStartPoint(int number, World w, double x, double y, double z) {
		this(number, new Location(w,x,y,z));
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UhcTeam getTeam() {
		return team;
	}

	public void setTeam(UhcTeam team) {
		this.team = team;
	}

	public double getX() { return location.getX(); }
	public double getY() { return location.getY(); }
	public double getZ() { return location.getZ(); }

	public int getNumber() {
		return number;
	}


	
	
	/**
	 * Build a starting trough at this start point, and puts a starter chest and sign there.
	 *
	 */
	public void buildStartingTrough() {

		// Get the block containing the player's feet
		Block b = location.getBlock();
				
		// Block of glass below chest
		b.getRelative(0,-2,0).setType(Material.GLASS);
		
		// Four sides
		b.getRelative(-1,-1,0).setType(Material.GLOWSTONE);
		b.getRelative(-1,0,0).setType(Material.GLASS);
		b.getRelative(-1,1,0).setType(Material.GLASS);
		
		b.getRelative(1,-1,0).setType(Material.GLOWSTONE);
		b.getRelative(1,0,0).setType(Material.GLASS);
		b.getRelative(1,1,0).setType(Material.GLASS);

		b.getRelative(0,-1,-1).setType(Material.GLOWSTONE);
		b.getRelative(0,0,-1).setType(Material.GLASS);
		b.getRelative(0,1,-1).setType(Material.GLASS);

		b.getRelative(0,-1,1).setType(Material.GLOWSTONE);
		b.getRelative(0,0,1).setType(Material.GLASS);
		b.getRelative(0,1,1).setType(Material.GLASS);
		
		Block chestBlock = b.getRelative(0,-1,0);
		if (chestBlock.getType() != Material.CHEST) chestBlock.setType(Material.CHEST);
		
		makeSign();
	}
	

	/**
	 * Empty the starting chest
	 */
	public void emptyChest() {
		this.fillChest(new ItemStack[27]);
	}
	
	/**
	 * Fill the starting chest with specified contents
	 */
	public void fillChest(ItemStack[] items) {
		Block b = location.getBlock().getRelative(0,-1,0);
		if (b.getType() != Material.CHEST) b.setType(Material.CHEST);
		
		Inventory chest = ((Chest) b.getState()).getBlockInventory();
		chest.setContents(items);
	}
	
	
	/**
	 * Make a default start sign
	 */
	public void makeSign() {
		if (team == null) 
			makeSign("Start #" + number);
		else
			makeSign(team.getName());
	}
	
	/**
	 * Make a start sign with specific text
	 * 
	 * @param text The text to write on the sign
	 */
	public void makeSign(String text) {
		// TODO handle substantially longer text than one short username
		Block b = location.getBlock().getRelative(0,0,2);
		b.setType(Material.SIGN_POST);
		
		Sign s = (Sign) b.getState();
		s.setLine(0, "");
		s.setLine(1, text);
		s.setLine(2, "");
		s.setLine(3, "");

		s.update();
	}

	
}
