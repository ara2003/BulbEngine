package com.greentree.engine.component;

import org.joml.Vector2f;

import com.greentree.common.time.Time;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.engine.core.component.DefoultValue;
import com.greentree.engine.core.component.EditorData;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.UpdatingGameComponent;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.geom2d.GeomUtil2D;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.phisic.Matirial;
import com.greentree.engine.system.PhisicSystem;

@RequireComponent({Transform.class,ColliderComponent.class})
@NecessarilySystems({PhisicSystem.class})
@Deprecated
public class PhisicComponent extends UpdatingGameComponent {
	
	private Transform position;
	@EditorData
	private float mass = 1;
	private Point2D massCenter;
	@DefoultValue("Assets\\iron")
	@EditorData()
	private Matirial matirial;
	@EditorData(name = "typeCollizion")
	private Type type = Type.DINAMIC;
	private Vector2f velosity = new Vector2f();
	private float rotationVelosity = 0f;
	
	public void additionVelosity(final Point2D p, final Vector2f float2f) {
		this.rotationVelosity += float2f.dot(this.normalTo(p));
		this.setVelosity(float2f.add(this.velosity));
	}
	
	public void additionVelosity(final Vector2f vec) {
		this.velosity.add(vec);
	}
	
	public float getElasticity() {
		return this.matirial.getElasticity();
	}
	
	public float getMass() {
		return this.mass;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public Vector2f getVelosity() {
		return this.velosity;
	}
	
	private Vector2f normalTo(final Point2D point) {
		return this.massCenter.getRadiusVector().sub(point.getRadiusVector());
	}
	
	public void setVelosity(final Vector2f velosity) {
		if(this.type != Type.STATIC) this.velosity = velosity;
	}
	
	@Override
	public void start() {
		this.position = this.getComponent(Transform.class);
		if(this.matirial.getElasticity() < 0 || this.matirial.getElasticity() >= 1)
			throw new IllegalArgumentException("elasticity value " + this.matirial.getElasticity() + " not corect");
	}
	
	@Override
	public void update() {
		this.massCenter = GeomUtil2D.getMassCenter(this.getComponent(ColliderComponent.class).getShape());
		if(this.type == Type.DINAMIC) {
			this.position.xy().add(this.getVelosity().mul(Time.getDelta()));
			this.getComponent(ColliderComponent.class).getShape().rotate(this.massCenter, this.rotationVelosity);
		}
	}
	
	public enum Type{
		DINAMIC,STATIC;
	}
}
