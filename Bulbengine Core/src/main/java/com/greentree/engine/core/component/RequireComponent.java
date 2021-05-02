package com.greentree.engine.core.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.core.object.GameComponent;

@Retention(RUNTIME)
@Target(TYPE)
/** @author Arseny Latyshev */
public @interface RequireComponent{
	
	Class<? extends GameComponent>[] value();
}
