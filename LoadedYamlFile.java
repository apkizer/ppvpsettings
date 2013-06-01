package com.planetarypvp.pe.settings;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoadedYamlFile
{
	private String path;
	private YamlConfiguration config;
	
	public LoadedYamlFile(String path)
	{
		this.path = path;
		config = new YamlConfiguration();
		try
		{
			config.load(path);
		} catch (IOException
				| InvalidConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public YamlConfiguration getConfig()
	{
		return config;
	}
}
