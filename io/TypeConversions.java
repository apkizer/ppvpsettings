package com.planetarypvp.pe.settings.io;

public class TypeConversions
{
	public static <T> T convert(Object value, Class<T> type)
	{
        if (type.isAssignableFrom(value.getClass())) {
            return type.cast(value);
        }
        else
        {
        	System.out.println("Error, could not convert value to type in type conversions!");
        	return null;
        }
	}
	
	
}
