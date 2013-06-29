package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class CdwbCommand extends UhcCommandExecutor {

	public CdwbCommand(TesseractUHC plugin) {
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
		
		int newRadius;
		
		if (args.length == 0 || args.length > 2) {
			return (ERROR_COLOR + "Specify world radius and countdown duration");
		}
		
		try {
			newRadius = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return (ERROR_COLOR + "World radius must be specified as an integer");
		}
		
		int countLength = 180;
		
		if (args.length == 2)
			try {
				countLength = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return (ERROR_COLOR + "Countdown duration was not understood");
			}
		
		if (match.startBorderCountdown(countLength, newRadius))
			return (OK_COLOR + "Countdown started");
		else
			return (ERROR_COLOR + "Unable to start border countdown");
		
	}

}
