package com.greentree.engine.core.builder;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;

/** @author Arseny Latyshev */
@Retention(RUNTIME)
@Target(TYPE)
public @interface RequireSystems{

	Class<? extends MultiBehaviour>[] value();
}
