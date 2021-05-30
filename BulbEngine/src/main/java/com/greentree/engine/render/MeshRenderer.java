package com.greentree.engine.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.engine.Cameras;
import com.greentree.engine.component.AbstractMeshComponent;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.mesh.Mesh.IndeciesArray;
import com.greentree.engine.mesh.Mesh.Type;
import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.GLType;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.shader.GLLocation;
import com.greentree.graphics.shader.GLVertexArray;
import com.greentree.graphics.shader.ShaderProgram;
import com.greentree.graphics.shader.ShaderProgram.Attribute;
import com.greentree.graphics.shader.VertexArray;
import com.greentree.graphics.shader.VideoBuffer;

/** @author Arseny Latyshev */
@RequireComponent({AbstractMeshComponent.class})
public class MeshRenderer extends Camera3DRendenerComponent {
	
	// OpenGL program object
	@EditorData(name = "matirial")
	private ShaderProgram program;
	
	// normal matrix uniform location
	private GLLocation nmUL;
	// model-view-projection matrix uniform location
	private GLLocation mvpUL;
	
	// vertex array object id
	private VertexArray vao;
	
	private int lengthIndecies;
	
	@Override
	public void render() {
		
		//		glEnable(GL_CULL_FACE);
		//		glCullFace(GL_BACK);
		//		glEnable(GL_DEPTH_TEST);
		
		// calculate perspective for our context
		// those works similar to gluPerspective
		
		final CameraComponent camera = Cameras.getMainCamera();
		
		final Matrix4f modelView = new Matrix4f().identity();
		
		// move model far from eye position
		modelView.translate(this.position.x(), this.position.y(), this.position.z());
		
		//		modelView.scale(position.scaleX, position.scaleY, position.scaleZ);
		//		modelView.scale(1/camera.getWidth(), 1/camera.getHeight(), 1);
		
		// rotate model a little by x an y axis, to see cube in projection
		modelView.rotateXYZ(this.position.getRotateX(), this.position.getRotateY(), this.position.getRotateZ());
		
		final Matrix4f normal = new Matrix4f();
		modelView.normal(normal);
		
		// take MVP
		final Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(camera.getProjection()).mul(modelView);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			this.program.start();
			
			final FloatBuffer nm = stack.callocFloat(16);
			normal.get(nm);
			final FloatBuffer mvp = stack.callocFloat(16);
			modelVeiwProjection.get(mvp);
			
			this.mvpUL.glUniformMatrix4fv(false, mvp);
			this.nmUL.glUniformMatrix4fv(false, nm);
			
			
			this.vao.bind();
			
			Graphics.glDrawElements(GLPrimitive.TRIANGLES, this.lengthIndecies, GLType.UNSIGNED_INT);
			this.vao.unbind();
			this.program.stop();
		}
	}
	
	@Override
	public void start() {
		super.start();
		
		final IndeciesArray mesh = this.getComponent(AbstractMeshComponent.class).getMesh().get(Type.VERTEX, Type.NORMAL);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			
			this.vao = new GLVertexArray();
			
			final VideoBuffer vbo = this.program.createVideoBuffer(stack.floats(mesh.getVertex()), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			final VideoBuffer vio = this.program.createVideoBuffer(stack.ints(mesh.getIndecies()), VideoBuffer.Type.ELEMENT_ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			this.lengthIndecies = mesh.getIndecies().length;
			
			this.vao.bind();
			vio.bind();
			
			this.program.passVertexAttribArray(vbo, false, Attribute.of("vertex_coord", 3), Attribute.of("vertex_normal", 3));
			
			this.vao.unbind();
		}catch(final Exception e) {
			e.printStackTrace();
		}
		
		// now we need to link a program, after that we can not build VAO
		this.program.link();
		
		// now we can locate uniforms from program
		this.mvpUL = this.program.getUniformLocation("mvp");
		this.nmUL  = this.program.getUniformLocation("nm");
	}
	
}
