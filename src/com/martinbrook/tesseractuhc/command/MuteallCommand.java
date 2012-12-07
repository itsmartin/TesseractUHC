package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class MuteallCommand extends UhcCommandExecutor {

	public MuteallCommand(TesseractUHC plugin) {
		super(plugin);
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
		if (args.length < 1)
			return ERROR_COLOR +"Please specify 'on' or 'off'";

		if (args[0].equalsIgnoreCase("on")) {
			match.setChatMuted(true);
			return OK_COLOR + "Chat muted!";
		}
		if (args[0].equalsIgnoreCase("off")) {
			match.setChatMuted(false);
			return OK_COLOR + "Chat unmuted!";
		}
		
		return ERROR_COLOR + "Please specify 'on' or 'off'";

	}

}
