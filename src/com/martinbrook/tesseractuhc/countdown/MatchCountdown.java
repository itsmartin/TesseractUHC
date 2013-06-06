package com.martinbrook.tesseractuhc.countdown;

import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcMatch;
import com.martinbrook.tesseractuhc.util.PluginChannelUtils;

public class MatchCountdown extends UhcCountdown {

	public MatchCountdown(int countdownLength, Plugin plugin, UhcMatch match) {
		super(countdownLength, plugin, match);
		PluginChannelUtils.messageSpectators("match",TesseractUHC.PLUGIN_CHANNEL_WORLD,"countdown",Integer.toString(countdownLength));
	}

	@Override
	protected void complete() {
		match.startMatch();
		PluginChannelUtils.messageSpectators("match",TesseractUHC.PLUGIN_CHANNEL_WORLD,"start");
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
