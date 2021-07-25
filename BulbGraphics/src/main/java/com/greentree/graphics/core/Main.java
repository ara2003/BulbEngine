package com.greentree.graphics.core;


import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Camera;
import com.greentree.graphics.Camera.CameraTranslateType;
import com.greentree.graphics.Color;
import com.greentree.graphics.GLFWContext;
import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.GLType;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.Graphics.ClientState;
import com.greentree.graphics.Window;
import com.greentree.graphics.texture.GLTexture2D;
import com.greentree.graphics.texture.GLTextureLoader;
import com.greentree.graphics.window.SimpleWindow;

/** @author Arseny Latyshev */
public abstract class Main {

	private static GLTexture2D texture;

//	private static int texture;

	private static void drawBlack() {
		Graphics.pushMatrix();

		Graphics.enableBlead();

		Color.white.bind();

//		GL11.glBindTexture(GL_TEXTURE_2D, texture);
		texture.bind();
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			Graphics.glVertexPointer(2, GLType.FLOAT, 0, stack.floats(Graphics.array2f));
			Graphics.glTexCoordPointer(2, GLType.FLOAT, 0, stack.floats(0, 0, 1, 0, 1, 1, 0, 1));
		}
		Graphics.glEnableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glEnableClientState(ClientState.TEXTURE_COORD_ARRAY);

		Graphics.glDrawArrays(GLPrimitive.QUADS, 0, 4);

		Graphics.glDisableClientState(ClientState.VERTEX_ARRAY);
		Graphics.glDisableClientState(ClientState.TEXTURE_COORD_ARRAY);

		GLTexture2D.unbindTexture();
		Graphics.disableBlead();

		Graphics.popMatrix();
		Graphics.validateOpenGL();
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
		final Collection<GLFWContext> windows = new CopyOnWriteArrayList<>();

		var res = new GLFWContext("res", 500, 500, true, false, false, null);
		var w1 = new SimpleWindow("window 1", 500, 500, res);
		var w2 = new SimpleWindow("window 2", 500, 500, res);
		
		windows.add(w1);
		windows.add(w2);
//		windows.add(res);

		res.makeCurrent();
		
		texture = GLTextureLoader.getTexture2D("test\\mag(2).png");
		
		GLTexture2D.unbindTexture();
		Graphics.validateOpenGL();
		
		for(var w : windows) {
			w.makeCurrent();
			start();
		}
		Window.unmakeCurrent();
		Camera c = new Camera();

		c.getScale().x = 1f/2;
		c.getScale().y = 1f/2;
		c.getScale().z = 1f/2;

		c.getPosition().z -= 4;

		while(!windows.isEmpty()) {
//						c.getPosition().z -= .02;
			c.getRotate().x += 1f;
			c.getRotate().y += .01f;
			for(final var window : windows) if(window.isShouldClose()) {
				window.close();
				windows.remove(window);
			}
			Window.unmakeCurrent();
			for(var w : windows) {
				w.makeCurrent();
				w.updateEvents();
				Graphics.viewport(0, 0, w.getWidth(), w.getHeight());
				render(c);
				w.swapBuffer();
			}
			Window.unmakeCurrent();
		}
		BulbGL.terminate();
	}

	private static void render(Camera camera) {
		camera.translate(CameraTranslateType.FRUSTUM, CameraTranslateType.SCALE, CameraTranslateType.MOVE, CameraTranslateType.ROTATE);

		Graphics.glClearAll();

		Graphics.enableDepthTest();
		Main.drawBlack();
//				Main.drawGreen();
		Graphics.disableDepthTest();

		camera.untranslate();
	}

	private static void start() {
		//		Log.init(new File("test"));

		Graphics.clearColor(.6f, .6f, .6f);
		Graphics.setClearDepth(1.0);
	}

}
