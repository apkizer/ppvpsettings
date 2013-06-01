package com.planetarypvp.pe.settings.io;

import java.util.ArrayList;
import java.util.HashSet;

public final class Utils
{
    private static final HashSet<Class<?>> ENDPOINT_TYPES = getEndpointTypes();

    public static boolean isEndpointType(Class<?> clazz)
    {
        return ENDPOINT_TYPES.contains(clazz);
    }
    
    public static ActualValueType getActualValueType(Class<?> clazz)
    {
    	if(Integer.class.isAssignableFrom(clazz))
    	{
    		return ActualValueType.INT;
    	}
    	else if(Boolean.class.isAssignableFrom(clazz))
    	{
    		return ActualValueType.BOOL;
    	}
    	else if(Double.class.isAssignableFrom(clazz))
    	{
    		return ActualValueType.DOUB;
    	}
    	else if(String.class.isAssignableFrom(clazz))
    	{
    		return ActualValueType.STR;
    	}
    	else
    	{
    		System.out.println(clazz.getSimpleName() + " isn't an int, bool, doub, or str");
    		return null;
    	}
    }
    
    public static ArrayList<String> directory(ArrayList<String> directory, String string)
    {
    	ArrayList<String> r = new ArrayList<String>();
    	for(String s : directory)
    	{
    		r.add(s);
    	}
    	r.add(string);
    	return r;
    }
    
    public static String listToString(ArrayList<String> list)
    {
    	String ret = "";
    	for(String s : list)
    	{
    		ret += s;
    	}
    	return ret;
    }

    private static HashSet<Class<?>> getEndpointTypes()
    {
        HashSet<Class<?>> endTypes = new HashSet<Class<?>>();
        endTypes.add(Boolean.class);
        endTypes.add(Character.class);
        endTypes.add(Byte.class);
        endTypes.add(Short.class);
        endTypes.add(Integer.class);
        endTypes.add(Long.class);
        endTypes.add(Float.class);
        endTypes.add(Double.class);
        endTypes.add(Void.class);
        endTypes.add(String.class);
        return endTypes;
    }
}
