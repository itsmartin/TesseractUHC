package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.MatchPhase;
import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ReadyCommand extends UhcCommandExecutor {

	public ReadyCommand(TesseractUHC plugin) {
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
		if (args.length > 1) {
			return (ERROR_COLOR + "Usage: /ready [seconds]");
		}
		
		int countLength = 120;
		
		try {
			if (args.length == 1)
				countLength = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return (ERROR_COLOR + "Invalid countdown length");
		}
		
		if (countLength < 120 && match.getMatchPhase() == MatchPhase.PRE_MATCH) {
			return (ERROR_COLOR + "Countdown less than 2 minutes - you must launch players first!");
		}
		
		if (match.startMatchCountdown(countLength))
			return (OK_COLOR + "Countdown started");
		else 
			return (ERROR_COLOR + "Countdown already in progress!");
		
	}

}
