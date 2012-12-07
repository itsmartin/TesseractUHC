package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class RelaunchCommand extends UhcCommandExecutor {

	public RelaunchCommand(TesseractUHC plugin) {
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
			return ERROR_COLOR + "Please specify player to relaunch";
		} else {
			UhcPlayer pl = match.getExistingPlayer(args[0]);
			if (pl == null || !pl.isOnline())
				return ERROR_COLOR + "Player " + args[0] + " not found";
			
			if (!pl.isParticipant())
				return ERROR_COLOR + "Player is not a participant in this match";
			
			boolean success = pl.getParticipant().sendToStartPoint();
			if (success)
				return OK_COLOR + "Relaunched " + pl.getDisplayName();
			else 
				return ERROR_COLOR + "Player could not be relaunched";
		}
	}

}
