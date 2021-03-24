package com.greentree.common;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arseny Latyshev
 *
 */
public class ObjectPool<E> {
	
	private final List<SoftReference<E>> buffer;
	
	public ObjectPool() {
		this.buffer = new ArrayList<>();
	}
	
	public final E get(){
		if(buffer.isEmpty()) return null;
		E obj = buffer.remove(0).get();
		if(obj == null)return get();
		return obj;
	}

	public final void add(E object) {
		if(object == null)throw new RuntimeException("object has already been deleted");
		if(contains(object))throw new RuntimeException("object already added");
		buffer.add(new SoftReference<>(object));
	}

	public boolean contains(E object) {
		return buffer.contains(new SoftReference<>(object));
	}
	
}
