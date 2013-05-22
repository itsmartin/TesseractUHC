package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;

public class MatchCountdown extends UhcCountdown {

	public MatchCountdown(int countdownLength, Plugin plugin, UhcMatch match) {
		super(countdownLength, plugin, match);
	}

	@Override
	protected void complete() {
		match.startMatch();
	}

	@Override
	protected String getDescription() {
		return "The match will begin";
	}

	@Override
	protected void nearing() {
		match.launchAll();
	}
	
	@Override
	protected void preWarn() {
		match.preWarn();
	}

}
