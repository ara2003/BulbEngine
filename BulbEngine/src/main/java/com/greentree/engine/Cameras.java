package com.greentree.engine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.util.SceneMananger;
import com.greentree.engine.render.CameraComponent;

/** @author Arseny Latyshev */
public class Cameras {

	protected static CameraComponent mainCamera = null;

	protected static CameraComponent foundCameraInScene() {
		final List<CameraComponent> cameras = SceneMananger.getCurrentScene().getAllComponents(CameraComponent.class);
		if(cameras.size() < 1) throw new UnsupportedOperationException("Camera not found in scene " + SceneMananger.getCurrentScene().toSimpleString());
		if(cameras.size() > 1) throw new UnsupportedOperationException(
				"Found more one camera in object " + cameras.parallelStream().map(CameraComponent::getObject).filter(Objects::nonNull).map(GameObject::getName).collect(Collectors.toList()));
		return cameras.get(0);
	}

	public static CameraComponent getMainCamera() {
		if(mainCamera == null) mainCamera = foundCameraInScene();
		else if(mainCamera.isDestroy()) {
			mainCamera = null;
			return getMainCamera();
		}
		return mainCamera;
	}
}
