package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;

public class PermadayCountdown extends UhcCountdown {

	Boolean newValue;
	
	public PermadayCountdown(int countdownLength, Plugin plugin, UhcMatch match, Boolean newValue) {
		super(countdownLength, plugin, match);
		this.newValue = newValue;
	}

	@Override
	protected void nearing() {	}

	@Override
	protected void preWarn() { }

	@Override
	protected void complete() {
		match.setPermaday(newValue);
		match.resetPermadayCountdown();
		match.broadcast("Permaday is now " + (newValue ? "enabled" : "disabled") + "!");

	}

	@Override
	protected String getDescription() {
		return "Permaday will be " + (newValue ? "enabled" : "disabled");
	}

}
