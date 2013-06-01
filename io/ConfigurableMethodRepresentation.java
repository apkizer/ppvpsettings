package com.planetarypvp.pe.settings.io;

public class ConfigurableMethodRepresentation
{
	private String key;

	private Class<?> primitive;
	private ConfigurableClassRepresentation cCR;
	private ListRepresentation list;
	private ActualListRepresentation actualList;
	private ValueType valueType;
	private String path;

	public ConfigurableMethodRepresentation(String key, Class<?> value)
	{
		this.key = key;
		this.primitive = value;
		this.valueType = ValueType.PRIMITIVE;
	}

	public ConfigurableMethodRepresentation(String key,
			ConfigurableClassRepresentation cCR)
	{
		this.key = key;
		this.cCR = cCR;
		this.valueType = ValueType.CCR;
	}

	public ConfigurableMethodRepresentation(String key, ListRepresentation list)
	{
		this.key = key;
		this.list = list;
		this.valueType = ValueType.LIST;
	}
	
	/*public ConfigurableMethodRepresentation(String key, ActualListRepresentation actualList)
	{
		this.key = key;
		this.actualList = actualList;
		this.valueType = ValueType.ACTUAL_LIST;
	}*/
			
	public String getKey()
	{
		return key;
	}

	public Class<?> getPrimitive()
	{
		return primitive;
	}

	public ConfigurableClassRepresentation getCCR()
	{
		return cCR;
	}

	/**
	 * For use in saving.
	 * @return
	 */
	public ListRepresentation getList()
	{
		return list;
	}

	public ValueType getValueType()
	{
		return valueType;
	}
	
	/**
	 * For use in loading.
	 * @return
	 */
	public ActualListRepresentation getActualList()
	{
		return actualList;
	}
	
	@Override
	public String toString()
	{
		if (valueType.equals(ValueType.PRIMITIVE))
			return key + " => " + primitive;
		else if (valueType.equals(ValueType.CCR))
			return key + " => " + cCR;
		else if (valueType.equals(ValueType.LIST))
			return key + " => " + list;
		else
		{
			System.out.println("error cMR line 92");
			return "";
		}
	}

}
