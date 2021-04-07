package com.greentree.engine.component.render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryStack;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.DataType;
import com.greentree.bulbgl.GPrimitive;
import com.greentree.bulbgl.shader.Location;
import com.greentree.bulbgl.shader.ShaderProgram;
import com.greentree.bulbgl.shader.ShaderProgram.Attribute;
import com.greentree.bulbgl.texture.Texture2D;
import com.greentree.bulbgl.shader.VertexArray;
import com.greentree.bulbgl.shader.VideoBuffer;
import com.greentree.engine.Cameras;
import com.greentree.engine.Windows;
import com.greentree.engine.component.AbstractMeshComponent;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.mesh.Mesh.IndeciesArray;
import com.greentree.engine.mesh.Mesh.Type;

/** @author Arseny Latyshev */
@RequireComponent({AbstractMeshComponent.class})
public class MeshRenderer extends CameraRendenerComponent {
	
	// OpenGL program object
	@EditorData(name = "matirial")
	private ShaderProgram program;
	
	// normal matrix uniform location
	private Location nmUL;
	// model-view-projection matrix uniform location
	private Location mvpUL;
	
	protected Texture2D texture;
	
	// vertex array object id
	private VertexArray vao;

	private int lengthIndecies;
	
	@Override
	protected void start() {
		super.start();

		IndeciesArray mesh = getComponent(AbstractMeshComponent.class).getMesh().get(Type.VERTEX, Type.NORMAL);
			
		try (MemoryStack stack = MemoryStack.stackPush()) {

			vao = BulbGL.getShaderLoader().createVertexArray();
			
			VideoBuffer vbo = program.createVideoBuffer(stack.floats(mesh.getVertex()), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			VideoBuffer vio = program.createVideoBuffer(stack.ints(mesh.getIndecies()), VideoBuffer.Type.ELEMENT_ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			lengthIndecies = mesh.getIndecies().length;
			
			vao.bind();
			vio.bind();

			program.passVertexAttribArray(vbo, false, Attribute.of("vertex_coord", 3), Attribute.of("vertex_normal", 3));
			
			vao.unbind();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// now we need to link a program, after that we can not build VAO
		program.link();

		// now we can locate uniforms from program
		mvpUL = program.getUniformLocation("mvp");
		nmUL = program.getUniformLocation("nm");
	}
	
	@Override
	public void render() {
		
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_BACK);
//		glEnable(GL_DEPTH_TEST);
		
		// calculate perspective for our context
		// those works similar to gluPerspective
		
		Camera camera = Cameras.getMainCamera();
		
		final Matrix4f modelView = new Matrix4f().identity();
		
		// move model far from eye position
		modelView.translate(position.x(), position.y(), position.z());

//		modelView.scale(position.scaleX, position.scaleY, position.scaleZ);
//		modelView.scale(1/camera.getWidth(), 1/camera.getHeight(), 1);
		
		// rotate model a little by x an y axis, to see cube in projection
		modelView.rotateXYZ(position.rotateX, position.rotateY, position.rotateZ);
		
		final Matrix4f normal = new Matrix4f();
		modelView.normal(normal);
		
		// take MVP
		final Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(camera.getProjection()).mul(modelView);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			program.start();

			FloatBuffer nm = stack.callocFloat(16);
			normal.get(nm);
			FloatBuffer mvp = stack.callocFloat(16);
			modelVeiwProjection.get(mvp);

			mvpUL.glUniformMatrix4fv(false, mvp);
			nmUL.glUniformMatrix4fv(false, nm);
			

			vao.bind();
			
			BulbGL.getGraphics().glDrawElements(GPrimitive.TRIANGLES, lengthIndecies, DataType.UNSIGNED_INT);
			vao.unbind();
			program.stop();
		}
	}
	
}
