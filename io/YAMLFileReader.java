package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;

public interface YAMLFileReader
{
	/**
	 * Focuses on specified directory.
	 * @param directory
	 */
	public void directory(ArrayList<String> directory);
	
	/**
	 * Returns the fully qualified classname present in the -settings.yml file in this directory, if such file exists.
	 * @return
	 */
	public String readQualifiedClassName();
	
	/**
	 * Reads all settings from the first file in the current directory that contains the specified suffix in its name to the specified ConfigurableClassRepresentation. TODO ACCR is immutable
	 * @param name
	 * @param cCR
	 */
	public ActualConfigurableClassRepresentation readFile(/*String suffix, */ActualConfigurableClassRepresentation aCCR);//the accr will have to have been initialized with fully qualified class name, look in here for types etc.
	
	/**
	 * Returns an ArrayList representing all directories representing ConfigurableClassRepresentations in the current directory.
	 * @return
	 */
	public ArrayList<String> getCCRDirectories();
	
	public ArrayList<ArrayList<String>> getFullCCRDirectories();
	
	/**
	 * Returns an ArrayList representing all directories representing ActualListRepresentations in the current directory.
	 * 
	 * @return
	 */
	public ArrayList<String> getListDirectories();
	
	public void setPreset(String preset);

}
