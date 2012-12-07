package com.martinbrook.tesseractuhc.command;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcSpectator;

public class InteractCommand extends UhcCommandExecutor {

	public InteractCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {

		boolean interacting = !sender.isInteracting();
		sender.setInteracting(interacting);
		
		return OK_COLOR + "Interaction has been " + (interacting ? "enabled" : "disabled") + ".";
	}

}
