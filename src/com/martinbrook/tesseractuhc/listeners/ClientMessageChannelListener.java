package com.martinbrook.tesseractuhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.martinbrook.tesseractuhc.TesseractUHC;
import com.martinbrook.tesseractuhc.UhcMatch;
import com.martinbrook.tesseractuhc.UhcPlayer;
import com.martinbrook.tesseractuhc.util.PluginChannelUtils;

public class ClientMessageChannelListener implements Listener,PluginMessageListener{
	
	private UhcMatch m;

	public ClientMessageChannelListener(UhcMatch m) { this.m = m; }

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {		
		// currently no message need to be received other than REGISTER (done in 'channelRegistration')
	}
	
	@EventHandler
	public void channelRegistration(PlayerRegisterChannelEvent e)
	{
		UhcPlayer pl = m.getExistingPlayer(e.getPlayer());
		
		//if there is no match or the player doesn't exist, don't bother
		if(m == null || pl == null)return;

		if (TesseractUHC.PLUGIN_CHANNEL.equals(e.getChannel()) && m != null)
		{
			pl.setAutoRefereeClientEnabled(true);
			
			// if this is a player, complain and force them to quit their team!
			if (pl.isActiveParticipant())
			{
				m.spectatorBroadcast(pl.getDisplayName() + ChatColor.DARK_GRAY + " attempted to log in with a modified client!");
				m.removeParticipant(pl.getName());
			}

			// update a spectator with the latest information regarding the match
			else PluginChannelUtils.updateSpectator(pl.getPlayer(),m);
		}
	}
	
	@EventHandler
	public void playerLogOut(PlayerQuitEvent e){
		//client mod is disabled for people leaving the server to make sure they can rejoin without it.
		UhcPlayer pl = m.getExistingPlayer(e.getPlayer());
		if (pl != null) pl.setAutoRefereeClientEnabled(false);
	}
}
