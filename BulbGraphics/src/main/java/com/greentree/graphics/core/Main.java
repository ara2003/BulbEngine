package com.greentree.graphics.core;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import com.greentree.common.logger.Log;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Camera;
import com.greentree.graphics.Camera.CameraTranslateType;
import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.Window;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.texture.Filtering;
import com.greentree.graphics.texture.GLTexture2D;
import com.greentree.graphics.texture.GLTextureLoader;
import com.greentree.graphics.window.SimpleWindow;

/** @author Arseny Latyshev */
public abstract class Main {
	
	private static GLTexture2D texture;

	private static void render(Window window, Camera camera) {
		window.makeCurrent();
		window.updateEvents();
		camera.translate(CameraTranslateType.FRUSTUM, CameraTranslateType.SCALE, CameraTranslateType.MOVE, CameraTranslateType.ROTATE);

		Graphics.glClearAll();
		
//		GL11.glLineWidth(5);
//		try(MemoryStack stack = MemoryStack.stackPush()) {
//				float[] color = {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1};
//				Graphics.glVertexPointer(3, GLType.FLOAT, 0, stack.floats(color));
//				Graphics.glEnableClientState(Graphics.ClientState.VERTEX_ARRAY);
//				Graphics.glColorPointer(3, GLType.FLOAT, 0, stack.floats(color));
//				Graphics.glEnableClientState(Graphics.ClientState.COLOR_ARRAY);
//				
//				Graphics.glDrawElements(GLPrimitive.LINES, stack.ints(0, 1, 0, 2, 0, 3));
//				
//				Graphics.glDisableClientState(Graphics.ClientState.VERTEX_ARRAY);
//				Graphics.glDisableClientState(Graphics.ClientState.COLOR_ARRAY);
//		}
		
//		Graphics.setColor(0, 0, 0, 1);
//		Graphics.getFont().drawString(0, 0, "text");
		

		Graphics.enableDepthTest();
		Main.drawBlack();
		Main.drawGreen();
		Graphics.disableDepthTest();
		
		/*
		Graphics.enableBlead();
		
		Color.white.bind();
		
		texture.bind();
		try(MemoryStack stack = MemoryStack.stackPush()){
			Graphics.glVertexPointer(2, GLType.FLOAT, stack.floats(0, 0, 0, 1, 1, 1, 1, 0));
			Graphics.glTexCoordPointer(2, GLType.FLOAT, stack.floats(0, 0, 0, texture.getTexHeight(), texture.getTexWidth(), texture.getTexHeight(), texture.getTexWidth(), 0));
		}
		Graphics.glEnableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glEnableClientState(ClientState.TEXTURE_COORD_ARRAY);
		
		Graphics.glDrawArrays(GLPrimitive.QUADS, 0, 4);
		
		Graphics.glDisableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glDisableClientState(ClientState.TEXTURE_COORD_ARRAY);

		GLTexture2D.unbindTexture();
		Graphics.disableBlead();
		//*/
		
		camera.untranslate();
		window.swapBuffer();
	}
	
	private static void drawBlack() {
		Graphics.glBegin(GLPrimitive.TRIANGLES);
		Graphics.setColor(0, 0, 0, 1f);
		GL11.glVertex3f(-1, 1, -1);
		GL11.glVertex3f(-1, 1, 1);
		GL11.glVertex3f(1, -1, 0);
		Graphics.glEnd();
	}
	
	private static void drawGreen() {
		Graphics.glBegin(GLPrimitive.TRIANGLES);
		Graphics.setColor(0, 1, 0, 1f);
		GL11.glVertex3f(-1, -1, 0);
		GL11.glVertex3f(1, 0, 0);
		GL11.glVertex3f(0, 1, 0);
		Graphics.glEnd();
	}
	
	public static void main(final String[] args) {
		BulbGL.init();
		final Collection<Window> windows = new CopyOnWriteArrayList<>();
		
		windows.add(new SimpleWindow("window 1", 500, 500));
//		windows.add(new SimpleWindow("window 2", 500, 500));
		
		for(final Window w : windows) {
			w.makeCurrent();
			Main.start();
		}
		
		Camera c = new Camera();

		c.getScale().x = 1f/2;
		c.getScale().y = 1f/2;
		c.getScale().z = 1f/2;

		c.getPosition().z -= 3;
		
		while(!windows.isEmpty()) {
			c.getPosition().z -= .02;
			c.getRotate().x += 1f;
			c.getRotate().y += .01f;
			for(final Window w : windows) {
				if(w.isShouldClose()) {
					w.close();
					windows.remove(w);
				}else {
					render(w, c);
				}
			}
		}
		BulbGL.terminate();
	}

	private static void start() {
		Log.init(new File("test"));
		
		Graphics.clearColor(.6f, .6f, .6f);
		Graphics.setClearDepth(1.0);
		
		texture = GLTextureLoader.getTexture2D("test\\mag(2).png");
		texture.setMagFilter(Filtering.LINEAR);
		texture.setMinFilter(Filtering.NEAREST);
		
		texture.setWrap(Wrapping.CLAMP_TO_BORDER);
		
	}
	
}
