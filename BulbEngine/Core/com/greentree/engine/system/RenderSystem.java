package com.greentree.engine.system;

import java.util.List;

import com.greentree.engine.component.Camera;
import com.greentree.engine.component.RendenerComponent;
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
	
	@Override
	public void execute() {
		List<Camera> cameras = getComponents(Camera.class);
		List<RendenerComponent> rendeners = getComponents(RendenerComponent.class);
		
		for(Camera camera : cameras)
			for(RendenerComponent rendener : rendeners) if(camera.mustDraw(rendener)){
				rendener.draw(GL);
			}
		
	}
	
}
