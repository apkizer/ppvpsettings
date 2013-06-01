package com.planetarypvp.pe.settings.io;

import java.io.File;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import com.planetarypvp.pe.settings.Configurable;
import com.planetarypvp.pe.settings.ConfigurableClass;
import com.planetarypvp.pe.settings.Null;
import com.planetarypvp.pe.settings.Reader;
import com.planetarypvp.pe.settings.Writer;

public class SettingsIO
{
	private String settingsDir;
	private ConfigurableClassRepresentation root;
	private ActualConfigurableClassRepresentation actualRoot;
	private int statNumSettings = 0;
	private YAMLFileWriter writer;
	private YAMLFileReader reader;//TODO instantiate
	private String currentPreset;
	
	private ActualConfigurableClassRepresentation currentACCR;//TODO for apply settings.

	private int savedListLength = 3;
	private String yamlFileSuffix = "-settings";
	private String listSuffix = "(s)";

	/**
	 * Constructs a new SettingsSaver for the specified plugin data directory.
	 * 
	 * @param dataDir
	 *            The plugin's data directory, usually obtained through
	 *            plugin.getDataFolder().
	 */
	public SettingsIO(String dataDir)
	{
		File settingsDir = new File(dataDir + File.separator + "settings");
		if (!(settingsDir.exists()))
		{
			settingsDir.mkdirs();
		}
		this.settingsDir = settingsDir.getAbsolutePath();
		writer = new Writer(this.settingsDir);
		reader = new Reader(this.settingsDir);
	}

	/**
	 * Sets the YamlFileWriter for this SettinsgSaver.
	 * 
	 * @param writer
	 *            The YamlFileWriter instance.
	 */
	public void setYAMLFileWriter(YAMLFileWriter writer)
	{
		this.writer = writer;
	}

	/**
	 * Processes the specified ConfigurableClass instance.
	 * 
	 * @param presetable
	 *            A ConfigurableClass.
	 */
	public void createSettings(ConfigurableClass presetable)
	{
		root = process(presetable.getClass());
	}

	/**
	 * Saves the specified ConfigurableClass under the specified preset.
	 * 
	 * @param presetable
	 *            A ConfigurableClass.
	 * @param preset
	 *            The preset name under which to save the settings.
	 */
	public void saveSettings(ConfigurableClass presetable, String preset)
	{
		writer.setPreset(preset);
		createSettings(presetable);
		save(root.getClassName(), root, new ArrayList<String>());
		writer.save();
	}
	
	public void loadSettings(String preset)
	{
		reader.setPreset(preset);
		//ArrayList<String> dir = new ArrayList<>();
		//dir.add(preset);
		actualRoot = load(new ArrayList<String>());		
	}
	
	//TODO for testing, delete:
	public ActualConfigurableClassRepresentation getRoot()
	{
		return actualRoot;
	}
	
	public Object applySettings()
	{
		currentACCR = actualRoot;
		try
		{
			return apply();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Object apply() throws Exception
	{
		//System.out.println("Starting apply, currentACCR = " + currentACCR.getQualifiedClassName());
		Class configurableClass = Class.forName(currentACCR.getQualifiedClassName());// = Class.forName(qualifiedClassName);
		
		Object object = configurableClass.newInstance();
	
		for (Method method : configurableClass.getMethods())
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
						
						ArrayList<Object> list = new ArrayList<>();
						ActualConfigurableClassRepresentation temp = currentACCR;
						
						for(ActualConfigurableClassRepresentation aCCR : currentACCR.getSetting(key).getList().getACCRs())
						{
							currentACCR = aCCR;
							list.add(apply());
						}
						
						method.invoke(object, method.getParameterTypes()[0].cast(list));
						
						currentACCR = temp;
						
					} else
					{
						//System.out.println("(apply) looking for key " + key);
						//if(currentACCR.getSetting(key) == null)
						//{
							//System.out.println("(apply) primitive list is null");
							//System.out.println("(apply) current accr is " + currentACCR.getQualifiedClassName());
						//} 
						switch(currentACCR.getSetting(key).getList().getActualValueType())
						{
						case INT:
							method.invoke(object, currentACCR.getSetting(key).getList().getInts());
							break;
						case STR:
							method.invoke(object, currentACCR.getSetting(key).getList().getStrings());
							break;
						case DOUB:
							method.invoke(object, currentACCR.getSetting(key).getList().getDoubles());
							break;
						case BOOL:
							method.invoke(object, currentACCR.getSetting(key).getList().getBooleans());
							break;
						default:
							System.out.println("should not have to worry about accr or list");
							break;
						}
					}
				} 
				else if (Utils.isEndpointType(method.getParameterTypes()[0]))
				{
					//if(currentACCR.getSetting(key) == null)
					//{
						//System.out.println("(apply) primitive currentACCR.getSetting(key) is null");
						//System.out.println("(apply) current accr is " + currentACCR.getQualifiedClassName());
						//System.out.println("(apply) primitive key is " + key);
					//}
					
					switch(currentACCR.getSetting(key).getActualValueType())
					{
					case INT:
						method.invoke(object, currentACCR.getSetting(key).getInt());
						break;
					case STR:
						method.invoke(object, currentACCR.getSetting(key).getString());
						break;
					case DOUB:
						method.invoke(object, currentACCR.getSetting(key).getDouble());
						break;
					case BOOL:
						method.invoke(object, currentACCR.getSetting(key).getBoolean());
						break;
					default:
						System.out.println("should not have to worry about accr or list because should be endpoint");
						break;
					}
					
				} 
				else if (ConfigurableClass.class.isAssignableFrom(method.getParameterTypes()[0]))
				{
					ActualConfigurableClassRepresentation temp = currentACCR;
					currentACCR = currentACCR.getSetting(key).getACCR();
					method.invoke(object, apply());
					currentACCR = temp;
				} 
				else System.out.println("(apply) Invalid parameter type in method marked as configurable. Class: "
									+ method.getClass().getName()
									+ "Method: "
									+ method.getName()
									+ ". Parameter type: "
									+ method.getParameterTypes()[0]);
			}
		}
		return object;
	}
	
	
	private ActualConfigurableClassRepresentation load(ArrayList<String> directory)
	{
		reader.directory(directory);
		
		ActualConfigurableClassRepresentation aCCR = new ActualConfigurableClassRepresentation(reader.readQualifiedClassName());
		
		aCCR = reader.readFile(aCCR);
		
		for(String dir : reader.getCCRDirectories())
		{
			//System.out.println("(load) adding setting key = " + dir);
			aCCR.addSetting(dir, load(Utils.directory(directory, dir)));//check key, possibly not just directory name
		}
		
		for(String listDir : reader.getListDirectories())
		{
			ActualListRepresentation actualList = new ActualListRepresentation(ActualValueType.ACCR);
			reader.directory(Utils.directory(directory, listDir));
			for(ArrayList<String> dir : reader.getFullCCRDirectories())
			{
				//System.out.println("(a) loading directory " + dir.toString());
				actualList.addConfigurableClassRepresentation(load(dir));//check if this is the right directory
			}
			//System.out.println("(apply/load) assumed list dir = " + listDir);
			//System.out.println("(apply/load) assumed list dir = " + listDir.replace("(s)", ""));
			aCCR.addSetting(listDir.replace("(s)", ""), actualList);//again, check if list dir is the actual key name. It probably isn't!
		}
		
		return aCCR;
		
	}

	private void save(String settingsFileName,
			ConfigurableClassRepresentation cCR, ArrayList<String> directory)
	{
		writer.directory(directory);
		String settings = settingsFileName.toLowerCase() + yamlFileSuffix;
		writer.file(settings, cCR.getQualifiedClassName());

		for (ConfigurableMethodRepresentation cMR : (cCR.getSettings()))
		{
			if (cMR.getValueType().equals(ValueType.LIST))
			{
				if (cMR.getList().getValueType().equals(ValueType.PRIMITIVE))
				{
					writer.directory(directory);
					writer.settingList(settings, cMR.getKey(), cMR.getList()
							.getPrimitive().getName(), savedListLength);
				} else if (cMR.getList().getValueType().equals(ValueType.CCR))
				{
					ArrayList<String> listDir = Utils.directory(directory,
							cMR.getKey() + listSuffix);
					writer.directory(listDir);

					for (int i = 1; i < (savedListLength + 1); i++)
					{
						save(cMR.getKey() + i, cMR.getList().getCCR(),
								Utils.directory(listDir, cMR.getKey() + i + ""));
					}
				} else
				{

				}
			} else if (cMR.getValueType().equals(ValueType.CCR))
			{
				save(cMR.getKey(), cMR.getCCR(),
						Utils.directory(directory, cMR.getKey()));
			} else if (cMR.getValueType().equals(ValueType.PRIMITIVE))
			{
				writer.directory(directory);
				writer.setting(settings, cMR.getKey(), cMR.getPrimitive()
						.getName());
			} else
			{

			}
		}
	}

	public void printSettings(ConfigurableClass configurableClass,
			String presetName)
	{
		System.out.println("Settings tree for "
				+ configurableClass.getClass().getName() + " (\"" + presetName
				+ "\")\n");
		createSettings(configurableClass);
		print(root.getSettings(), 0);
		System.out.println("\n" + statNumSettings + " settings");
	}
	
	public void printLoadedSettings()
	{
		System.out.println("LoadedSettings tree");
		printLoaded(actualRoot.getSettings(), 0);
	}
	
	private void printLoaded(ArrayList<?> settings, int dashes)
	{
		String dashesString = "";

		for (int i = 0; i < dashes; i++)
		{
			dashesString += "-";
		}

		for (ActualConfigurableMethodRepresentation aCMR : ((ArrayList<ActualConfigurableMethodRepresentation>) settings))// check
		{
			if (aCMR.getActualValueType().equals(ActualValueType.ACTUAL_LIST))
			{
				if (aCMR.getList().getActualValueType().equals(ActualValueType.ACCR))
				{
					System.out.println(dashesString + aCMR.getKey() + " => [accr list]:");
					for(ActualConfigurableClassRepresentation aCCR : aCMR.getList().getACCRs())
					{
						//System.out.println("recursing in printloaded");
						printLoaded(aCCR.getSettings(), dashes + 1);
					}
				} 
				else
				{
					System.out.println(aCMR.getKey() + " => [list]:");
					for(Object o : aCMR.getList().getObjects())
					{
						System.out.println(dashesString + "*" + o.toString());
					}
				}
			} 
			else if (aCMR.getActualValueType().equals(ActualValueType.ACCR))
			{
				//System.out.println("ACCR value, recursing in printloaded");
				printLoaded(aCMR.getACCR().getSettings(), dashes + 1);
			}
			else
			{
				System.out.println(dashesString + aCMR.getKey() + " => " + aCMR.getValue().toString());
			}
		}
	}
	
	private void print(ArrayList<?> settings, int dashes)
	{
		if (!(settings.get(0) instanceof ConfigurableMethodRepresentation))
		{
			System.out
					.println("printSettings needs ArrayList<ConfigurableMethodRepresentation>");
			System.out.println("printSettings got "
					+ settings.get(0).getClass().getName());
			return;
		}

		String dashesString = "";

		for (int i = 0; i < dashes; i++)
		{
			dashesString += "-";
		}

		for (ConfigurableMethodRepresentation cMR : ((ArrayList<ConfigurableMethodRepresentation>) settings))// check
		{
			if (cMR.getValueType().equals(ValueType.LIST))
			{
				if (cMR.getList().getValueType().equals(ValueType.PRIMITIVE))
				{
					System.out.println(dashesString + "" + cMR.getKey()
							+ " [list] "
							+ cMR.getList().getPrimitive().toString());
				} else if (cMR.getList().getValueType().equals(ValueType.CCR))
				{
					System.out.println(dashesString + "" + cMR.getKey()
							+ " [cCR list] ");
					print(cMR.getList().getCCR().getSettings(), dashes + 1);
				} else
				{

				}
			} else if (cMR.getValueType().equals(ValueType.CCR))
			{
				print(cMR.getCCR().getSettings(), dashes + 1);
			} else if (cMR.getValueType().equals(ValueType.PRIMITIVE))
			{
				System.out.println(dashesString + cMR.getKey() + " => "
						+ cMR.getPrimitive().toString());
			} else
			{
				System.out
						.println("Invalid ConfigurableMethodRepresentation value type. SettingsSaver line 86");
				System.out.println("value type is" + cMR.getValueType());
			}
		}
	}

	private ConfigurableClassRepresentation process(Class<?> configurableClass)
	{
		if (ConfigurableClass.class.isAssignableFrom(configurableClass
				.getClass()))
			throw new InvalidParameterException(
					"Parameter configurableClass must implement ConfigurableClass.");

		ConfigurableClassRepresentation cCR = new ConfigurableClassRepresentation(
				configurableClass.getSimpleName(), configurableClass.getName());

		for (Method method : configurableClass.getMethods())
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
					if (ConfigurableClass.class.isAssignableFrom(method
							.getAnnotation(Configurable.class).listType()))
					{
						cCR.addSetting(key, new ListRepresentation(
								process(method
										.getAnnotation(Configurable.class)
										.listType())));
						statNumSettings++;
					} else
					{
						cCR.addSetting(key, new ListRepresentation(method
								.getAnnotation(Configurable.class).listType()));
						statNumSettings++;
					}
				} else if (Utils.isEndpointType(method.getParameterTypes()[0]))
				{
					cCR.addSetting(key, method.getParameterTypes()[0]);
					statNumSettings++;
				} else if (ConfigurableClass.class.isAssignableFrom(method
						.getParameterTypes()[0]))
				{
					cCR.addSetting(key, process(method.getParameterTypes()[0]));
					statNumSettings++;
				} else
					System.out
							.println("Invalid parameter type in method marked as configurable. Class: "
									+ method.getClass().getName()
									+ "Method: "
									+ method.getName()
									+ ". Parameter type: "
									+ method.getParameterTypes()[0]);
			}
		}

		return cCR;

	}
}
