package com.greentree.engine.core.system;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.core.object.GameSystem;

/** @author Arseny Latyshev */
@Retention(RUNTIME)
@Target(TYPE)
public @interface RequireSystems{
	
	Class<? extends GameSystem>[] value();
}
