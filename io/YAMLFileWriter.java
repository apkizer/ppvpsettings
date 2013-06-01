package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;

public interface YAMLFileWriter
{
	/**
	 * Create specified directory if it does not exist and set focus inside specified directory.
	 * @param path
	 */
	public void directory(ArrayList<String> path);
	
	/**
	 * Creates files in current directory if it does not exist.
	 * @param name
	 */
	public void file(String name, String qualifiedClassName);
	
	/**
	 * Writes a YAML setting to specified file.
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public void setting(String fileName, String key, String value);
	
	//public String getDirectory();
	
	/**
	 * Writes a YAML list to specified file.
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public void settingList(String fileName, String key, String value);
	
	/**
	 * Writes a YAML list to specified file, with {count} elements.
	 * @param fileName
	 * @param key
	 * @param value
	 * @param count
	 */
	public void settingList(String fileName, String key, String value, int count);
	
	/**
	 * Set which preset the YAMLFileWriter will be writing to.
	 * @param preset
	 */
	public void setPreset(String preset);
	
	/**
	 * Save all files and directories.
	 */
	public void save();
}
