package com.greentree.engine.component.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greentree.engine.object.necessarily;
import com.greentree.engine.system.RenderSystem;

@necessarily({RenderSystem.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface render{
}
