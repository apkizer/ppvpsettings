package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;

public class ActualListRepresentation
{
	private ActualValueType actualType;
	private ArrayList<ActualConfigurableClassRepresentation> aCCRs;
	private ArrayList<Integer> ints;
	private ArrayList<String> strings;
	private ArrayList<Boolean> bools;
	private ArrayList<Double> doubs;
	
	public ActualListRepresentation(ActualValueType actualType)
	{
		this.actualType = actualType;
		switch(actualType)
		{
		case ACCR:
			aCCRs = new ArrayList<>();
			break;
		//case ACTUAL_LIST:
			//TODO later feature
			//break;
		case INT:
			ints = new ArrayList<>();
			break;
		case DOUB:
			doubs = new ArrayList<>();
			break;
		case BOOL:
			bools = new ArrayList<>();
			break;
		case STR:
			strings = new ArrayList<>();
			break;
		default:
			break;
		}
	}
	
	public ArrayList<?> getObjects()
	{
		switch(actualType)
		{
		case ACCR:
			return null;
			//case ACTUAL_LIST:
			//TODO later feature
			//break;
		case INT:
			return ints;
		case DOUB:
			return doubs;
		case BOOL:
			return bools;
		case STR:
			return strings;
		default:
			return null;
		}
	}
	
	public ActualValueType getActualValueType()
	{
		return actualType;
	}
	
	
	public void addConfigurableClassRepresentation(ActualConfigurableClassRepresentation cCR)
	{
		aCCRs.add(cCR);
	}
	
		
	public ArrayList<ActualConfigurableClassRepresentation> getACCRs()
	{
		return aCCRs;
	}
	
	public ArrayList<Integer> getInts()
	{
		return ints;
	}
	
	public ArrayList<Double> getDoubles()
	{
		return doubs;
	}
	
	public ArrayList<Boolean> getBooleans()
	{
		return bools;
	}
	
	public ArrayList<String> getStrings()
	{
		return strings;
	}
	
	public void addInt(Integer i)
	{
		ints.add(i);
	}
	
	public void addBool(Boolean b)
	{
		bools.add(b);
	}
	
	public void addStr(String s)
	{
		strings.add(s);
	}
	
	public void addDoub(Double d)
	{
		doubs.add(d);
	}
}
