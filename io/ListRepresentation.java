package com.planetarypvp.pe.settings.io;

import java.security.InvalidParameterException;

public class ListRepresentation
{
	private ConfigurableClassRepresentation cCR;
	private Class primitive;
	private ListRepresentation list;//TODO future feature.
	private ValueType valueType;
	
	
	public ListRepresentation(Class<?> primitive)
	{
		/*if(!Utils.isWrapperType(primitive.getClass()))
			throw new InvalidParameterException("Parameter primitive must be a primitive type.");*/
		
		this.primitive = primitive;
		valueType = ValueType.PRIMITIVE;
	}
	
	public ListRepresentation(ConfigurableClassRepresentation cCR)
	{
		this.cCR = cCR;
		valueType = ValueType.CCR;
	}
	
	public ValueType getValueType()
	{
		return valueType;
	}
	
	public ConfigurableClassRepresentation getCCR()
	{
		return cCR;
	}
	
	public Class<?> getPrimitive()
	{
		return primitive;
	}
}

