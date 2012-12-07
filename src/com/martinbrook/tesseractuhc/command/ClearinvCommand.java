package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ClearinvCommand extends UhcCommandExecutor {

	public ClearinvCommand(TesseractUHC plugin) {
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
		if (args.length == 0) {
			return (ERROR_COLOR + "Please specify player to clear, or * to clear all players");
		}

		if (args[0].equals("*")) {
			for (UhcPlayer pl : match.getOnlinePlayers())
				if (pl.clearInventory()) pl.sendMessage(OK_COLOR + "Your inventory has been cleared");

			return (OK_COLOR + "Cleared inventory of all players.");
		}
		
		UhcPlayer up = match.getPlayer(args[0]);
		
		if (up.isOnline()) {
			if (up.clearInventory()) {
				up.sendMessage(OK_COLOR + "Your inventory has been cleared");
				return (OK_COLOR + "Cleared inventory of " + up.getName());
			} else {
				return (ERROR_COLOR + "Could not clear inventory of " + up.getName());
			}
		} else {
			return (ERROR_COLOR + "Player " + args[0] + " is not online.");
		}

	}

}
