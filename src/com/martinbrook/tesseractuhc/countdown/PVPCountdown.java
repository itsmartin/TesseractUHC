package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;

public class PVPCountdown extends UhcCountdown {

	public PVPCountdown(int countdownLength, Plugin plugin, UhcMatch match) {
		super(countdownLength, plugin, match);
	}

	@Override
	protected void nearing() {	}

	@Override
	protected void preWarn() { }

	@Override
	protected void complete() {
		match.setPVP(true);
		match.broadcast("PVP is now enabled!");

	}

	@Override
	protected String getDescription() {
		return "PVP will be enabled";
	}

}
