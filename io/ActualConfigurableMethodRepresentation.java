package com.planetarypvp.pe.settings.io;

public class ActualConfigurableMethodRepresentation
{
	private String key;
	private int valInt;
	private String valStr;
	private boolean valBool;
	private double valDoub;
	private ActualConfigurableClassRepresentation aCCR;
	private ActualListRepresentation list;
	private ActualValueType actualType;
	
	public ActualConfigurableMethodRepresentation(String key, ActualConfigurableClassRepresentation aCCR)
	{
		actualType = ActualValueType.ACCR;
		this.aCCR = aCCR;
		this.key = key;
	}
	
	public ActualConfigurableMethodRepresentation(String key, ActualListRepresentation list)
	{
		actualType = ActualValueType.ACTUAL_LIST;
		this.list = list;
		this.key = key;
		//System.out.println("(apply/load) created aCMR for a list with key = " + key);
	}
	
	public ActualConfigurableMethodRepresentation(String key, int value)
	{
		actualType = ActualValueType.INT;
		valInt = value;
		this.key = key;
	}
	
	public ActualConfigurableMethodRepresentation(String key, String value)
	{
		actualType = ActualValueType.STR;
		valStr = value;
		this.key = key;
	}
	
	public ActualConfigurableMethodRepresentation(String key, double value)
	{
		actualType = ActualValueType.DOUB;
		valDoub = value;
		this.key = key;
	}
	
	public ActualConfigurableMethodRepresentation(String key, boolean value)
	{
		actualType = ActualValueType.BOOL;
		valBool = value;
		this.key = key;
	}
	
	public ActualListRepresentation getList()
	{
		return list;
	}
	
	public Object getValue()
	{
		switch(actualType)
		{
		case ACCR:
			return aCCR;//TODO shouldn't
		case INT:
			return valInt;
		case STR:
			return valStr;
		case DOUB:
			return valDoub; 
		case BOOL:
			return valBool;
		default:
			return null;
		}
	}
	
	public String getKey()
	{
		//System.out.println("ActualConfig is returning key " + key);
		return key;
	}
	
	public ActualValueType getActualValueType()
	{
		return actualType;
	}
	
	public ActualConfigurableClassRepresentation getACCR()
	{
		return aCCR;
	}
	
	public String toString()
	{
		return key + " = " + getValue().toString();
	}
	
	public Integer getInt()
	{
		return valInt;
	}
	
	public Double getDouble()
	{
		return valDoub;
	}
	
	public Boolean getBoolean()
	{
		return valBool;
	}
	
	public String getString()
	{
		return valStr;
	}
}
