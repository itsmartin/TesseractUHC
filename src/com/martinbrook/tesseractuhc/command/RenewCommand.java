package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class RenewCommand extends UhcCommandExecutor {

	public RenewCommand(TesseractUHC plugin) {
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
			return (ERROR_COLOR + "Please specify player to renew, or * to renew all players");
		}

		if (args[0].equals("*")) {
			for (UhcPlayer pl : match.getOnlinePlayers())
				if (pl.renew()) pl.sendMessage(OK_COLOR + "You have been healed and fed");

			return (OK_COLOR + "Renewed all players.");
		}
		
		UhcPlayer up = match.getPlayer(args[0]);
		
		if (up.isOnline()) {
			if (up.renew()) {
				up.sendMessage(OK_COLOR + "You have been healed and fed");
				return (OK_COLOR + "Renewed " + up.getName());
			} else {
				return (ERROR_COLOR + "Could not renew " + up.getName());
			}
		} else {
			return (ERROR_COLOR + "Player " + args[0] + " is not online.");
		}

	}

}
