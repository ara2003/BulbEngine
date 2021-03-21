package com.greentree.engine.system;

import java.util.stream.Collectors;

import com.greentree.engine.Game;
import com.greentree.engine.component.Camera;
import com.greentree.engine.component.ComponentList;
import com.greentree.engine.component.RendenerComponent;
import com.greentree.engine.component.Transform;
import com.greentree.util.Log;

/** @author Arseny Latyshev */
public class RenderSystem extends GameSystem {
	
	private static final long serialVersionUID = 1L;
	private Camera mainCamera;
	
	public Camera getMainCamera() {
		return this.mainCamera;
	}
	
	@Override
	public void start() {
		final ComponentList<Camera> cameras = this.getAllComponentsAsComponentList(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found " + Game.getCurrentScene(), new NullPointerException());
		if(cameras.size() > 1) Log.error("Found more one camera", new Exception());
		this.mainCamera = cameras.get(0);
	}
	
	@Override
	public void update() {
		this.mainCamera.translate();
		for(final RendenerComponent renderable : getAllComponents(RendenerComponent.class).parallelStream()
				.sorted((a, b)->(int) (a.getComponent(Transform.class).z - b.getComponent(Transform.class).z))
				.collect(Collectors.toList())) {
			renderable.render();
		}
		this.mainCamera.untranslate();
	}
}
