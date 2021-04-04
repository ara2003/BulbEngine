package com.greentree.engine;

import com.greentree.engine.component.render.Camera;
import com.greentree.engine.core.Game;
import com.greentree.engine.system.RenderSystem;

/**
 * @author Arseny Latyshev
 *
 */
public class Cameras {

	public static Camera getMainCamera() {
		return Game.getCurrentScene().getSystem(RenderSystem.class).getMainCamera();
	}
}
