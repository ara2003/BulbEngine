package com.greentree.engine.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.object.GameElement;
import com.greentree.engine.object.GameObject;
import com.greentree.util.Log;

public abstract class GameComponent extends GameElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient boolean awake;
	private transient GameObject object;
	private Collection<Corutine> corutines;
	
	protected GameComponent() {
		corutines = new ArrayList<>();
	}
	
	protected void awake() {
	}

	protected final void startCorutine(Corutine corutine) {
		corutines.add(corutine);
	}
	
	public final void awake0(final GameObject object) {
		if(this.awake) {
			Log.error("second awake component " + object + ":" + this);
			return;
		}
		this.awake  = true;
		this.object = Objects.requireNonNull(object, "object is null");
		this.awake();
	}
	
	public final <T extends GameComponent> T getComponent(final Class<T> clazz) {
		return this.getObject().getComponent(clazz);
	}
	
	public GameObject getObject() {
		return this.object;
	}
	
	@Override
	protected void start() {
	}
	
	@Override
	public final String toString() {
		return "GameComponent[" + this.getClass().getSimpleName() + "]@" + this.hashCode();
	}

	@Override
	protected void update() {
	}
	
	public final void update0() {
		update();
		corutines.removeIf(e -> e.run());
	}
	
}
