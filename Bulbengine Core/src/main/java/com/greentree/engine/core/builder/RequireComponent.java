package com.greentree.engine.core.builder;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.core.node.GameComponent;

@Retention(RUNTIME)
@Target(TYPE)
/** @author Arseny Latyshev */
public @interface RequireComponent{

	Class<? extends GameComponent>[] value();

}
