package com.martinbrook.tesseractuhc.command;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPOI;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.UhcTeam;
import com.martinbrook.tesseractuhc.startpoint.UhcStartPoint;

public class UhcCommand extends UhcCommandExecutor {

	public UhcCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		if (args.length<1)
			return ERROR_COLOR + "Please specify an action.";
		
		if ("startpoint".equalsIgnoreCase(args[0]) || "sp".equalsIgnoreCase(args[0])) {
			return cUhcStartpoint(sender, args);
		} else if ("poi".equalsIgnoreCase(args[0])) {
			return cUhcPoi(sender, args);
		} else if ("setbonus".equalsIgnoreCase(args[0])) {
			return cUhcSetbonus(sender);
		} else if ("getbonus".equalsIgnoreCase(args[0])) {
			return cUhcGetbonus(sender);
		}
		
		return this.run(args);
	}
	
	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return this.run(args);

	}
	
	private String run(String[] args) {
		if (args.length<1)
			return ERROR_COLOR + "Please specify an action.";
		
		if ("reset".equalsIgnoreCase(args[0])) {
			return cUhcReset(args);
		} else if ("save".equalsIgnoreCase(args[0])) {
			return cUhcSave(args);
		} else if ("starts".equalsIgnoreCase(args[0])) {
			return cUhcStarts();
		} else if ("pois".equalsIgnoreCase(args[0])) {
			return cUhcPois();
		} else if ("params".equalsIgnoreCase(args[0])) {
			return cUhcParams();
		} else if ("set".equalsIgnoreCase(args[0])) {
			return cUhcSet(args);
		} 
		
		return ERROR_COLOR + "Command not understood";
	}



	/**
	 * @param sender
	 * @return
	 */
	private String cUhcGetbonus(UhcSpectator sender) {
		sender.getPlayer().getPlayer().getEnderChest().setContents(match.getBonusChest());
		return OK_COLOR + "Bonus chest loaded into your ender chest";
	}

	/**
	 * @param sender
	 * @return
	 */
	private String cUhcSetbonus(UhcSpectator sender) {
		match.setBonusChest(sender.getPlayer().getPlayer().getEnderChest().getContents());
		return OK_COLOR + "Bonus chest saved from your ender chest";
	}


	/**
	 * @param args
	 * @return
	 */
	private String cUhcSet(String[] args) {
		if (args.length < 3)
			return ERROR_COLOR + "Invalid command";

		String parameter = args[1].toLowerCase();

		String value = "";
		for (int i = 2; i < args.length; i++) {
			value += args[i] + " ";
		}
		value = value.substring(0, value.length()-1);
		if (match.setParameter(parameter, value)) {
			return match.formatParameter(parameter);
		} else
			return ERROR_COLOR + "Unable to set value of " + parameter;
	}

	/**
	 * @return
	 */
	private String cUhcPois() {
		ArrayList<UhcPOI> pois = match.getPOIs();
		String response = "";
		for(int i = 0; i < pois.size(); i++) {
			response += (i + 1) + ": " + pois.get(i).getName() + " (" + pois.get(i).toString() + ")\n"; 
		}
		return response;
	}

	/**
	 * @return
	 */
	private String cUhcStarts() {
		HashMap<Integer, UhcStartPoint> startPoints = match.getStartPoints();
		if (startPoints.size()==0)
			return ERROR_COLOR + "There are no starts";

		String response = "";
		for (UhcStartPoint sp : startPoints.values()) {
			UhcTeam t = sp.getTeam();
			
			response += (sp.getNumber());
			
			if (t != null) response += " (" + t.getName() + ")";
			
			response += ": " + sp.getX() + "," + sp.getY() + "," + sp.getZ() + "\n";
		}
		return response;
	}

	/**
	 * @param args
	 * @return
	 */
	private String cUhcSave(String[] args) {
		if (args.length < 2 || "params".equalsIgnoreCase(args[1])) {
			match.saveMatchParameters();
			return OK_COLOR + "If no errors appear above, match parameters have been saved";
		} else if ("teams".equalsIgnoreCase(args[1]) || "players".equalsIgnoreCase(args[1])) {
			match.saveTeams();
			return OK_COLOR + "If no errors appear above, teams and players have been saved";
		}
		return ERROR_COLOR + "Argument not understood. Please use " + SIDE_COLOR + "/uhc save params" 
		+ ERROR_COLOR + " or " + SIDE_COLOR + "/uhc save teams";
	}

	/**
	 * @param args
	 */
	private String cUhcReset(String[] args) {
		if (args.length < 2 || "params".equalsIgnoreCase(args[1])) {
			match.resetMatchParameters();
			return OK_COLOR + "Match data reset to default values";
		} else if ("teams".equalsIgnoreCase(args[1]) || "players".equalsIgnoreCase(args[1])) {
			if (!match.clearTeams())
				return ERROR_COLOR + "Failed to clear teams and players";
			else
				return OK_COLOR + "Teams and players have been reset";
		}
		return ERROR_COLOR + "Argument not understood. Please use " + SIDE_COLOR + "/uhc reset params" 
				+ ERROR_COLOR + " or " + SIDE_COLOR + "/uhc reset teams";
	}

	/**
	 * @param sender
	 * @param args
	 * @return
	 */
	private String cUhcPoi(UhcSpectator sender, String[] args) {
		if (args.length<2) return ERROR_COLOR + "Please give a description/name";
		String name = "";
		for(int i = 1; i < args.length; i++) name += args[i] + " ";
		name = name.substring(0, name.length()-1);
		match.addPOI(sender.getLocation(), name);
		return OK_COLOR + "POI added at your current location";
	}

	/**
	 * @param sender
	 * @param args
	 * @return
	 */
	private String cUhcStartpoint(UhcSpectator sender, String[] args) {
		Location l = sender.getLocation();
		double x = l.getBlockX() + 0.5;
		double y = l.getBlockY();
		double z = l.getBlockZ() + 0.5;
		
		UhcStartPoint startPoint = match.addStartPoint(x, y, z, args.length < 2 || !("-n".equalsIgnoreCase(args[1])));
		
		return OK_COLOR + "Start point " + startPoint.getNumber() + " added at your current location";
	}
	
	
	private String cUhcParams() {
		String response = ChatColor.GOLD + "Match details:\n";
		response += ChatColor.RESET + "[uhc]           " + match.formatParameter("uhc") + "\n";
		response += ChatColor.RESET + "[ffa]           " + match.formatParameter("ffa") + "\n";
		response += ChatColor.RESET + "[nopvp]         " + match.formatParameter("nopvp") + "\n";
		response += ChatColor.RESET + "[killerbonus]   " + match.formatParameter("killerbonus") + "\n";
		response += ChatColor.RESET + "[miningfatigue] " + match.formatParameter("miningfatigue") + "\n";
		response += ChatColor.RESET + "[deathban]      " + match.formatParameter("deathban") + "\n";
		response += ChatColor.RESET + "[autospectate]  " + match.formatParameter("autospectate") + "\n";
		response += ChatColor.RESET + "[nolatecomers]  " + match.formatParameter("nolatecomers") + "\n";
		
		return response;
	}

}
