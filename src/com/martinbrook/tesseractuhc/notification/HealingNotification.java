package com.martinbrook.tesseractuhc.notification;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import com.martinbrook.tesseractuhc.UhcParticipant;

public class HealingNotification extends UhcNotification {
	private UhcParticipant player;
	private RegainReason cause;



	public HealingNotification(UhcParticipant player, RegainReason cause) {
		super();
		this.player = player;
		this.cause = cause;
	}


	@Override
	public String formatForPlayers() {
		if (cause == RegainReason.MAGIC)
			return ChatColor.AQUA + player.getName() + " gained health through magic";
		if (cause == RegainReason.MAGIC_REGEN)
			return ChatColor.AQUA + player.getName() + " regenerated health through magic";

		return ChatColor.AQUA + player.getName() + " mysteriously regained health";
	}
}
