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
		return this.run(true);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.run(true);
	}
	
	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return this.run(false);
	}

	@Override
	protected String runAsPlayer(UhcPlayer sender, String[] args) {
		return this.run(false);
	}
	
	private String run(boolean adminInfo) {
		String response = ChatColor.GOLD + "Gameplay summary:\n";
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
		
		if (config.isNoSkeletons())
			response += "   " + config.formatParameter("noskeletons") + "\n";
		
		if (!config.isWeather())
			response += "   " + config.formatParameter("weather") + "\n";
		
		if (adminInfo)
			response += ChatColor.AQUA + "For a full list of parameters, use " + ChatColor.GOLD + "/uhc params";
		
		return response;

	}
}
