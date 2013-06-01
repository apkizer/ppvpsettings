package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;

public class ActualConfigurableClassRepresentation
{
	private ArrayList<ActualConfigurableMethodRepresentation> settings;
	private String qualifiedClassName;
	
	public ActualConfigurableClassRepresentation(String qualifiedClassName)
	{
		this.qualifiedClassName = qualifiedClassName;
		settings = new ArrayList<>();
	}
	
	public void addSetting(String key, ActualListRepresentation aLR)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, aLR));
	}
	
	public void addSetting(String key, ActualConfigurableClassRepresentation aCCR)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, aCCR));
	}
	
	public void addSetting(String key, int value)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, value));
	}
	
	public void addSetting(String key, String value)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, value));
	}
	
	public void addSetting(String key, double value)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, value));
	}
	
	public void addSetting(String key, boolean value)
	{
		settings.add(new ActualConfigurableMethodRepresentation(key, value));
	}
	
	public ActualConfigurableMethodRepresentation getSetting(String key)
	{
		//System.out.println("ACCR searching for key: " + key);
		for(ActualConfigurableMethodRepresentation aCMR : settings)
		{
			if(aCMR.getKey().equals(key))
			{
				return aCMR;
			}
		}
		//System.out.println("Could not find key: " + key + " in this ACCR: " + this.getQualifiedClassName());
		return null;
	}
	
	public ArrayList<ActualConfigurableMethodRepresentation> getSettings()
	{
		return settings;
	}
	
	public String getQualifiedClassName()
	{
		return qualifiedClassName;
	}
}
