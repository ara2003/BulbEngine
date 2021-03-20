package com.greentree.engine.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface EditorData{
	
	public final static String NULL = "";
	
	public String def() default NULL;
	
	public String name() default NULL;
}
