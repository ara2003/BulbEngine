package com.greentree.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/** @author Arseny Latyshev */
@Deprecated
public class ConcurentArrayList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	private final Collection<E> toAdd, toDelete;
	private final Consumer<E> add;
	private final Runnable changed;

	public ConcurentArrayList(final Consumer<E> add,
			final Runnable changed) {
		this.toAdd      = new ArrayList<>();
		this.toDelete   = new ArrayList<>();
		this.add        = add;
		this.changed    = changed;
	}

	@Override
	public boolean add(final E e) {
		return this.toAdd.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		return this.toAdd.addAll(c);
	}

	@Override
	public void clear() {
		this.removeAll(this);
	}

	@Override
	public boolean contains(final Object o) {
		this.updateCollection();
		return super.parallelStream().anyMatch(e->e.equals(o));
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		this.updateCollection();
		return super.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		this.updateCollection();
		return super.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		this.updateCollection();
		return super.iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object o) {
		return this.toDelete.add((E) o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.toDelete.addAll((Collection<? extends E>) c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		this.updateCollection();
		return super.retainAll(c);
	}

	@Override
	public int size() {
		this.updateCollection();
		return super.size();
	}

	@Override
	public Object[] toArray() {
		this.updateCollection();
		return super.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		this.updateCollection();
		return super.toArray(a);
	}

	public void updateCollection() {
		boolean isChanged = false;
		if(this.toAdd.isEmpty() == false) {
			isChanged = true;
			this.toAdd.parallelStream().forEach(this.add);
			super.addAll(this.toAdd);
			this.toAdd.clear();
		}
		if(this.toDelete.isEmpty() == false) {
			isChanged = true;
			super.removeAll(this.toDelete);
			this.toDelete.clear();
		}
		if(isChanged) this.changed.run();
	}
}
