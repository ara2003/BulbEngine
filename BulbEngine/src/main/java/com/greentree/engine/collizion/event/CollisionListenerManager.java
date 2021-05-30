package com.greentree.engine.collizion.event;

import java.util.ArrayList;

import com.greentree.common.collection.DoubleSet;
import com.greentree.common.pair.UnOrentetPair;
import com.greentree.engine.collizion.ColliderComponent;
import com.greentree.event.AbstractListenerManager;
import com.greentree.event.Listener;

/** @author Arseny Latyshev */
public class CollisionListenerManager extends AbstractListenerManager<CollisionListEvent, Listener> {

	private final DoubleSet<ColliderComponent> lastFream = new DoubleSet<>();
	private final DoubleSet<ColliderComponent> nowFream = new DoubleSet<>();
	private final ArrayList<DoubleCollisionListener> listeners = new ArrayList<>();
	private final ArrayList<CollisionListListener> listenersList = new ArrayList<>();

	@Override
	protected void event0(final CollisionListEvent event) {
		nowFream.clear();
		nowFream.addAll(event.getCollection());
		for(CollisionListListener l : listenersList)l.ColliderList(nowFream);
		for(final UnOrentetPair<ColliderComponent> p : nowFream) if(lastFream.remove(p)) {
			p.first.getAction().collizionStay(p.second);
			p.second.getAction().collizionStay(p.first);
			for(DoubleCollisionListener l : listeners)l.CollisionStay(p.first, p.second);
			for(DoubleCollisionListener l : listeners)l.CollisionStay(p.second, p.first);
		}else {
			p.first.getAction().collizionEnter(p.second);
			p.second.getAction().collizionEnter(p.first);
			for(DoubleCollisionListener l : listeners)l.CollisionEnter(p.first, p.second);
			for(DoubleCollisionListener l : listeners)l.CollisionEnter(p.second, p.first);
		}
		for(final UnOrentetPair<ColliderComponent> p : lastFream) {
			p.first.getAction().collizionExit(p.second);
			p.second.getAction().collizionExit(p.first);
			for(DoubleCollisionListener l : listeners)l.CollisionExit(p.first, p.second);
			for(DoubleCollisionListener l : listeners)l.CollisionExit(p.second, p.first);
		}
		lastFream.clear();
		lastFream.addAll(nowFream);
	}

	@Override
	protected boolean addListener0(Listener listener) {
		if(DoubleCollisionListener.class.isInstance(listener))return listeners.add((DoubleCollisionListener)listener);else
		if(CollisionListListener.class.isInstance(listener))return listenersList.add((CollisionListListener)listener);else
		return false;
	}
	
}
