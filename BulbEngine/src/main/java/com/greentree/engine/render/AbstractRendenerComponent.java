package com.greentree.engine.render;

import com.greentree.engine.component.StartGameComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.RequireComponent;

/** @author Arseny Latyshev */
@RequireComponent({Transform.class})
public abstract class AbstractRendenerComponent extends StartGameComponent {

	public abstract void render();


}
