package com.martinbrook.tesseractuhc.command;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class ParamsCommand extends UhcCommandExecutor {

	public ParamsCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return this.run();
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.run();
	}
	
	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return this.run();
	}

	@Override
	protected String runAsPlayer(UhcPlayer sender, String[] args) {
		return this.run();
	}
	
	private String run() {
		String response = ChatColor.GOLD + "Match details:\n";
		response += "   " + match.formatParameter("uhc") + "\n";
		response += "   " + match.formatParameter("ffa") + "\n";
		response += "   " + match.formatParameter("nopvp") + "\n";
		response += "   " + match.formatParameter("killerbonus") + "\n";
		response += "   " + match.formatParameter("miningfatigue") + "\n";
		
		return response;

	}
}
