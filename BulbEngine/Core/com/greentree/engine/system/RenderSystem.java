package com.greentree.engine.system;

import com.greentree.engine.Log;
import com.greentree.engine.component.Camera;
import com.greentree.engine.component.util.ComponentList;
import com.greentree.engine.opengl.rendener.LineStripRenderer;
import com.greentree.engine.opengl.rendener.Renderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.util.GameSystem;


/**
 * @author Arseny Latyshev
 *
 */
public class RenderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private static SGL GL = Renderer.get();
	private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
	private Camera mainCamera;
	
	@Override
	protected void start() {
		ComponentList<Camera> cameras = getComponents(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found", new NullPointerException());
		if(cameras.size() > 1) Log.error("Found more one camera", new Exception());
		mainCamera = cameras.get(0);
	}
	
	@Override
	public void execute() {
		
		mainCamera.draw(GL, LSR);
		
	}

	public Camera getMainCamera() {
		return mainCamera;
	}
	
}
