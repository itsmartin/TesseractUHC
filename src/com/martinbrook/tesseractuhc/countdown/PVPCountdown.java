package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;

public class PVPCountdown extends UhcCountdown {

	Boolean newValue;
	
	public PVPCountdown(int countdownLength, Plugin plugin, UhcMatch match, Boolean newValue) {
		super(countdownLength, plugin, match);
		this.newValue = newValue;
	}

	@Override
	protected void nearing() {	}

	@Override
	protected void preWarn() { }

	@Override
	protected void complete() {
		match.setPVP(newValue);
		match.resetPVPCountdown();
		match.broadcast("PVP is now " + (newValue ? "enabled" : "disabled") + "!");

	}

	@Override
	protected String getDescription() {
		return "PVP will be " + (newValue ? "enabled" : "disabled");
	}

}
