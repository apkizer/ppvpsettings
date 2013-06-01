package com.planetarypvp.pe.settings;

import java.io.File;
import java.util.ArrayList;

import com.planetarypvp.pe.settings.io.YAMLFileWriter;

public class Writer implements YAMLFileWriter
{
	private String presetPath;
	private String currentPreset;
	private String settingsDir;
	private File file;
	private ArrayList<YamlFile> yamlFiles;
	private String currentDir;

	public Writer(String path)
	{
		yamlFiles = new ArrayList<>();
		settingsDir = path;
		currentDir = path;
	}

	@Override
	public void setPreset(String preset)
	{
		this.currentPreset = preset;
		this.presetPath = settingsDir + File.separator + preset;
	}
	
	@Override
	public void directory(ArrayList<String> path)
	{
		currentDir = constructPath(presetPath, path);
		if (!isDir(currentDir))
			createDir(currentDir);
	}

	@Override
	public void file(String path, String qualifiedClassName)
	{
		YamlFile yamlFile = new YamlFile(currentDir.concat(path),
				qualifiedClassName);
		yamlFiles.add(yamlFile);
	}

	@Override
	public void setting(String fileName, String key, String value)
	{
		YamlFile yamlFile = null;
		yamlFile = findFile(fileName);
		yamlFile.getConfig().set(key, value);
	}

	private YamlFile findFile(String fileName)
	{
		YamlFile yamlFile = null;
		for (YamlFile y : yamlFiles)
		{
			if (y.getPath().equals(currentDir.concat(fileName)))
			{
				yamlFile = y;// TODO check, possible override if same path?
			} else
			{

			}
		}
		return yamlFile;
	}

	private boolean isDir(String dir)
	{
		file = new File(dir);
		return file.exists();
	}

	private void createDir(String path)
	{
		file = new File(path);
		file.mkdirs();
	}

	private String constructPath(String absolute, ArrayList<String> path)
	{
		return absolute.concat(File.separator).concat(constructPath(path));// TODO
																			// check
	}

	private String constructPath(ArrayList<String> path)
	{
		String ret = "";

		for (String string : path)
		{
			ret = ret.concat(string.concat(File.separator));
		}

		return ret;
	}

	@Override
	public void settingList(String fileName, String key, String value)
	{
		settingList(fileName, key, value, 3);
	}

	@Override
	public void settingList(String fileName, String key, String value, int count)
	{
		YamlFile yamlFile = findFile(fileName);
		String[] list = new String[count];
		for (int i = 0; i < count; i++)
		{
			list[i] = value;
		}
		yamlFile.getConfig().set(key, list);
	}

	@Override
	public void save()
	{
		for (YamlFile y : yamlFiles)
		{
			y.save();
		}
	}
}
