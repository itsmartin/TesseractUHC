package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class PermadayCommand extends UhcCommandExecutor {

	public PermadayCommand(TesseractUHC plugin) {
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
			return OK_COLOR + "Permaday is " + (match.getPermaday() ? "on" : "off");
		
		if (args[0].equalsIgnoreCase("off") || args[0].equals("0")) {
			match.setPermaday(false);
		} else if (args[0].equalsIgnoreCase("on") || args[0].equals("1")) {
			match.setPermaday(true);
		} else {
			return ERROR_COLOR + "Argument '" + args[0] + "' not understood";
		}
		return null;

	}

}
