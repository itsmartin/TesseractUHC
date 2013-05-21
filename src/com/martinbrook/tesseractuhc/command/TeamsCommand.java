package com.martinbrook.tesseractuhc.command;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.UhcTeam;

public class TeamsCommand extends UhcCommandExecutor {

	public TeamsCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.run(args);

	}

	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return this.run(args);
	}

	private String run(String[] args) {
		// teams - lists all teams
		// teams add identifier name name name - adds a team
		if (config.isFFA())
			return ERROR_COLOR + "This is not a team match. Use /players to list players";
		
		if (args.length < 1) {
			Collection<UhcTeam> allTeams = match.getTeams();
			String response = ChatColor.GOLD + "" + allTeams.size() + " teams (" + match.countTeamsInMatch() + " still alive):\n";
			
			for (UhcTeam team : allTeams) {
				response += (team.aliveCount()==0 ? ChatColor.RED + "[D] " : ChatColor.GREEN) + "" +
						ChatColor.ITALIC + team.getName() + ChatColor.GRAY +
						" [" + team.getIdentifier() + "]\n";
			}
			return response;
		}
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2)
				return ERROR_COLOR + "Specify team to add!";
			
			String identifier = args[1];
			String name = "";
			
			if (args.length < 3)
				name = identifier;
			else {
				for (int i = 2; i < args.length; i++) name += args[i] + " ";
				name = name.substring(0,name.length()-1);
			}
				
			if (!match.addTeam(identifier, name))
				return ERROR_COLOR + "Could not add team";
			
			return OK_COLOR + "Team created";
			
		} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rm")) {
			if (args.length < 2)
				return ERROR_COLOR + "Specify team to remove!";
			if (!match.removeTeam(args[1]))
				return ERROR_COLOR + "Team could not be removed!";

			return OK_COLOR + "Team removed";
		}

		
		
		return null;
	}

}
