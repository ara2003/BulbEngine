package com.greentree.engine.render;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.object.GameSystem;


/** @author Arseny Latyshev */
public class SimpleFigureSystem extends GameSystem {

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWindow();
		for(final SimpleFigure renderable : getAllComponents(SimpleFigure.class)) renderable.render();
		Cameras.getMainCamera().untranslate();
	}

}
