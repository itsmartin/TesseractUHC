package com.martinbrook.tesseractuhc.command;

import org.bukkit.ChatColor;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.util.MatchUtils;

public class NvCommand extends UhcCommandExecutor {

	public NvCommand(TesseractUHC plugin) {
		super(plugin);
	}


	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return run(sender, args);
	}
	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return run(sender, args);
	}
	
	private String run(UhcSpectator sender,String[] args){
		if(args.length<1){
			return ERROR_COLOR +"Syntax: /nv [on | off]";
		}
		
		Boolean value = MatchUtils.stringToBoolean(args[0]);
		if (value == null)
			return ERROR_COLOR +"Syntax: /nv [on | off]";
		
		
		sender.setNightVision(value);
		return ChatColor.GOLD + "Night vision " + (value? "applied" : "removed");
	}

}
