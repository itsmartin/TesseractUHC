package com.martinbrook.tesseractuhc.notification;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.martinbrook.tesseractuhc.TesseractUHC;

public class PlayerMessageNotification extends UhcNotification {

	private Player sender;
	private String message;
	
	public PlayerMessageNotification(Player sender, String message) {
		super();
		this.sender=sender;
		this.message=message;
	}

	@Override
	public String formatForPlayers() {
		return null;
	}
	
	@Override
	public String formatForStreamers() {
		return TesseractUHC.ALERT_COLOR + "[N]" + ChatColor.WHITE + " <" + sender.getDisplayName() + "> " + TesseractUHC.ALERT_COLOR + message;
	}

}
