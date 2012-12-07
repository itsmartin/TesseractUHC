package com.martinbrook.tesseractuhc.command;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TplCommand extends UhcCommandExecutor {

	public TplCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		Location l = match.getLastLogoutLocation();
		if (l == null)
			return ERROR_COLOR + "Nobody has logged out.";

		sender.teleport(l);
		return null;
	}

}
