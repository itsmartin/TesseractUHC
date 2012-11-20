package com.martinbrook.tesseractuhc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.martinbrook.tesseractuhc.TesseractUHC;

public class FileUtils {

	private FileUtils() { }

	/**
	 * Read in a file from the plugin data folder, and return its contents as an ArrayList<String> 
	 * 
	 * @param filename
	 * @return
	 */
	public static ArrayList<String> readFile(String filename) {
		File fChat = getPluginDataFile(filename, true);
		
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
	 * Initialise the data folder for the UhcTools plugin.
	 *
	 * @return The data folder for the plugin, or null if it couldn't be created
	 */
	public static File getPluginFolder() {
	    File pluginFolder = TesseractUHC.getInstance().getDataFolder();
	    if (!pluginFolder.isDirectory()){
	        if (!pluginFolder.mkdirs()) {
	            // failed to create the non existent directory, so failed
	            return null;
	        }
	    }
	    return pluginFolder;
	}

	/**
	 * Get the folder for the current (main) world
	 * 
	 * @return The world folder
	 */
	public static File getWorldFolder() {
		File worldFolder = TesseractUHC.getInstance().getMatch().getStartingWorld().getWorldFolder();
		if (worldFolder.isDirectory()) return worldFolder;
		else return null;
	}

	/**
	 * Retrieve a File from the plugin data folder.
	 *
	 * @param filename Filename to retrieve
	 * @param mustAlreadyExist true if the file should already exist
	 * @return The specified data file, or null if not found
	 */
	public static File getPluginDataFile(String filename, boolean mustAlreadyExist) {
		return getDataFile(getPluginFolder(), filename, mustAlreadyExist);
	}

	/**
	 * Retrieve a File from the world data folder.
	 *
	 * @param filename Filename to retrieve
	 * @param mustAlreadyExist true if the file should already exist
	 * @return The specified data file, or null if not found
	 */
	public static File getWorldDataFile(String filename, boolean mustAlreadyExist) {
		return getDataFile(getWorldFolder(), filename, mustAlreadyExist);
	}

	/**
	 * Retrieve a File from a specific folder.
	 * 
	 * @param folder The folder to look for the file in
	 * @param filename Filename to retrieve
	 * @param mustAlreadyExist true if the file should already exist
	 * @return The specified data file, or null if not found
	 */
	public static File getDataFile(File folder, String filename, boolean mustAlreadyExist) {
	    if (folder != null) {
	        File file = new File(folder, filename);
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
