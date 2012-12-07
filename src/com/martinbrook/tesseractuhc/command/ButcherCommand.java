package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ButcherCommand extends UhcCommandExecutor {

	public ButcherCommand(TesseractUHC plugin) {
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
		match.butcherHostile();
		return "Hostile mobs have been butchered";
	}
}
