package com.martinbrook.uhctools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class UhcUtil {

	private UhcUtil() { }
	
	public static String formatDuration(Calendar t1, Calendar t2, boolean precise) {
		// Get duration in seconds
		int d = (int) (t2.getTimeInMillis() - t1.getTimeInMillis()) / 1000;
		
		if (precise) {
			int seconds = d % 60;
			d = d / 60;
			int minutes = d % 60;
			int hours = d / 60;
			
			// The string
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			int minutes = (d + 30) / 60;
			return minutes + " minute" + (minutes == 1 ? "s" : "");
			
		}
		
	}

	public static ArrayList<String> readFile(String filename) {
		File fChat = getDataFile(filename, true);
		
		if (fChat == null) return null;
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(fChat);
			BufferedReader in = new BufferedReader(fr);
			String s = in.readLine();

			while (s != null) {
				lines.add(s);
				s = in.readLine();
			}

			in.close();
			fr.close();
			return lines;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Initialise the data directory for the UhcTools plugin.
	 *
	 * @return true if the directory has been created or already exists.
	 */
	private static boolean createDataDirectory() {
	    File file = UhcTools.getInstance().getDataFolder();
	    if (!file.isDirectory()){
	        if (!file.mkdirs()) {
	            // failed to create the non existent directory, so failed
	            return false;
	        }
	    }
	    return true;
	}
	 
	/**
	 * Retrieve a File description of a data file for your plugin.
	 * This file will be looked for in the data directory of your plugin, wherever that is.
	 * There is no need to specify the data directory in the filename such as "plugin/datafile.dat"
	 * Instead, specify only "datafile.dat"
	 *
	 * @param filename The name of the file to retrieve.
	 * @param mustAlreadyExist True if the file must already exist on the filesystem.
	 *
	 * @return A File descriptor to the specified data file, or null if there were any issues.
	 */
	public static File getDataFile(String filename, boolean mustAlreadyExist) {
	    if (createDataDirectory()) {
	        File file = new File(UhcTools.getInstance().getDataFolder(), filename);
	        if (mustAlreadyExist) {
	            if (file.exists()) {
	                return file;
	            }
	        } else {
	            return file;
	        }
	    }
	    return null;
	}
	
}
