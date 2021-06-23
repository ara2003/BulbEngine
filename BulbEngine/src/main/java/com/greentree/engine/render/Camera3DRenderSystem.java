package com.greentree.engine.render;

import com.greentree.engine.core.system.GameSystem.MultiBehaviour;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Camera3DRenderSystem extends MultiBehaviour {

	@Override
	public void update() {
		Graphics.enableDepthTest();
		Graphics.enableCullFace();
		Graphics.enableBlead();

		//		Cameras.getMainCamera().translate(CameraTranslateType.FRUSTUM, CameraTranslateType.SCALE, CameraTranslateType.MOVE, CameraTranslateType.ROTATE);

		for(final Camera3DRendenerComponent renderable : this.getAllComponents(Camera3DRendenerComponent.class)) renderable.render();

		//		Cameras.getMainCamera().untranslate();

		Graphics.disableDepthTest();
		Graphics.disableCullFace();
		Graphics.disableBlead();
	}
}
