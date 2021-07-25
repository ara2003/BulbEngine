package com.greentree.engine2d.render;

import com.greentree.engine.core.object.GameSystem.MultiBehaviour;
import com.greentree.engine.util.Cameras;


/** @author Arseny Latyshev */
public class SimpleFigureSystem extends MultiBehaviour {

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWindow();
		for(final SimpleFigure renderable : getAllComponents(SimpleFigure.class)) renderable.render();
		Cameras.getMainCamera().untranslate();
	}

}
