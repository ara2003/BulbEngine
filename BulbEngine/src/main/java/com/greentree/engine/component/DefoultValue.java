package com.greentree.engine.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;



/**
 * @author Arseny Latyshev
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface DefoultValue {
	
	String value();
	
}
