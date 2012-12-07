package com.martinbrook.tesseractuhc.command;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.util.MatchUtils;

public class CalcstartsCommand extends UhcCommandExecutor {

	public CalcstartsCommand(TesseractUHC plugin) {
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
		ArrayList<Location> starts = MatchUtils.calculateStarts(args);
		if (starts == null) return ERROR_COLOR + "No start locations were calculated";
		
		String response = OK_COLOR + "" + starts.size() + " start locations calculated: \n";
		for(int i = 0 ; i < starts.size() ; i++) {
			Location l = starts.get(i);
			response += (i+1) + ": x=" + l.getX() + " z=" + l.getZ() + "\n";
		}
		match.setCalculatedStarts(starts);
		return response;
		
	}

}
