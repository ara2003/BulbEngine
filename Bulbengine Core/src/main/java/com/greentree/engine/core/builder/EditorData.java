package com.greentree.engine.core.builder;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface EditorData {

	String name() default "";
	/**
	 * @deprecated use Required
	 * @return
	 */
	@Deprecated
	boolean required() default false;

}
