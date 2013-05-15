package com.martinbrook.tesseractuhc.command;

import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class SpectateCommand extends UhcCommandExecutor {

	
	public SpectateCommand(TesseractUHC plugin) {
		super(plugin);
	}
	
	
	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		if(args.length<1||args.length>1)
			return ERROR_COLOR +"Syntax: /spectate [Player Name]";
		
		
		return this.run(args[0]);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		if(args.length<1||args.length>1)
			return ERROR_COLOR +"Syntax: /spectate [Player Name]";
		
		
		return this.run(args[0]);
	}
	
	protected String run(String name){
		UhcPlayer pl =match.getPlayer(name);
		if(!pl.isOnline()){
			return ERROR_COLOR +"That player does not seem to be online.";
		}else if(pl.isActiveParticipant()){
			return ERROR_COLOR +"That player is still playing. ";
		}
		pl.setParticipant(null);
		pl.makeSpectator();
		return OK_COLOR +pl.getName()+" has been made a spectator;";
		
	}
	
}
