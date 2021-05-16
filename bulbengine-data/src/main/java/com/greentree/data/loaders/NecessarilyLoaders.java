package com.greentree.data.loaders;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** @author Arseny Latyshev
 * @see BasicClassLoader */
@Retention(RUNTIME)
@Target(TYPE)
public @interface NecessarilyLoaders{
	
	Class<? extends Loader>[] value();
}
