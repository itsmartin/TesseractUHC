package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ChatscriptCommand extends UhcCommandExecutor {

	public ChatscriptCommand(TesseractUHC plugin) {
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
		String scriptFile;
		if (args.length < 1)
			scriptFile = "announcement.txt"; 
		else
			scriptFile = args[0];
		match.playChatScript(scriptFile, true);
		return OK_COLOR + "Starting chat script";
	}

}
