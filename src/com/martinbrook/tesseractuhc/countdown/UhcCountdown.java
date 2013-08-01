package com.martinbrook.tesseractuhc.countdown;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import com.martinbrook.tesseractuhc.UhcMatch;
import com.martinbrook.tesseractuhc.util.MatchUtils;

public abstract class UhcCountdown {

	protected long countdownLength;
	protected int task = -1;
	private Plugin plugin;
	protected UhcMatch match;
	private Boolean active;
	private Calendar startTime;
	private ArrayList<Long> announcements = new ArrayList<Long>();
	private int preWarnTime;
	private boolean preWarnDone = false;
	private int nearingTime;
	private boolean nearingDone = false;
	private boolean firstAnnouncementDone = false;
	private static long REFRESH_RATE = 5L;

	
	public UhcCountdown(long countdownLength, Plugin plugin, UhcMatch match) {
		this.startTime = Calendar.getInstance();
		this.countdownLength = countdownLength;
		this.plugin = plugin;
		this.match = match;
		this.active = true;
		
		// Build a list of announcements
		for (long i = (countdownLength - 1) / 60; i > 0; i--)
			announcements.add(i * 60);
		
		if (countdownLength > 45) announcements.add(45L);
		if (countdownLength > 30) announcements.add(30L);
		if (countdownLength > 15) announcements.add(15L);
		if (countdownLength > 5) announcements.add(5L);
		if (countdownLength > 4) announcements.add(4L);
		if (countdownLength > 3) announcements.add(3L);
		if (countdownLength > 2) announcements.add(2L);
		if (countdownLength > 1) announcements.add(1L);
		
		this.preWarnTime = 120;
		
		this.nearingTime = 90;
		
	
		this.task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				tick();
			}
		}, REFRESH_RATE, REFRESH_RATE);
	}
	
	protected abstract void preWarn();
	protected abstract void nearing();
	protected abstract void complete();
	protected abstract String getDescription();
	
	public Boolean cancel() {
		if (!this.active) return false; 
		plugin.getServer().getScheduler().cancelTask(task);
		this.active=false;
		return true;
	}
	
	private long getRemainingSeconds() {
		long remaining = countdownLength - MatchUtils.getDuration(startTime, Calendar.getInstance());
		return (remaining > 0? remaining : 0);
	}
	
	private void tick() {
		if (!this.active) return;
		
		long remainingSeconds = this.getRemainingSeconds();

		if (!firstAnnouncementDone) {
			firstAnnouncementDone = true;
			this.announce(countdownLength);
		}
		
		if (remainingSeconds <= 0) {
			this.active=false;
			this.complete();
			return;
		}
		

		if (!preWarnDone && remainingSeconds <= preWarnTime) {
			this.preWarnDone = true;
			this.preWarn();
		}
		
		if (!nearingDone && remainingSeconds <= nearingTime) {
			this.nearingDone = true;
			this.nearing();
		}
		
		while (announcements.size() > 0 && remainingSeconds <= announcements.get(0))
			this.announce(announcements.remove(0));
		
	}
	
	private void announce(long timeRemaining) {
		if (timeRemaining == 0) return;
		if (timeRemaining > 5) {
			long minutes = timeRemaining / 60;
			long seconds = timeRemaining % 60;
			
			broadcast(ChatColor.LIGHT_PURPLE + this.getDescription() + " in "
					 + (minutes > 0 ? minutes + " minute" + (minutes == 1? "":"s") : "")
					 + (minutes > 0 && seconds > 0 ? " and " : "")
					 + (seconds > 0 ? seconds + " second" + (seconds == 1? "":"s") : "")
					 );
		} else { 
			broadcast(ChatColor.LIGHT_PURPLE + "" + timeRemaining + "...");
		}
	}
	
	private void broadcast(String message) {
		plugin.getServer().broadcastMessage(message);
	}
	
	public Boolean isActive() {
		return this.active;
	}


}
