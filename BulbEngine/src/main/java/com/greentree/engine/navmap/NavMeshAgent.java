package com.greentree.engine.navmap;

import java.util.Deque;
import java.util.LinkedList;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.system.RequireSystems;

@RequireSystems({NavMeshSystem.class})
@RequireComponent({Transform.class})
public class NavMeshAgent extends GameComponent {

	@EditorData
	private String type = "default";
	@EditorData
	private float speed;
	private final Deque<AbstractVector2f> path = new LinkedList<>();

	public void addPathPoint(AbstractVector2f point) {
		path.push(point);
	}

	public Deque<AbstractVector2f> getPath() {
		return path;
	}

	public float getRadius() {
		return 40;
	}

	public float getSpeed() {
		return speed;
	}

}
