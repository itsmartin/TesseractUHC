package com.martinbrook.tesseractuhc.command;

import java.util.ArrayList;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPOI;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TppCommand extends UhcCommandExecutor {

	public TppCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		ArrayList<UhcPOI> pois = match.getPOIs();
		if (args.length != 1)
			return ERROR_COLOR + "Please give the POI number - see /uhc pois";
		
		try {
			int poiNo = Integer.parseInt(args[0]);
			sender.teleport(pois.get(poiNo - 1).getLocation());
		} catch (NumberFormatException e) {
			return ERROR_COLOR + "Please give the POI number - see /uhc pois";
		} catch (IndexOutOfBoundsException e) {
			return ERROR_COLOR + "That POI does not exist";
		}
		
		return null;

	}

}
