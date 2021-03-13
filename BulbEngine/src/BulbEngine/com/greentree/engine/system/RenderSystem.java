package com.greentree.engine.system;

import com.greentree.engine.component.Camera;
import com.greentree.engine.component.ComponentList;
import com.greentree.util.Log;

/** @author Arseny Latyshev */
public class RenderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private Camera mainCamera;
	
	@Override
	public void execute() {
		mainCamera.draw();
	}
	
	public Camera getMainCamera() {
		return mainCamera;
	}
	
	@Override
	protected void start() {
		ComponentList<Camera> cameras = getComponents(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found", new NullPointerException());
		if(cameras.size() > 1) Log.error("Found more one camera", new Exception());
		mainCamera = cameras.iterator().next();
	}
}
