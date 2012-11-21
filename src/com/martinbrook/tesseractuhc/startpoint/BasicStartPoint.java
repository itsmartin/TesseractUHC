package com.martinbrook.tesseractuhc.startpoint;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BasicStartPoint extends UhcStartPoint {

	public BasicStartPoint(int number, Location location, boolean hasChest) {
		super(number, location, hasChest);
	}


	@Override
	public void buildStartingTrough() { }

	@Override
	public Block getChestBlock() { return location.getBlock().getRelative(0,-1,0); }

	@Override
	public Block getSignBlock() { return location.getBlock().getRelative(0,0,2); }

}
