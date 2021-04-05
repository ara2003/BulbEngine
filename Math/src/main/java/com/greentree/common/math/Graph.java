package com.greentree.common.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Graph<E> implements Collection<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Set<Integer>> joint = new ArrayList<>();
	protected final CopyOnWriteArrayList<E> point = new CopyOnWriteArrayList<>();
	public Graph() {
	}
	
	public Graph(final Collection<E> t) {
		addAll(t);
	}
	
	public boolean add(final E t) {
		if(t == null) return false;
		boolean flag = this.point.add(t);
		if(flag)this.joint.add(new HashSet<>());
		return flag;
	}
	
	public final void add(final E i, final E j) {
		add(getPoint(i), getPoint(j));
	}
	
	protected void add(final int i, final int j) {
		this.joint.get(i).add(j);
		//this.joint.get(j).add(i);
	}
//	
//	public void addAll(final Collection<E> t) {
//		for(int i = 0; i < t.size(); i++) this.joint.add(new HashSet<>());
//		this.point.addAll(t);
//	}
//	
//	public boolean contains(final E e) {
//		return this.point.contains(e);
//	}
//	
	@Override
	public boolean equals(final Object obj) {
		if(!(obj instanceof Graph)) return false;
		if(obj == this) return true;
		final Graph<?> g = (Graph<?>) obj;
		if(!point.equals(g.point)) return false;
		if(!joint.equals(g.joint)) return false;
		return true;
	}
	
	protected E get(final int v) {
		return this.point.get(v);
	}
	
	public int getConected(final E v1, final E v2) {
		final int f = getPoint(v2);
		final Set<Integer> used = new HashSet<>(point.size());
		final List<pair<Integer, Integer>> list = new LinkedList<>();
		list.add(new pair<>(getPoint(v1), 0));
		while(!list.isEmpty()) {
			final pair<Integer, Integer> p = list.remove(0);
			final int v = p.first;
			if(v == f) return p.seconde;
			for(final int to : joint.get(v)) {
				if(used.contains(to)) continue;
				used.add(to);
				list.add(new pair<>(to, p.seconde + 1));
			}
		}
		return -1;
	}
	
	public E getElemeny(final int i) {
		return this.point.get(i);
	}
	
	public final int getPoint(final E e) {
		return this.point.indexOf(e);
	}
	
	public int howJoint() {
		int n = 0;
		for(final Set<Integer> l : this.joint) n += l.size();
		return n / 2;
	}
	
	public boolean isEmpty() {
		return this.point.isEmpty();
	}
	
	public boolean isTree() {
		boolean f = this.point.size() == (howJoint() + 1);
		for(final Set<Integer> l : this.joint) if(f) if(l.isEmpty()) f = false;
		return f;
	}
	
	public void printTree(final E root, final Writer<E> w) throws MathException {
		printTree(this.point.indexOf(root), w);
	}
	
	public void printTree(final int root, final Writer<E> w) throws MathException {
		if(!isTree()) throw new MathException("not tree " + this);
		else if(isEmpty()) return;
		else printTree0(root, -1, 0, w);
	}
	
	private void printTree0(final int v, final int last, final int d, final Writer<E> w) {
		for(int i = 0; i < d; i++) System.out.print("- ");
		w.print(get(v));
		for(final int to : this.joint.get(v)) {
			if(to == last) continue;
			printTree0(to, v, d + 1, w);
		}
	}
	
	public int size() {
		return this.point.size();
	}
	
	@Override
	public String toString() {
		return "ppoints = " + size() + " j = " + howJoint() + " " + point + "\n" + joint;
	}
	
	public Tree<E> toTree(final E root) {
		return toTree(getPoint(root));
	}
	
	private Tree<E> toTree(final int root) {
		if(!isTree()) return null;
		final Tree<E> t = new Tree<>();
		t.addAll(this.point);
		t.setRoot(root);
		toTree0(root, -1, t);
		return t;
	}
	
	private void toTree0(final int v, final int last, final Tree<E> t) {
		for(final int to : this.joint.get(v)) {
			if(to == last) continue;
			t.add(v, to);
			toTree0(to, v, t);
		}
	}
	
	public void trim() {
	}
	
	public CopyOnWriteArrayList<E> values() {
		return this.point;
	}
	
	public interface Writer<T> {
		
		void print(T data);
	}

	@Override
	public boolean contains(Object o) {
		return point.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return point.iterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
	}
}

class pair<A, B> {
	
	A first;
	B seconde;
	
	pair(final A a, final B b) {
		pair.this.first = a;
		pair.this.seconde = b;
	}
}
