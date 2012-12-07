package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class SetvanishCommand extends UhcCommandExecutor {

	public SetvanishCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.run();
	}

	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return this.run();
	}
	
	private String run() {
		match.setVanish();
		return OK_COLOR + "Visibility of all players has been updated";
	}

}
