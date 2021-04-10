package com.greentree.engine.builder.xml;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.builder.loaders.Loader;

/** @author Arseny Latyshev
 * @see BasicClassLoader */
@Retention(RUNTIME)
@Target(FIELD)
public @interface MainLoader{
	
	Class<? extends Loader> value();
}
