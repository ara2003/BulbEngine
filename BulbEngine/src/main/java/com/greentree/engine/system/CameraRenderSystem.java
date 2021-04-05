package com.greentree.engine.system;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import com.greentree.common.Log;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.render.Camera;
import com.greentree.engine.component.render.CameraRendenerComponent;
import com.greentree.engine.core.Game;
import com.greentree.engine.core.GameObject;
import com.greentree.engine.core.GameSystem;
import com.greentree.engine.core.component.ComponentList;

/** @author Arseny Latyshev */
public class CameraRenderSystem extends GameSystem {
	
	private Camera mainCamera;
	
	public Camera getMainCamera() {
		return this.mainCamera;
	}
	
	@Override
	protected void start() {
		final ComponentList<Camera> cameras = this.getAllComponentsAsComponentList(Camera.class);
		if(cameras.size() < 1) Log.error("Camera not found " + Game.getCurrentScene(), new NullPointerException());
		if(cameras.size() > 1) Log.warn(
			"Found more one camera in object " + cameras.parallelStream().map(Camera::getObject).filter(Objects::nonNull).map(GameObject::getName).collect(Collectors.toList()),
			new Exception());
		this.mainCamera = cameras.get(0);
	}
	
	@Override
	protected void update() {
		this.mainCamera.translate();
		for(final CameraRendenerComponent renderable : this.getAllComponents(CameraRendenerComponent.class).parallelStream()
			.sorted(Comparator.comparing(a->a.getComponent(Transform.class).z()))
			.collect(Collectors.toList()))
			renderable.render();
		this.mainCamera.untranslate();
	}
}
