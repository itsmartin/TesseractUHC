package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class CdcCommand extends UhcCommandExecutor {

	public CdcCommand(TesseractUHC plugin) {
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
		if (match.cancelMatchCountdown())
			return OK_COLOR + "Match countdown cancelled!";
		
		if (match.cancelBorderCountdown())
			return OK_COLOR + "Border countdown cancelled!";

		return ERROR_COLOR + "No countdown is in progress";
	}

}
