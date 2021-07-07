package com.greentree.engine.component;

import org.joml.Matrix4f;

import com.greentree.common.math.Mathf;
import com.greentree.common.math.vector.VectorAction3f;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.StartGameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameObjectParent;

public class Transform extends StartGameComponent {

	@EditorData
	public final VectorAction3f scale = new VectorAction3f(1);

	
	@EditorData
	public final VectorAction3f rotation = new VectorAction3f(0);
	
	@EditorData
	public final VectorAction3f position = new VectorAction3f(0);
	
	@EditorData("static")
	private boolean isStatic = false;

	private static Matrix4f getModelViewMatrix0(GameObject obj) {
		Transform transform = obj.getComponent(Transform.class);
		final Matrix4f modelView = new Matrix4f();
		if(transform == null)return modelView;
		modelView.translate(transform.position.toJoml());
		modelView.scale(transform.scale.toJoml());
		modelView.rotateXYZ(transform.rotation.toJoml());
		return modelView;
	}
	
	
	public Matrix4f getModelViewMatrix() {
		final Matrix4f modelView = new Matrix4f();
		GameObjectParent par = getObject();
		while(par instanceof GameObject) {
			GameObject obj = (GameObject) par;
			modelView.mul(getModelViewMatrix0(obj));
			par = obj.getParent();
		}
		return modelView;
	}

	public boolean isStatic() {
		return isStatic;
	}


	@Override
	public void start() {
		if(Mathf.min(scale.x(), scale.y(), scale.z()) <= 1E-9)throw new RuntimeException("scale x|y|z == 0 " + getObject());
	}
}
