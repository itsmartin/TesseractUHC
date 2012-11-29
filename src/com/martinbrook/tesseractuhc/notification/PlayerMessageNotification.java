package com.martinbrook.tesseractuhc.notification;

import org.bukkit.ChatColor;
import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;

public class PlayerMessageNotification extends UhcNotification {

	private UhcPlayer sender;
	private String message;
	
	public PlayerMessageNotification(UhcPlayer sender, String message) {
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
