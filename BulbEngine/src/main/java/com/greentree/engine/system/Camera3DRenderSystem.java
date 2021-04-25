package com.greentree.engine.system;

import com.greentree.engine.component.render.Camera3DRendenerComponent;
import com.greentree.engine.core.GameSystem;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Camera3DRenderSystem extends GameSystem {
	
	@Override
	protected void update() {
		Graphics.enableDepthTest();
		Graphics.enableCullFace();
		Graphics.enableBlead();
		
//		Cameras.getMainCamera().translate(CameraTranslateType.FRUSTUM, CameraTranslateType.SCALE, CameraTranslateType.MOVE, CameraTranslateType.ROTATE);
		
		for(final Camera3DRendenerComponent renderable : this.getAllComponents(Camera3DRendenerComponent.class)) {
			renderable.render();
		}
		
//		Cameras.getMainCamera().untranslate();
		
		Graphics.disableDepthTest();
		Graphics.disableCullFace();
		Graphics.disableBlead();
	}
}
