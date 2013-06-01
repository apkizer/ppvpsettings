package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;

import com.planetarypvp.pe.settings.ConfigurableClass;

public class ConfigurableClassRepresentation
{
	private ArrayList<ConfigurableMethodRepresentation> settings;
	private ConfigurableClassRepresentation owner;// TODO
	private String className;
	private String qualifiedClassName;

	public ConfigurableClassRepresentation(String className,
			String qualifiedClassName)
	{
		settings = new ArrayList<>();
		this.className = className;
		this.qualifiedClassName = qualifiedClassName;
	}

	public ArrayList<ConfigurableMethodRepresentation> getSettings()
	{
		return settings;
	}

	public void addSetting(String key, ConfigurableClassRepresentation cCR)
	{
		settings.add(new ConfigurableMethodRepresentation(key, cCR));
	}

	public void addSetting(String key, Class<?> clazz)
	{
		settings.add(new ConfigurableMethodRepresentation(key, clazz));
	}

	public void addSetting(String key, ListRepresentation list)
	{
		settings.add(new ConfigurableMethodRepresentation(key, list));
	}
	
	/*public void addSetting(String key, ActualListRepresentation actualList)
	{
		settings.add(new ConfigurableMethodRepresentation(key, actualList));
	}*/
	
	public String getClassName()
	{
		return className;
	}

	public String getQualifiedClassName()
	{
		return qualifiedClassName;
	}

}
