package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ClearpotsCommand extends UhcCommandExecutor {

	public ClearpotsCommand(TesseractUHC plugin) {
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
			return (ERROR_COLOR + "Please specify player to clear effects for, or * to clear effects for all players");
		}

		if (args[0].equals("*")) {
			for (UhcPlayer pl : match.getOnlinePlayers())
				if (pl.clearPotionEffects()) pl.sendMessage(OK_COLOR + "Your potion effects have been cleared");

			return (OK_COLOR + "Cleared effects for all players.");
		}
		
		UhcPlayer up = match.getPlayer(args[0]);
		
		if (up.isOnline()) {
			if (up.clearPotionEffects()) {
				up.sendMessage(OK_COLOR + "Your potion effects have been cleared");
				return (OK_COLOR + "Cleared effects for " + up.getName());
			} else {
				return (ERROR_COLOR + "Could not clear effects for " + up.getName());
			}
		} else {
			return (ERROR_COLOR + "Player " + args[0] + " is not online.");
		}

	}

}
