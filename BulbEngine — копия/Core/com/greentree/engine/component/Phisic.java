package com.greentree.engine.component;

import com.greentree.engine.GameComponent;
import com.greentree.engine.Time;
import com.greentree.engine.component.collider.ColliderComponent;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.object.RequireComponent;
import com.greentree.engine.object.necessarily;
import com.greentree.engine.phisic.Matirial;
import com.greentree.engine.system.PhisicSystem;
import com.greentree.geom.GeomUtil;
import com.greentree.geom.Point;
import com.greentree.math.vector.float2f;

@RequireComponent({Transform.class,ColliderComponent.class})
@necessarily({PhisicSystem.class})
@Deprecated
public class Phisic extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	private Transform position;
	@EditorData(def = "1")
	private float mass;
	private Point massCenter;
	@EditorData(def = "Assets\\iron")
	private Matirial matirial;
	@EditorData(def = "DINAMIC", name = "typeCollizion")
	private Type type;
	private float2f velosity = new float2f();
	private float rotationVelosity = 0f;
	
	public void additionVelosity(float2f float2f) {
		setVelosity(float2f.addition(velosity));
	}
	
	public void additionVelosity(Point p, float2f float2f) {
		rotationVelosity += float2f.scalarMultiply(normalTo(p));
		
		setVelosity(float2f.addition(velosity));
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
	
	public float2f getVelosity() {
		return velosity;
	}
	
	private float2f normalTo(Point point) {
		return massCenter.getRadiusVector().multiply(-1).addition(point.getRadiusVector());
	}
	
	public void setVelosity(float2f velosity) {
		if(type != Type.STATIC)
			this.velosity = velosity;
	}
	
	@Override
	public void start() {
		if((matirial.getElasticity() < 0) || (matirial.getElasticity() >= 1)) {
			throw new IllegalArgumentException("elasticity value " + matirial.getElasticity() + " not corect");
		}
	}
	
	@Override
	public String toString() {
		return "[Phisic [position=" + position + ", mass=" + getMass() + ", matirial=" + matirial + ", type=" + type + ", velosity=" + getVelosity() + "]]";
	}
	
	@Override
	public void update() {
		massCenter = GeomUtil.getMassCenter(getComponent(ColliderComponent.class).getShape());
		if(type == Type.DINAMIC) {
			position.addition(getVelosity().multiply(Time.getDelta()));
			getComponent(ColliderComponent.class).getShape().rotate(massCenter, rotationVelosity);
		}
	}
	
	public enum Type{
		DINAMIC,STATIC;
	}
}
