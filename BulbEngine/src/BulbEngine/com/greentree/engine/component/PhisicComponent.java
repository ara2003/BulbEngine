package com.greentree.engine.component;

import org.joml.Vector2f;

import com.greentree.engine.GameComponent;
import com.greentree.engine.Time;
import com.greentree.engine.necessarily;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.geom2d.GeomUtil2D;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.phisic.Matirial;
import com.greentree.engine.system.PhisicSystem;

@RequireComponent({Transform.class,ColliderComponent.class})
@necessarily({PhisicSystem.class})
@Deprecated
public class PhisicComponent extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private Transform position;
	@EditorData(def = "1")
	private float mass;
	private Point2D massCenter;
	@EditorData(def = "Assets\\iron")
	private Matirial matirial;
	@EditorData(def = "DINAMIC", name = "typeCollizion")
	private Type type;
	private Vector2f velosity = new Vector2f();
	private float rotationVelosity = 0f;
	
	public void additionVelosity(Vector2f float2f) {
		setVelosity(float2f.add(velosity));
	}
	
	public void additionVelosity(Point2D p, Vector2f float2f) {
		rotationVelosity += float2f.dot(normalTo(p));
		
		setVelosity(float2f.add(velosity));
	}
	
	public float getElasticity() {
		return matirial.getElasticity();
	}
	
	public float getMass() {
		return mass;
	}
	
	public Type getType() {
		return type;
	}
	
	public Vector2f getVelosity() {
		return velosity;
	}
	
	private Vector2f normalTo(Point2D point) {
		return massCenter.getRadiusVector().sub(point.getRadiusVector());
	}
	
	public void setVelosity(Vector2f velosity) {
		if(type != Type.STATIC)
			this.velosity = velosity;
	}
	
	@Override
	public void start() {
		position = getComponent(Transform.class);
		if((matirial.getElasticity() < 0) || (matirial.getElasticity() >= 1)) {
			throw new IllegalArgumentException("elasticity value " + matirial.getElasticity() + " not corect");
		}
	}
	
	@Override
	public void update() {
		massCenter = GeomUtil2D.getMassCenter(getComponent(ColliderComponent.class).getShape());
		if(type == Type.DINAMIC) {
			position.add(getVelosity().mul(Time.getDelta()));
			getComponent(ColliderComponent.class).getShape().rotate(massCenter, rotationVelosity);
		}
	}
	
	public enum Type{
		DINAMIC,STATIC;
	}
}
