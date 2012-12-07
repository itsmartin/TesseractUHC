package com.martinbrook.tesseractuhc.command;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class LeaveCommand extends UhcCommandExecutor {

	public LeaveCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.runAsSpectator(sender, args);
	}
	
	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return this.runAsPlayer(sender.getPlayer(), args);
	}

	@Override
	protected String runAsPlayer(UhcPlayer sender, String[] args) {
		if (!match.removeParticipant(sender.getName()))
			return ERROR_COLOR + "Leave failed";
		else
			return OK_COLOR + "You have left the match";

	}
}
