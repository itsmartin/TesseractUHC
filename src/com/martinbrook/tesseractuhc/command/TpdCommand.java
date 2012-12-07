package com.martinbrook.tesseractuhc.command;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TpdCommand extends UhcCommandExecutor {

	public TpdCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {

		Location l = match.getLastDeathLocation();
		if (l == null)
			return ERROR_COLOR + "Nobody has died.";

		sender.teleport(l);
		return null;
	}
	
}
