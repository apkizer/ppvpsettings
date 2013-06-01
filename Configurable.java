package com.planetarypvp.pe.settings;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Configurable 
{
	String value() default "";//key name
	Class listType() default Null.class;//type of list in cases where an ArrayList is passed as the parameter in the method
}
