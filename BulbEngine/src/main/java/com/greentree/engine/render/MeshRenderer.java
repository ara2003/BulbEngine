package com.greentree.engine.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.engine.Cameras;
import com.greentree.engine.component.AbstractMeshComponent;
import com.greentree.engine.component.Transform;
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
	@EditorData("matirial")
	private ShaderProgram program;

	// normal matrix uniform location
	private GLLocation nmUL;
	// model-view-projection matrix uniform location
	private GLLocation mvpUL;

	// vertex array object id
	private VertexArray vao;

	private int lengthIndecies;

	private Transform position;

	@Override
	public void render() {
		final Matrix4f modelView = position.getModelViewMatrix();
		
		final Matrix4f normal = new Matrix4f();
		modelView.normal(normal);

		// take MVP
		final Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(Cameras.getMainCamera().getProjection()).mul(modelView);
		
		try(MemoryStack stack = MemoryStack.create(32 * Float.BYTES).push()) {
			program.start();

			final FloatBuffer nm = stack.callocFloat(16);
			normal.get(nm);
			final FloatBuffer mvp = stack.callocFloat(16);
			modelVeiwProjection.get(mvp);

			mvpUL.glUniformMatrix4fv(false, mvp);
			nmUL.glUniformMatrix4fv(false, nm);

			vao.bind();

			Graphics.glDrawElements(GLPrimitive.TRIANGLES, lengthIndecies, GLType.UNSIGNED_INT);
			
			vao.unbind();
			program.stop();
		}
	}

	@Override
	public void start() {
		position = getComponent(Transform.class);
		
		final IndeciesArray mesh = this.getComponent(AbstractMeshComponent.class).getMesh().get(Type.VERTEX, Type.NORMAL);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			vao = new GLVertexArray();

			final VideoBuffer vbo = program.createVideoBuffer(stack.floats(mesh.getVertex()), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);

			final VideoBuffer vio = program.createVideoBuffer(stack.ints(mesh.getIndecies()), VideoBuffer.Type.ELEMENT_ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);

			lengthIndecies = mesh.getIndecies().length;

			vao.bind();
			vio.bind();

//			for(int i = 0; i < mesh.getVertex().length; i += 6) {
//				System.out.printf("%2d :", i/6);
//				for(int j = 0; j < 3; j++) 
//					System.out.printf("%2d ",Math.round(mesh.getVertex()[i+j]));
//				for(int j = 0; j < 3; j++) 
//					System.out.printf("%2d ",Math.round(mesh.getVertex()[i+j]));
//				System.out.println();
//			}
//			System.out.println(Arrays.toString(mesh.getIndecies()));

			program.passVertexAttribArray(vbo, false, Attribute.of("vertex_coord", 3), Attribute.of("vertex_normal", 3));

			vao.unbind();
		}catch(final Exception e) {
			e.printStackTrace();
		}

		// now we need to link a program, after that we can not build VAO
		program.link();

		// now we can locate uniforms from program
		mvpUL = program.getUniformLocation("mvp");
		nmUL  = program.getUniformLocation("nm");
	}
	
}
