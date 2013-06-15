package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;


public class BorderCountdown extends UhcCountdown {
	private int newRadius;

	public BorderCountdown(int countdownLength, Plugin plugin, UhcMatch match, int newRadius) {
		super(countdownLength, plugin, match);
		this.newRadius = newRadius;
	}

	@Override
	protected void nearing() {
		match.sendBorderWarnings(newRadius);
	}

	@Override
	protected void preWarn() { }

	@Override
	protected void complete() {
		match.setWorldBorder(newRadius);
		match.broadcast(ChatColor.GOLD + "World border is now at +/- " + newRadius  + " x and z!");
	}

	@Override
	protected String getDescription() {
		return "World border will move to +/- " + newRadius + " x and z";
	}


}
