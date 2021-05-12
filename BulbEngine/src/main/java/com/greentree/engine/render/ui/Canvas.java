package com.greentree.engine.render.ui;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;
import com.greentree.graphics.Graphics;


/** @author Arseny Latyshev */
@GroupSystem("graphics")
public class Canvas extends GameSystem {
	
	int a = 9;
	
	@Override
	public void update() {
		Cameras.getMainCamera().translateAsCamera();
		Graphics.enableBlead();
		for(final UIComponent uiComponent : getAllComponents(UIComponent.class)) {
			uiComponent.render();
		}
		Graphics.disableBlead();
		Cameras.getMainCamera().untranslate();
	}
	
}
