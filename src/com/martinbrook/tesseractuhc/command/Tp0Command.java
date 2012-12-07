package com.martinbrook.tesseractuhc.command;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class Tp0Command extends UhcCommandExecutor {

	public Tp0Command(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		sender.teleport(match.getStartingWorld().getSpawnLocation());
		return OK_COLOR + "Teleported to spawn";

	}

}
