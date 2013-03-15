package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;

public class FireworkCountdown extends UhcCountdown{

	public FireworkCountdown(int countdownLength, Plugin plugin, UhcMatch match) {
		super(countdownLength, plugin, match);
		
	}

	@Override
	protected void nearing() {
		return;
	}

	@Override
	protected void complete() {
		// TODO launch firework and reset countdown
		
		
	}

	@Override
	protected String getDescription() {
		return "All players suddenly launch a firework from there backside!";
		
	}

}
