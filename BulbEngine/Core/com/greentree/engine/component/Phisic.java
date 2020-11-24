package com.greentree.engine.component;

import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.XmlData;
import com.greentree.engine.phisic.Matirial;
import com.greentree.util.math.vector.float2f;

public class Phisic extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	public CircleColliderComponent col;
	@XmlData(def = "1")
	public float mass;
	@XmlData(name = "matirial")
	public Matirial mat;
	@XmlData(def = "DINAMIC", name = "typeCollizion")
	public Type type;
	public float2f vel;

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
	
	public enum Type{
		DINAMIC,STATIC;
	}
}
