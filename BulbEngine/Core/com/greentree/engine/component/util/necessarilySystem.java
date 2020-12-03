package com.greentree.engine.component.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.system.util.GameSystem;

@Retention(RUNTIME)
@Target(TYPE)
public @interface necessarilySystem{
	
	Class<? extends GameSystem>[] value();
}
