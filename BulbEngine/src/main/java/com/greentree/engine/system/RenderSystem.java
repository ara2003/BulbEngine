package com.greentree.engine.system;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.greentree.common.Log;
import com.greentree.engine.Game;
import com.greentree.engine.component.AbstractRendenerComponent;
import com.greentree.engine.component.Camera;
import com.greentree.engine.component.ComponentList;
import com.greentree.engine.component.Transform;
import com.greentree.engine.object.GameSystem;

/** @author Arseny Latyshev */
public class RenderSystem extends GameSystem {
	
	private Camera mainCamera;
	
	public Camera getMainCamera() {
		return this.mainCamera;
	}
	
	@Override
	protected void start() {
		final ComponentList<Camera> cameras = this.getAllComponentsAsComponentList(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found " + Game.getCurrentScene(), new NullPointerException());
		if(cameras.size() > 1) Log.error("Found more one camera", new Exception());
		this.mainCamera = cameras.get(0);
	}
	
	@Override
	protected void update() {
		this.mainCamera.translate();
		for(final AbstractRendenerComponent renderable : this.getAllComponents(AbstractRendenerComponent.class).parallelStream()
				.sorted(Comparator.comparing(a -> a.getComponent(Transform.class).z()))
				.collect(Collectors.toList()))
			renderable.render();
		this.mainCamera.untranslate();
	}
}
