package com.greentree.engine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.render.CameraComponent;

/** @author Arseny Latyshev */
public class Cameras {
	
	protected static CameraComponent mainCamera = null;
	
	protected static CameraComponent foundCameraInScene() {
		final List<CameraComponent> cameras = GameCore.getCurrentScene().getAllComponents(CameraComponent.class);
		if(cameras.size() < 1) throw new UnsupportedOperationException("Camera not found in scene " + GameCore.getCurrentScene().toSimpleString());
		if(cameras.size() > 1) throw new UnsupportedOperationException(
			"Found more one camera in object " + cameras.parallelStream().map(CameraComponent::getObject).filter(Objects::nonNull).map(GameObject::getName).collect(Collectors.toList()));
		return cameras.get(0);
	}
	
	public static CameraComponent getMainCamera() {
		if(mainCamera != null)
		if(mainCamera.getObject().isDestroy()) {
			mainCamera = null;
		}
		if(mainCamera == null) {
			mainCamera = foundCameraInScene();
		}
		return mainCamera;
	}
}
