package com.greentree.engine.core.system;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** @author Arseny Latyshev */
@Retention(RUNTIME)
@Target({TYPE})
@Inherited
public @interface GroupSystem{
	
	byte priority() default 0;
	
	String value();
	
}
