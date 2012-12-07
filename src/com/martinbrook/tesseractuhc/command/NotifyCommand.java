package com.martinbrook.tesseractuhc.command;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.UhcSpectator;
import com.martinbrook.tesseractuhc.notification.PlayerMessageNotification;

public class NotifyCommand extends UhcCommandExecutor {

	public NotifyCommand(TesseractUHC plugin) {
		super(plugin);
	}

	@Override
	protected String runAsAdmin(UhcSpectator sender, String[] args) {
		return this.run(sender.getPlayer(),args);
	}

	@Override
	protected String runAsSpectator(UhcSpectator sender, String[] args) {
		return this.run(sender.getPlayer(),args);
	}

	@Override
	protected String runAsPlayer(UhcPlayer sender, String[] args) {
		return this.run(sender,args);
	}


	private String run(UhcPlayer sender, String[] args) {
		String s = "";
		if (args.length == 0)
			s = "no text";
		else {
			for (int i = 0; i < args.length; i++) {
				s += args[i] + " ";
			}
			s = s.substring(0, s.length() - 1);
		}

		match.sendSpectatorNotification(new PlayerMessageNotification(sender, s), sender.getLocation());

		return sender.isSpectator() ? null : OK_COLOR + "Message sent";

	}
}
