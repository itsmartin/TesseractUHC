package com.martinbrook.tesseractuhc.command;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcMatch;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public abstract class UhcCommandExecutor implements CommandExecutor {
	protected UhcMatch match;
	protected Server server;
	
	public static final ChatColor MAIN_COLOR = ChatColor.GREEN, SIDE_COLOR = ChatColor.GOLD, OK_COLOR = ChatColor.GREEN, WARN_COLOR = ChatColor.LIGHT_PURPLE, ERROR_COLOR = ChatColor.RED,
			DECISION_COLOR = ChatColor.GOLD, ALERT_COLOR = ChatColor.GREEN;
	
	public UhcCommandExecutor(TesseractUHC plugin) {
		this.match = plugin.getMatch();
		this.server = plugin.getServer();
		
	}

	public final boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		String response;
		if (commandSender instanceof Player) {
			UhcPlayer pl = match.getPlayer((Player) commandSender);
			if (pl.isSpectator()) {
				if (pl.isAdmin()) {
					response = runAsAdmin(pl.getSpectator(), args);
				} else {
					response = runAsSpectator(pl.getSpectator(), args);
				}
			} else {
				response = runAsPlayer(pl, args);
			}
		} else if (commandSender instanceof ConsoleCommandSender) {
			response = runAsConsole((ConsoleCommandSender) commandSender, args);
		} else return false;
		
		if (response != null) commandSender.sendMessage(response);
		return true;
	}
	
	protected abstract String runAsAdmin(UhcSpectator sender, String[] args);
	
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return (ChatColor.GOLD + "You are not permitted to use that command!");
	}
	protected String runAsPlayer(UhcPlayer sender, String[] args) {
		return (ChatColor.GOLD + "You are not permitted to use that command!");
	}
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return (ChatColor.GOLD + "That command is not available from the console.");
	}
	
	
	
	

}
