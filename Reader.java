package com.planetarypvp.pe.settings;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.planetarypvp.pe.settings.io.ActualConfigurableClassRepresentation;
import com.planetarypvp.pe.settings.io.ActualListRepresentation;
import com.planetarypvp.pe.settings.io.ConfigurableClassRepresentation;
import com.planetarypvp.pe.settings.io.ListRepresentation;
import com.planetarypvp.pe.settings.io.Utils;
import com.planetarypvp.pe.settings.io.YAMLFileReader;

public class Reader implements YAMLFileReader
{
	private String presetPath;
	private String currentPreset;
	private String settingsDir;
	private File currentDirFile;
	private File file;
	private ArrayList<LoadedYamlFile> loadedYamlFiles;
	private String currentDir;
	private ArrayList<String> currentDirectory = new ArrayList<>();
	
	public Reader(String path)
	{
		loadedYamlFiles = new ArrayList<>();
		settingsDir = path;
		currentDir = path;
	}
	
	public void setPreset(String preset)
	{
		this.currentPreset = preset;
		this.presetPath = settingsDir + File.separator + preset;
	}

	@Override
	public void directory(ArrayList<String> path)
	{
		currentDirectory = path;
		currentDir = constructPath(presetPath, path);
		currentDirFile = new File(currentDir/* + File.separator*/);
		//System.out.println("reader switched dir to " + currentDirFile.getAbsolutePath());
	}

	@Override
	public String readQualifiedClassName()
	{
		if(currentDirFile == null)
		{
			//System.out.println("currentDirFile is null");
		}
		else if(currentDirFile.listFiles() == null)
		{
			//System.out.println("currentDirFile " + currentDirFile.getAbsolutePath() + " has no files");
		}
		File[] files = currentDirFile.listFiles();
		//System.out.println("(2) files array size = " + files.length);
		File settingsFile = files[0];//There should only be one file in each directory, so just pick the first.
		LoadedYamlFile yamlFile = new LoadedYamlFile(settingsFile.getAbsolutePath());
		//System.out.println("Attempting to get qualified class name from " + settingsFile.getAbsolutePath());
		//System.out.println("Qualified class name is " + yamlFile.getConfig().getString("class"));
		return yamlFile.getConfig().getString("class");
	}
	


	@Override
	public ActualConfigurableClassRepresentation readFile(/*String suffix, */ActualConfigurableClassRepresentation aCCR)//TODO get rid of suffix
	{
		File[] files = currentDirFile.listFiles();
		File settingsFile = files[0];//There should only be one file in each directory, so just pick the first.
		LoadedYamlFile yamlFile = new LoadedYamlFile(settingsFile.getAbsolutePath());
		loadedYamlFiles.add(yamlFile);//TODO should include?
		
		Class clazz = null;
		Object object = null;
		try
		{
			//System.out.println("qualifiedclassnameis " + aCCR.getQualifiedClassName());
			clazz = Class.forName(aCCR.getQualifiedClassName());
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for(Method m : clazz.getMethods())
		{
			if(m.isAnnotationPresent(Configurable.class))
			{
				String key = m.getName().substring(3);//.toLowerCase();
					System.out.println("Setting " + m.getName() + " to " + yamlFile.getConfig().getString(key));
					System.out.println("method parameter is " + m.getParameterTypes()[0].getName());
					if(yamlFile.getConfig().isBoolean(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getBoolean(key));
					}
					else if(yamlFile.getConfig().isInt(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getInt(key));
					}
					else if(yamlFile.getConfig().isDouble(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getDouble(key));
					}
					else if(yamlFile.getConfig().isString(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getString(key));
					}
					else
					{
						System.out.println("unknown value type for reader!");
					}
			}
		}*/
		
		for (Method method : clazz.getMethods())
		{
			if (method.isAnnotationPresent(Configurable.class))
			{
				String key;

				if (method.getAnnotation(Configurable.class).value().equals(""))
				{
					key = method.getName().substring(3);
				} else
				{
					key = method.getAnnotation(Configurable.class).value();
				}

				if (!(method.getAnnotation(Configurable.class).listType() == Null.class))
				{
					if (ConfigurableClass.class.isAssignableFrom(method.getAnnotation(Configurable.class).listType()))
					{
						//not here
					} 
					else
					{
						ActualListRepresentation aLR = new ActualListRepresentation(Utils.getActualValueType(method.getAnnotation(Configurable.class).listType()));
						if(yamlFile.getConfig().isList(key))
						{
							switch(aLR.getActualValueType())
							{
							case INT:
								for(Integer i : (ArrayList<Integer>)yamlFile.getConfig().getList(key))
								{
									aLR.addInt(i);
								}
								break;
							case STR:
								for(String s : (ArrayList<String>)yamlFile.getConfig().getList(key))
								{
									aLR.addStr(s);
								}
								break;
							case DOUB:
								for(Double d : (ArrayList<Double>)yamlFile.getConfig().getList(key))
								{
									aLR.addDoub(d);
								}
								break;
							case BOOL:
								for(Boolean b : (ArrayList<Boolean>)yamlFile.getConfig().getList(key))
								{
									aLR.addBool(b);
								}
								break;
							default:
								//error
								break;
							}
						}
						else
						{
							//error
						}
						aCCR.addSetting(key, aLR);
					}
				} 
				else if (Utils.isEndpointType(method.getParameterTypes()[0]))
				{
					if(yamlFile.getConfig().isBoolean(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getBoolean(key));
					}
					else if(yamlFile.getConfig().isInt(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getInt(key));
					}
					else if(yamlFile.getConfig().isDouble(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getDouble(key));
					}
					else if(yamlFile.getConfig().isString(key))
					{
						aCCR.addSetting(key, yamlFile.getConfig().getString(key));
					}
					else
					{
						System.out.println("unknown value type for reader!");
						System.out.println("method is" + method.getName());
					}
				}
				else if (ConfigurableClass.class.isAssignableFrom(method.getParameterTypes()[0]))
				{
					//not here
				}
				else{}	
			}
		}

		return aCCR;
	}

	@Override
	public ArrayList<String> getCCRDirectories()//TODO check
	{
		//System.out.println("getCCRDirectories called");
		File[] files = currentDirFile.listFiles();
		ArrayList<String> names = new ArrayList<>();
		//System.out.println("file array size is " + files.length);
		for(File f : files)
		{
			//System.out.println("file in directory: " + f.getName());
			if(f.getName().contains("(s)") || f.getName().contains("settings"))//TODO change from settings string to suffix variable
			{
				//break;
			}
			else
			{
				//System.out.println("ccr directory detected, name = " + f.getName());
				names.add(f.getName());
			}
		}
		return names;
	}

	@Override
	public ArrayList<ArrayList<String>> getFullCCRDirectories()
	{
		ArrayList<ArrayList<String>> fullDirs = new ArrayList<>();
		for(String string : getCCRDirectories())
		{
			fullDirs.add(Utils.directory(currentDirectory, string));//TODO check
			//System.out.println("(b) currentDir is " + currentDirectory);
		}
		return fullDirs;
	}

	@Override
	public ArrayList<String> getListDirectories()
	{
		File[] files = currentDirFile.listFiles();
		ArrayList<String> names = new ArrayList<>();
		for(File f : files)
		{
			if(f.getName().contains("(s)"))//TODO change from (s) string to variable
			{
				names.add(f.getName());
			}
		}
		return names;
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


}
