package com.martinbrook.tesseractuhc.event;

import com.martinbrook.tesseractuhc.UhcMatch;

public class UhcEventFactory {

	public static UhcEvent newEvent(String eventString, UhcMatch match) {
		long time;
		String eventType;
		int param;
		
		String[] eventData = eventString.split(",",3);
		
		if (eventData.length < 3) return null;
		
		try {
			time = Long.parseLong(eventData[0]);
			eventType = eventData[1];
			param = Integer.parseInt(eventData[2]);
		} catch (NumberFormatException e) {
			return null;
		}
		
		if (eventType.equalsIgnoreCase("border")) {
			return new BorderEvent(time, match, param);
		} else if (eventType.equalsIgnoreCase("pvp")) {
			return new PVPEvent(time, match, param);
		} else if (eventType.equalsIgnoreCase("permaday")) {
			return new PermadayEvent(time, match, param);
		} else {
			return null;
		}
	}

}
