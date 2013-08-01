package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class LaunchCommand extends UhcCommandExecutor {

	public LaunchCommand(TesseractUHC plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return run(args);
	}
	
	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return run(args);
	}
	
	private String run(String[] args) {
		// launch all players
		if (match.launchAll())
			return OK_COLOR + "Player launching has begun";
		else
			return ERROR_COLOR + "Player launching has already been started";
				
	}

}
