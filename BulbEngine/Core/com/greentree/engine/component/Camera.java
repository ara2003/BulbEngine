package com.greentree.engine.component;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.greentree.engine.Game;
import com.greentree.engine.necessarily;
import com.greentree.engine.component.util.DiapasonInt;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.offsetGameComponent;
import com.greentree.engine.opengl.rendener.LineStripRenderer;
import com.greentree.engine.opengl.rendener.SGL;
import com.greentree.engine.system.RenderSystem;
import com.greentree.geom.AABB;


/**
 * @author Arseny Latyshev
 *
 */
@necessarily({RenderSystem.class})
public class Camera extends offsetGameComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData
	@DiapasonInt(min = 1)
	private int width, height;
	private Transform position;
	
	private List<RendenerComponent> cull(List<RendenerComponent> components) {//TODO
		List<RendenerComponent> list = new ArrayList<>(components);
		list.removeIf(rendener->!isVisible(rendener));
		return list;
	}
	
	public boolean isVisible(RendenerComponent rendener){
		return new AABB(position.x-width/2, position.y-height/2, width, height).isTouch(rendener.getAABB());
	}
	
	public void draw(SGL GL, LineStripRenderer SLR) {
		GL.glScalef(1f*Display.getWidth() / width, 1f*Display.getHeight() / height, 1);
		GL.glTranslatef(width/2-position.x, height/2-position.y, 0);
		for(RendenerComponent rendener : cull(Game.getCurrentScene().getComponents(RendenerComponent.class))){
			rendener.draw(GL);
		}
		GL.glTranslatef(-width/2+position.x,-height/2+position.y, 0);
		GL.glScalef(1f*width / Display.getWidth(), 1f*height / Display.getHeight(), 1);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	@Override
	public String toString() {
		return "Camera [width=" + width + ", height=" + height + ", position=" + position + "]";
	}
	
}
