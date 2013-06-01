package com.planetarypvp.pe.settings;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class YamlFile 
{
	private String path;
	private String clazz;
	private YamlConfiguration config;
	
	public YamlFile(String path, String qualifiedClassName)
	{
		this.path = path;
		config = new YamlConfiguration();
		this.clazz = qualifiedClassName;
		config.set("class", clazz);
	}
	
	public YamlConfiguration getConfig()
	{
		return config;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void save()
	{
		try {
			config.save(path + ".yml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
