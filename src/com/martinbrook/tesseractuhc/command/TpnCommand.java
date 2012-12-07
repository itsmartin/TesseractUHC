package com.martinbrook.tesseractuhc.command;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TpnCommand extends UhcCommandExecutor {

	public TpnCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		Location l = match.getLastNotifierLocation();
		if (l == null)
			return ERROR_COLOR + "No notification.";

		sender.teleport(l);
		return null;
	}

}
