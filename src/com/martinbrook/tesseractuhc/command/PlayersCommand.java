package com.martinbrook.tesseractuhc.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.ConsoleCommandSender;
import com.martinbrook.tesseractuhc.MatchPhase;
import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcParticipant;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class PlayersCommand extends UhcCommandExecutor {


	public PlayersCommand(TesseractUHC plugin) {
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
		// players - lists all players
		// players add playername
		// players add playername teamidentifier
		// players addall

		if (args.length < 1) {

			return match.getPlayerStatusReport();
			
		}
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2)
				return ERROR_COLOR + "Specify player to add!";
			
			if (match.getMatchPhase() != MatchPhase.PRE_MATCH)
				return ERROR_COLOR + "You cannot add players once the match has begun!";
			
			String response = "";
			
			UhcPlayer pl = match.getPlayer(args[1]);
			
			if (!pl.isOnline())
				response += WARN_COLOR + "Warning: adding a player who is not currently online\n";

			
			
			if (config.isFFA()) {
				if (!match.addSoloParticipant(pl))
					return ERROR_COLOR + "Failed to add player";
				
				return response + OK_COLOR + "Player added";
			} else {
				if (args.length < 3)
					return ERROR_COLOR + "Please specify the team! /players add NAME TEAM";
				if (!match.addParticipant(pl, args[2]))
					return ERROR_COLOR + "Failed to add player " + args[1] + " to team " + args[2];
				
				return response + OK_COLOR + "Player added";
			}
				
		} else if (args[0].equalsIgnoreCase("addall")) {
			if (!config.isFFA())
				return ERROR_COLOR + "Cannot auto-add players in a team match";
			
			int added = 0;
			for (UhcPlayer pl : match.getOnlinePlayers()) {
				if (!pl.isSpectator())
					if (match.addSoloParticipant(pl)) added++;
			}
			if (added > 0)
				return "" + OK_COLOR + added + " player" + (added == 1? "" : "s") + " added";
			else
				return ERROR_COLOR + "No players to add!";
			
			
		} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rm")) {
			if (args.length < 2)
				return ERROR_COLOR + "Specify player to remove!";
			if (!match.removeParticipant(args[1]))
				return ERROR_COLOR + "Player could not be removed!";

			return OK_COLOR + "Player removed";
		} else if (args[0].equalsIgnoreCase("removeunseen") || args[0].equalsIgnoreCase("rmu")) {
			if (!config.isFFA())
				return ERROR_COLOR + "Cannot auto-remove players in a team match";
			
			ArrayList<String> playersToRemove = new ArrayList<String>();
			for (UhcParticipant up : match.getParticipants()) {
				if (!up.getPlayer().isSeen()) playersToRemove.add(up.getName());
			}
			
			if (playersToRemove.size() > 0) {
				String response = OK_COLOR + "Unseen players removed: ";
				for (String name : playersToRemove) {
					match.removeParticipant(name);
					response += name + " ";
				}
				return response;
			} else {
				return ERROR_COLOR + "No players to remove";
			}
		} else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("ls")) {
			Map<String, ArrayList<String>> players = new HashMap<String, ArrayList<String>>();
			
			players.put("Active participants", new ArrayList<String>());
			players.put("Inactive participants", new ArrayList<String>());
			players.put("Administrators", new ArrayList<String>());
			players.put("Spectators", new ArrayList<String>());
			players.put("Others", new ArrayList<String>());
			
			for (UhcPlayer p : match.getOnlinePlayers()) {
				if (p.isActiveParticipant()) players.get("Active participants").add(p.getDisplayName());
				else if (p.isParticipant()) players.get("Inactive participants").add(p.getDisplayName());
				else if (p.isAdmin()) players.get("Administrators").add(p.getDisplayName());
				else if (p.isSpectator()) players.get("Spectators").add(p.getDisplayName());
				else players.get("Others").add(p.getDisplayName());
			}
			
			String response = match.getOnlinePlayers().size() + " players online:\n";
			for (Entry<String, ArrayList<String>> e : players.entrySet()) {
				if (e.getValue().size() > 0) {
					response += OK_COLOR + "  " + e.getKey() + "(" + e.getValue().size() + "):" + SIDE_COLOR;
					for (String name : e.getValue())
						response += " " + name;
					response += "\n";
				}
			}
			return response;

		}
		return null;
	}

}
