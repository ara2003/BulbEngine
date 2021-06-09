package com.greentree.engine.collizion.event;

import java.util.Objects;

import com.greentree.common.collection.DoubleSet;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.event.Event;

public class CollisionListEvent implements Event {

	private static final long serialVersionUID = 1L;
	private final DoubleSet<ColliderComponent> collection;

	public CollisionListEvent(final DoubleSet<ColliderComponent> collection) {
		this.collection = collection;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		CollisionListEvent other = (CollisionListEvent) obj;
		if(!Objects.equals(collection, other.collection)) {
			return false;
		}
		return true;
	}

	public DoubleSet<ColliderComponent> getCollection() {
		return collection;
	}

	@Override
	public int hashCode() {
		return Objects.hash(collection);
	}

	@Override
	public String toString() {
		return "CollisionListEvent [collection=" + collection + "]";
	}
}
