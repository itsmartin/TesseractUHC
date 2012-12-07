package com.martinbrook.tesseractuhc.command;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.startpoint.UhcStartPoint;

public class TpsCommand extends UhcCommandExecutor {

	public TpsCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		// Teleport sender to the specified start point, either by player name or by number
		if (args.length != 1)
			return ERROR_COLOR + "Incorrect number of arguments for /tps";
		
		UhcStartPoint destination = match.findStartPoint(args[0]);
		
		if (destination != null) {
			sender.teleport(destination.getLocation());
			return null;
		} else {
			return ERROR_COLOR + "Unable to find that start point";
		}

	}

}
