package com.martinbrook.tesseractuhc.command;

import java.util.ArrayList;

import org.bukkit.Location;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TpcsCommand extends UhcCommandExecutor {

	public TpcsCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		ArrayList<Location> starts = match.getCalculatedStarts();
		if (starts == null)
			return ERROR_COLOR + "Start points have not been calculated";
		
		if (args.length != 1)
			return ERROR_COLOR + "Please give the start number";
		
		
		try {
			int startNumber = Integer.parseInt(args[0]);
			sender.teleport(starts.get(startNumber - 1));
		} catch (NumberFormatException e) {
			return ERROR_COLOR + "Please give the start number";
		} catch (IndexOutOfBoundsException e) {
			return ERROR_COLOR + "That start does not exist";
		}
		
		return null;

	}

}
