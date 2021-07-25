package com.greentree.engine3d.particle;

import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.LinkedList;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.math.Mathf;
import com.greentree.common.math.vector.AbstractVector3f;
import com.greentree.common.math.vector.Vector3f;
import com.greentree.engine.component.AbstractMeshComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.util.Cameras;
import com.greentree.engine.util.Time;
import com.greentree.engine3d.render.Camera3DRendenerComponent;
import com.greentree.graphics.Color;
import com.greentree.graphics.GLPrimitive;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.Wrapping;
import com.greentree.graphics.shader.GLLocation;
import com.greentree.graphics.shader.GLVertexArray;
import com.greentree.graphics.shader.ShaderProgram;
import com.greentree.graphics.shader.ShaderProgram.Attribute;
import com.greentree.graphics.shader.VertexArray;
import com.greentree.graphics.shader.VideoBuffer;
import com.greentree.graphics.texture.Filtering;
import com.greentree.graphics.texture.GLTexture2D;

@RequireComponent({AbstractMeshComponent.class})
public class ParticleComponent extends Camera3DRendenerComponent {

	// OpenGL program object
	@EditorData("matirial")
	private ShaderProgram program;

	private GLLocation mvpUL;

	// vertex array object id
	private VertexArray vao;

	@Required
	@EditorData("image")
	protected GLTexture2D texture;
	private Collection<Particle> particles = new LinkedList<>();
	@EditorData
	private float size = 100f;
	@EditorData("life time")
	private float lifeTime = 30;
	@EditorData
	private Shape shape = Shape.SPHERE;
	@EditorData
	private float speed = 20f;
	@EditorData
	private Color color = Color.white;
	@EditorData("Rate over TimeUtil")
	private float RateOverTime = 150f;
	private float deltaTime;
	private Transform position;

	//	private final static Vector3f norm = new Vector3f(0, 0, -1);
	//	private final static Vector3f origin_norm = new Vector3f(0, 0, 1);

	@Override
	public void render() {
		final Matrix4f modelView = position.getModelViewMatrix();
		Matrix4f modelVeiwProjection = new Matrix4f().identity().mul(Cameras.getMainCamera().getProjection()).mul(modelView);
		try(MemoryStack stack = MemoryStack.create(Float.BYTES * 16 * particles.size()).push()) {
			program.start();
			vao.bind();
			//			Graphics.disableCullFace();

			for(Particle particle : particles) {
				var old = new Matrix4f(modelVeiwProjection);
				modelVeiwProjection.translate(particle.x, particle.y, particle.z);

				//				Vector3f norm = sub(Cameras.getMainCamera().position.xyz(), new Vector3f(particle.x+position.x(), particle.y+position.y(), particle.z+position.z()));
				//				norm.normalize();

				//				System.out.println(Cameras.getMainCamera().position.xyz() + "-" + new Vector3f(particle.x, particle.y, particle.z) + "=" +norm);

				//				modelVeiwProjection.rotate(-Mathf.acos(origin_norm.dot(norm)), norm.cross(origin_norm, new Vector3f()));

				final FloatBuffer mvp = stack.callocFloat(16);
				modelVeiwProjection.get(mvp);
				mvpUL.glUniformMatrix4fv(false, mvp);

				Graphics.glDrawArrays(GLPrimitive.QUADS, 0, 4);

				modelVeiwProjection = old;
			}
			//			Graphics.enableCullFace();
			vao.unbind();
			program.stop();
		}
		updateParticls();
	}

	@Override
	public void start() {
		position = getComponent(Transform.class);

		texture.setMagFilter(Filtering.LINEAR);
		texture.setMinFilter(Filtering.NEAREST);

		texture.setWrap(Wrapping.CLAMP_TO_BORDER);

		try(MemoryStack stack = MemoryStack.stackPush()) {
			vao = new GLVertexArray();

			final VideoBuffer vbo = program.createVideoBuffer(stack.floats(Graphics.array2f), VideoBuffer.Type.ARRAY_BUFFER, VideoBuffer.Usage.STATIC_DRAW);

			vao.bind();

			program.passVertexAttribArray(vbo, false, Attribute.of("vertex_coord", 2));

			vao.unbind();
		}catch(final Exception e) {
			e.printStackTrace();
		}

		// now we need to link a program, after that we can not build VAO
		program.link();

		// now we can locate uniforms from program
		mvpUL = program.getUniformLocation("mvp");
	}

	private void updateParticls() {
		for(Particle particle : particles) {
			particle.time += Time.getDelta();
			particle.x += particle.speedx*Time.getDelta();
			particle.y += particle.speedy*Time.getDelta();
			particle.z += particle.speedz*Time.getDelta();
		}
		particles.removeIf(p -> p.time > lifeTime);
		deltaTime += Time.getDelta();
		while(deltaTime > 0) {
			deltaTime -= 1f / RateOverTime;
			particles.add(new Particle(shape.nextSpeed().mul(speed)));
		}
	}

	protected static class Particle {
		public float x, y, z, speedx, speedy, speedz, time, scale = 1;

		public Particle(AbstractVector3f speed) {
			speedx = speed.x();
			speedy = speed.y();
			speedz = speed.z();
		}
		public Particle(AbstractVector3f position, AbstractVector3f speed) {
			this(speed);
			x = position.x();
			y = position.y();
			z = position.z();
		}

		@Override
		public String toString() {
			return "Particle [x=" + x + ", y=" + y + ", z=" + z + ", speedx=" + speedx + ", speedy=" + speedy + ", speedz=" + speedz + ", time=" + time + "]";
		}

	}

	public enum Shape{
		SPHERE{
			@Override
			protected AbstractVector3f nextSpeed() {
				return new Vector3f(Mathf.random()*2-1, Mathf.random()*2-1, Mathf.random()*2-1).normalize();
			}
		};

		protected abstract AbstractVector3f nextSpeed();
	}

}
