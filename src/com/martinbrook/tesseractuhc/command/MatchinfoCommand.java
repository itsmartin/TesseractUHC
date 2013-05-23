package com.martinbrook.tesseractuhc.command;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class MatchinfoCommand extends UhcCommandExecutor {

	public MatchinfoCommand(TesseractUHC plugin) {
		super(plugin);
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

	@Override
	protected String runAsConsole(ConsoleCommandSender sender, String[] args) {
		return this.run();
	}

	private String run() {
		return ChatColor.AQUA + "This is " + ChatColor.ITALIC + match.getConfig().getMatchTitle() + "\n" 
				+ ChatColor.RESET + match.matchTimeAnnouncement(true) + "\n" + match.getPlayerStatusReport(); 

	}

}
