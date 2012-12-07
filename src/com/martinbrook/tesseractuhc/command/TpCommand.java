package com.martinbrook.tesseractuhc.command;

import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class TpCommand extends UhcCommandExecutor {

	public TpCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return runAsSpectator(sender,args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		
		if (args.length == 0) {
			if (match.getLastEventLocation() == null) {
				return (ERROR_COLOR + "You haven't specified to who you want to teleport.");
			}

			sender.teleport(match.getLastEventLocation());
			return null;

		} else if (args.length == 1){
			Player to = server.getPlayer(args[0]);
			if (to == null || !to.isOnline()) {
				return (ERROR_COLOR + "Player " + args[0] + " not found");
			}
			sender.teleport(to,OK_COLOR + "Teleported to " + to.getName());
			return null;
			
		} else if (args.length == 2){
			Player from = server.getPlayer(args[0]);
			if (from == null || !from.isOnline()) {
				return (ERROR_COLOR + "Player " + args[0] + " not found");
			}
			Player to = server.getPlayer(args[1]);
			if (to == null || !to.isOnline()) {
				return (ERROR_COLOR + "Player " + args[1] + " not found");
			}
			from.teleport(to);
			
			return (OK_COLOR + "Teleported " + from.getName() + " to " + to.getName());
			
		} else if (args.length==3){
			// Teleport sender to coords in their current world
			Double x;
			Double y;
			Double z;
			try {
				x = new Double (args[0]);
				y = new Double (args[1]);
				z = new Double (args[2]);
			} catch (NumberFormatException e) {
				return (ERROR_COLOR + "Invalid coordinates");
			}
			
			sender.teleport(x,y,z);
			return null;
			
		} else if (args.length==4){
			// Teleport a player to coords in their current world
			Player from = server.getPlayer(args[0]);
			if (from == null || !from.isOnline()) {
				return (ERROR_COLOR + "Player " + args[0] + " not found");
			}
				
			Double x;
			Double y;
			Double z;
			try {
				x = new Double (args[1]);
				y = new Double (args[2]);
				z = new Double (args[3]);
			} catch (NumberFormatException e) {
				return (ERROR_COLOR + "Invalid coordinates");
			}
			
			Location to = new Location(from.getWorld(),x,y,z);
			from.teleport(to);
			return (OK_COLOR + from.getName() + " has been teleported");
			
		} else {
			return (ERROR_COLOR + "Incorrect number of arguments");
		}
		
	}


	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		
		if(args.length == 2) {
			Player from = server.getPlayer(args[0]);
			if (from == null || !from.isOnline()) {
				return (ERROR_COLOR + "Player " + args[0] + " not found");
			}
			Player to = server.getPlayer(args[1]);
			if (to == null || !to.isOnline()) {
				return (ERROR_COLOR + "Player " + args[1] + " not found");
			}
			from.teleport(to);
			
			return (OK_COLOR + "Teleported " + from.getName() + " to " + to.getName());
			
		} else if(args.length==4) {
			// Teleport a player to coords in their current world
			Player from = server.getPlayer(args[0]);
			if (from == null || !from.isOnline()) {
				return (ERROR_COLOR + "Player " + args[0] + " not found");
			}
			
			Double x;
			Double y;
			Double z;
			try {
				x = new Double (args[1]);
				y = new Double (args[2]);
				z = new Double (args[3]);
			} catch (NumberFormatException e) {
				return (ERROR_COLOR + "Invalid coordinates");
			}
			
			Location to = new Location(from.getWorld(),x,y,z);
			from.teleport(to);
			return (OK_COLOR + from.getName() + " has been teleported");
			
		} else {
			return (ERROR_COLOR + "Incorrect number of arguments");
		}
	}
	


}
