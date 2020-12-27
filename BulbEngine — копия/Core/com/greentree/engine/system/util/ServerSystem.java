package com.greentree.engine.system.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @author Arseny Latyshev
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ServerSystem{
}
