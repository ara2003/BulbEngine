package com.greentree.engine.component.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.greentree.bulbgl.shader.Location;
import com.greentree.bulbgl.shader.ShaderProgram;
import com.greentree.bulbgl.shader.ShaderProgram.Attribute;
import com.greentree.bulbgl.shader.VideoBuffer;
import com.greentree.engine.Windows;
import com.greentree.engine.component.AbstractMeshComponent;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
@RequireComponent({AbstractMeshComponent.class})
public class MeshRenderer extends Camera3DRendenerComponent {
	
	// OpenGL program object
	@EditorData(name = "matirial")
	private ShaderProgram program;
	private Mesh mesh;
	
	// normal matrix uniform location
	private Location nmUL;
	// model-view-projection matrix uniform location
	private Location mvpUL;
	
	private float x_rotation = 20;
	private float y_rotation = 45;
	
	// vertex array object id
	private int vao;
	
	@Override
	protected void start() {
		super.start();
		mesh = getComponent(AbstractMeshComponent.class).getMesh();
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			vao = GL30.glGenVertexArrays();

			VideoBuffer vbo = program.createVideoBuffer(stack.floats(mesh.getVertexAndNormal()), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
//			
			VideoBuffer vio = program.createVideoBuffer(stack.ints(mesh.getIndecies()), VideoBuffer.Type.ELEMENT_ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			GL30.glBindVertexArray(vao);
			vio.bind();

			program.passVertexAttribArray(vbo, false, Attribute.of("vertex_coord", 3), Attribute.of("vertex_normal", 3));
			
			GL30.glBindVertexArray(0);
		}
		
		// now we need to link a program, after that we can not build VAO
		program.link();

		// now we can locate uniforms from program
		mvpUL = program.getUniformLocation("mvp");
		nmUL = program.getUniformLocation("nm");
		
		GL11.glEnable(GL30.GL_DEPTH);
	}
	
	@Override
	public void render() {
		// calculate perspective for our context
		// those works similar to gluPerspective
		float fovY =    (float) Windows.getWindow().getHeight() / (float) Windows.getWindow().getWidth();
		float aspectRatio =  (float) Windows.getWindow().getWidth() / (float) Windows.getWindow().getHeight();
		float h = fovY * 2.0F;
		float w = h * aspectRatio;
		final Matrix4f projection = new Matrix4f().frustum( -w, w, -h, h, 2.0F, 10.0F);
		
		final Matrix4f modelView = new Matrix4f().identity();
		// move model far from eye position
		modelView.translate(0, 0, -5f);
		
		// rotate model a little by x an y axis, to see cube in projection
		modelView.rotateXYZ((float)((x_rotation%360) / Math.PI), (float)((y_rotation%360) / Math.PI), 0.0F);
		x_rotation += .01;
		y_rotation += .02;
		
		
		
		final Matrix4f normal = new Matrix4f();
		modelView.normal(normal);
		
		// take MVP
		final Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(projection).mul(modelView);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			program.start();

			FloatBuffer nm = stack.callocFloat(16);
			normal.get(nm);
			FloatBuffer mvp = stack.callocFloat(16);
			modelVeiwProjection.get(mvp);

			mvpUL.glUniformMatrix4fv(false, mvp);
			nmUL.glUniformMatrix4fv(false, nm);
			
			GL30.glLineWidth(5);
			
			GL30.glBindVertexArray(vao);
			GL30.nglDrawElements(GL30.GL_TRIANGLES, mesh.getAmountVertices(), GL11.GL_UNSIGNED_INT, MemoryUtil.NULL );
			GL30.glBindVertexArray(0);
			program.stop();
		}
	}
	
}
