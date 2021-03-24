package com.greentree.engine.system;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.object.GameSystem;

/** @author Arseny Latyshev
 * @see BasicClassLoader */
@Retention(RUNTIME)
@Target(TYPE)
public @interface NecessarilySystems{
	
	Class<? extends GameSystem>[] value();
}
