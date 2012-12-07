package com.martinbrook.tesseractuhc.command;

import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ViCommand extends UhcCommandExecutor {

	public ViCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		if (args.length < 1)
			return ERROR_COLOR + "Please give the name of a player";
		
		Player p = server.getPlayer(args[0]);
		
		if (p == null)
			return ERROR_COLOR + "Player " + args[0] + " not found.";

		if (!sender.showInventory(p))
			return ERROR_COLOR + "Unable to view inventory";
		
		return null;

	}

}
