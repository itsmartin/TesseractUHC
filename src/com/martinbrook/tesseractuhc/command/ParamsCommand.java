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
		if (config.isUHC())
			response += "   " + config.formatParameter("uhc") + "\n";
		
		response += "   " + config.formatParameter("ffa") + "\n";
		
		if (config.getNopvp() > 0)
			response += "   " + config.formatParameter("nopvp") + "\n";
		
		if (config.getWorldBorder() > 0)
			response += "   " + config.formatParameter("worldborder") + "\n";
		
		if (config.getKillerBonus() != null)
			response += "   " + config.formatParameter("killerbonus") + "\n";
		
		if (config.getMiningFatigueDiamond() > 0 || config.getMiningFatigueGold() > 0)
			response += "   " + config.formatParameter("miningfatigue") + "\n";
		
		if (config.isHardStone())
			response += "   " + config.formatParameter("hardstone") + "\n";
		
		if (config.isDropHeads())
			response += "   " + config.formatParameter("dropheads") + "\n";
		
		return response;

	}
}
