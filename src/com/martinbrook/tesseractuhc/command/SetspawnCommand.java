package com.martinbrook.tesseractuhc.command;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class SetspawnCommand extends UhcCommandExecutor {

	public SetspawnCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		Location newSpawn = sender.getLocation();
		newSpawn.getWorld().setSpawnLocation(newSpawn.getBlockX(), newSpawn.getBlockY(), newSpawn.getBlockZ());
		return OK_COLOR + "This world's spawn point has been set to " + newSpawn.getBlockX() + "," + newSpawn.getBlockY() + "," + newSpawn.getBlockZ();

	}

}
