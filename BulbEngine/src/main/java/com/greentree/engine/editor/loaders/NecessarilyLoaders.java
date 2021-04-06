package com.greentree.engine.editor.loaders;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** @author Arseny Latyshev
 * @see BasicClassLoader */
@Retention(RUNTIME)
@Target(FIELD)
public @interface NecessarilyLoaders {
	
	Class<? extends Loader>[] value();
}
