package com.greentree.graphics;


import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

/** @author Arseny Latyshev */
public class Camera {
	
	private final Vector3f position = new Vector3f(), scale = new Vector3f(), rotate = new Vector3f();
	
	public Camera() {
		position.set(0);
		rotate.set(0);
		scale.set(1);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotate() {
		return rotate;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void translate() {
		throw new UnsupportedOperationException();
	}
	
	public void translate(final CameraTranslateType... types) {
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		for(final CameraTranslateType type : types) type.translate(this);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public void untranslate() {
		GL11.glPopMatrix();
	}
	
	public enum CameraTranslateType{
		FRUSTUM{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glFrustum(-1, 1, -1, 1, 1, 100);
			}
		},
		ORTHO{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glOrtho(-1, 1, -1, 1, 0, 10);
			}
		},
		SCALE{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glScalef(camera.scale.x, camera.scale.y, camera.scale.z);
			}
		},
		/** before {@link SCALE} */
		NO_CENETR{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glScalef(1, -1, 1);
				GL11.glTranslatef(-1, -1, 0);
				GL11.glScalef(1, -1, 1);
			}
		},
		MOVE{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glTranslatef(camera.position.x, camera.position.y, camera.position.z);
			}
		},
		ROTATE{
			
			@Override
			protected void translate(final Camera camera) {
				GL11.glRotatef(camera.rotate.x, 1, 0, 0);
				GL11.glRotatef(camera.rotate.y, 0, 1, 0);
				GL11.glRotatef(camera.rotate.z, 0, 0, 1);
			}
		},
		INVERSION_Y{
			
			@Override
			protected void translate(final Camera cameraComponent) {
				GL11.glScalef(1, -1, 1);
			}
		};
		
		protected abstract void translate(Camera cameraComponent);
	}
	
	
	
}
