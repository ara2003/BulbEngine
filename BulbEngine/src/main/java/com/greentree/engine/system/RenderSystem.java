package com.greentree.engine.system;

import com.greentree.bulbgl.Renderable;
import com.greentree.engine.Game;
import com.greentree.engine.component.Camera;
import com.greentree.engine.component.ComponentList;
import com.greentree.util.Log;

/** @author Arseny Latyshev */
public class RenderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private Camera mainCamera;
	
	@Override
	public void update() {
		mainCamera.translate();
		for(Renderable renderable : getAllComponents(Renderable.class)) {
			renderable.render();
		}
		mainCamera.untranslate();
	}

	public Camera getMainCamera() {
		return mainCamera;
	}
	
	@Override
	public void start() {
		ComponentList<Camera> cameras = getAllComponentsAsComponentList(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found " + Game.getCurrentScene(), new NullPointerException());
		if(cameras.size() > 1) Log.error("Found more one camera", new Exception());
		mainCamera = cameras.get(0);
	}
}
