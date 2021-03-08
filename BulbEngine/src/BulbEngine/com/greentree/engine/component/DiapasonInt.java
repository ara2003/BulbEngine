package com.greentree.engine.component;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(CLASS)
@Target({FIELD,PARAMETER})
/**
 * @author Arseny Latyshev
 *
 */
public @interface DiapasonInt {

	int min() default Integer.MIN_VALUE;
	int max() default Integer.MAX_VALUE;
	
}
