package com.greentree.engine.render.ui;

import com.greentree.engine.core.node.GameSystem.MultiBehaviour;
import com.greentree.engine.util.Cameras;
import com.greentree.graphics.Graphics;


/** @author Arseny Latyshev */
public class Canvas extends MultiBehaviour {

	int a = 9;

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsCamera();
		Graphics.enableBlead();
		for(final UIComponent uiComponent : getAllComponents(UIComponent.class)) uiComponent.render();
		Graphics.disableBlead();
		Cameras.getMainCamera().untranslate();
	}

}
