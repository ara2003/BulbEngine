package com.greentree.engine.render;

import com.greentree.engine.core.object.GameSystem;
import com.greentree.engine.core.system.GroupSystem;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
@GroupSystem("graphics")
public class Camera3DRenderSystem extends GameSystem {
	
	@Override
	public void update() {
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
