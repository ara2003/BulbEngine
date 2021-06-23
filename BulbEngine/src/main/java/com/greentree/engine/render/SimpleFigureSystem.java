package com.greentree.engine.render;

import com.greentree.engine.Cameras;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;


/** @author Arseny Latyshev */
public class SimpleFigureSystem extends MultiBehaviour {

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWindow();
		for(final SimpleFigure renderable : getAllComponents(SimpleFigure.class)) renderable.render();
		Cameras.getMainCamera().untranslate();
	}

}
