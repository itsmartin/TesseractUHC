package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class HealCommand extends UhcCommandExecutor {

	public HealCommand(TesseractUHC plugin) {
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
			return (ERROR_COLOR + "Please specify player to heal, or * to heal all players");
		}

		if (args[0].equals("*")) {
			for (UhcPlayer pl : match.getOnlinePlayers())
				if (pl.heal()) pl.sendMessage(OK_COLOR + "You have been healed");

			return (OK_COLOR + "Healed all players.");
		}
		
		UhcPlayer up = match.getPlayer(args[0]);
		
		if (up.isOnline()) {
			if (up.heal()) {
				up.sendMessage(OK_COLOR + "You have been healed");
				return (OK_COLOR + "Healed " + up.getName());
			} else {
				return (ERROR_COLOR + "Could not heal " + up.getName());
			}
		} else {
			return (ERROR_COLOR + "Player " + args[0] + " is not online.");
		}
	}



}
