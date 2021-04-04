package com.greentree.engine.component.render;

import static com.greentree.bulbgl.shader.ShaderProgram.Attribute.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import com.greentree.bulbgl.BulbGL;
import com.greentree.bulbgl.DataType;
import com.greentree.bulbgl.shader.Location;
import com.greentree.bulbgl.shader.ShaderProgram;
import com.greentree.bulbgl.shader.VertexArray;
import com.greentree.bulbgl.shader.VideoBuffer;
import com.greentree.common.time.Time;
import com.greentree.engine.Windows;
import com.greentree.engine.component.MeshComponent;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.mesh.Mesh;

/** @author Arseny Latyshev */
@RequireComponent({MeshComponent.class})
public class MeshRenderer extends Camera3DRendenerComponent {
	
	// OpenGL program object
	@EditorData(name = "matirial")
	private ShaderProgram program;
	private Mesh mesh;
	
	// vertex array object id
	private VertexArray vao;
	
	// model-view-projection matrix uniform location
	private Location mvpUL;
	
	
	private float x_rotation = 20;
	private float y_rotation = 45;
	
	@Override
	protected void start() {
		super.start();
		mesh = getComponent(MeshComponent.class).getMesh();
	
			// Create vertex array buffer
			vao = BulbGL.getShaderLoader().createVertexArray();
			
			// Create vertex buffer object
			VideoBuffer vbo = program.createVideoBuffer(mesh.getVertexAndTexCoordAndNormal(), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			// Create vertex index buffer object
			VideoBuffer vio = program.createVideoBuffer(mesh.getIndecies(), VideoBuffer.Type.ELEMENT_ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);
			
			// now bind them both to vao
			vao.bind();
			vio.bind();
			// bind VBO to shader attributes
			program.passVertexAttribArray(vbo, false, of("Position_VS_in", 3), of("TexCoord_VS_in", 2), of("Normal_VS_in", 3));
			// unbind vao, state will leave as it is, vio should not be unbound
			vao.unbind();
		
		// now we need to link a program, after that we can not build VAO
		program.link();
		
		// now we can locate uniforms from program
		mvpUL = program.getUniformLocation("gWorld");
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
		x_rotation += 10 * Time.getDelta();
		y_rotation += 20 * Time.getDelta();
		// calculate normal matrix to be used in Phong shading
		final Matrix4f normal = new Matrix4f();
		modelView.normal(normal);
		
		// take MVP
		final Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(projection).mul(modelView);
			
		// take matrix data native pointers
		FloatBuffer nm = FloatBuffer.allocate(16);
		normal.get(nm);
		FloatBuffer mvp = FloatBuffer.allocate(16);
		modelVeiwProjection.get(mvp);
		
		// render
		program.start();
		
		mvpUL.glUniformMatrix4fv(false, mvp);
		
		vao.bind();
		BulbGL.getGraphics().glDrawTriangles(mesh.getAmountVertices(), DataType.UNSIGNED_SHORT);
		vao.unbind();
		
		program.stop();
		
	}
	
	
}
