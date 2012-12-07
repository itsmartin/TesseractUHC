package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class FeedCommand extends UhcCommandExecutor {

	public FeedCommand(TesseractUHC plugin) {
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
			return (ERROR_COLOR + "Please specify player to feed, or * to feed all players");
		}

		if (args[0].equals("*")) {
			for (UhcPlayer pl : match.getOnlinePlayers())
				if (pl.feed()) pl.sendMessage(OK_COLOR + "You have been fed");

			return (OK_COLOR + "Fed all players.");
		}
		
		UhcPlayer up = match.getPlayer(args[0]);
		
		if (up.isOnline()) {
			if (up.feed()) {
				up.sendMessage(OK_COLOR + "You have been fed");
				return (OK_COLOR + "Fed " + up.getName());
			} else {
				return (ERROR_COLOR + "Could not feed " + up.getName());
			}
		} else {
			return (ERROR_COLOR + "Player " + args[0] + " is not online.");
		}
	}

}
