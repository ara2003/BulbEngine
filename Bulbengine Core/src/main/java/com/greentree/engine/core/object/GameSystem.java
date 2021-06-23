package com.greentree.engine.core.object;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.engine.core.Events;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.component.ComponentList;
import com.greentree.engine.core.component.GameComponent;
import com.greentree.engine.core.component.NewComponentListener;
import com.greentree.event.Listener;

public abstract class GameSystem  {

	private boolean isDestoy = false;
	private boolean isStart = false;

	protected final static void addListener(final Listener listener) {
		Events.addListener(listener);
	}

	@SuppressWarnings("unchecked")
	public static <T> void addNewComponentListener(Class<T> clazz, Consumer<T> consumer) {
		addListener((NewComponentListener)c-> {
			if(clazz.isAssignableFrom(c.getClass()))consumer.accept((T) c);
		});
	}

	protected final static GameObject createFromPrefab(final String name, final String prefab) {
		return GameCore.createFromPrefab(name, prefab);
	}
	protected final static GameObject createFromPrefab(final String prefab) {
		return createFromPrefab(prefab, prefab);
	}

	protected boolean destroy() {
		if(isDestroy())return true;
		isDestoy = true;
		return false;
	}
	
	protected final <T> List<T> getAllComponents(final Class<T> clazz) {
		return GameCore.getCurrentScene().getAllComponents(clazz);
	}

	protected final <T extends GameComponent> ComponentList<T> getAllComponentsAsComponentList(final Class<T> clazz) {
		return GameCore.getCurrentScene().getAllComponentsAsComponentList(clazz);
	}

	public final void initSratr() {
		if(isStart) throw new UnsupportedOperationException("reinitialization of : " + this);
		isStart = true;
		start();
	}

	public final boolean isDestroy() {
		return isDestoy;
	}

	protected void start() {
	}

	public void update() {
	}
}
