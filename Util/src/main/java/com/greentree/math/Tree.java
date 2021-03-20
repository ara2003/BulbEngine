package com.greentree.math;

import java.util.ArrayList;

public class Tree<E> extends Graph<E> {
	
	private static final long serialVersionUID = 1L;
	private final ArrayList<Integer> perent = new ArrayList<>();
	private int root = -1;
	
	public Tree() {
	}
	
	public Tree(final E e) {
		addRoot(e);
	}
	
	@Override
	public boolean add(final E i) {
		this.perent.add(-1);
		return super.add(i);
	}
	
	@Override
	public void add(final int p, final int v) {
		this.perent.set(v, p);
		super.add(p, v);
	}
	
	public void addRoot(final E root) {
		add(root);
		setRoot(root);
	}
	
	@Override
	public int howJoint() {
		int res = -1;
		for(final Integer i : this.perent) if(i != -1) res++;
		return res;
	}
	
	@Override
	public boolean isTree() {
		boolean f = size() == (howJoint() + 1);
		for(final Integer l : this.perent) if(f) if(l == -1) f = false;
		return f;
	}
	
	/** O(n + )
	 * @param root
	 * @return */
	public Tree<E> newTree(final E root) {
		if(!isTree()) return null;
		final int r = getPoint(root);
		final Tree<E> tree = new Tree<>(root);
		for(int v = 0; v < point.size(); v++) if(perentOf(r, v)) tree.add(point.get(v));
		for(int v = 0; v < point.size(); v++) if(perentOf(r, v)) tree.add(point.get(perent.get(v)), point.get(v));
		return tree;
	}
	
	/** work O(n)
	 * @param p - perent
	 * @param v
	 * @return */
	public boolean perentOf(final int p, int v) {
		if(v == p) return false;
		do {
			if(v == p) return true;
			if(-1 == this.perent.get(v)) {
				System.out.println(point.get(v));
				return false;
			}
			v = this.perent.get(v);
		}while(v != this.root);
		return false;
	}
	
	public void printTree(final Writer<E> w) {
		try {
			printTree(this.root, w);
		}catch(final MathException e) {//never
			e.printStackTrace();
		}
	}
	
	public void setRoot(final E root) {
		setRoot(getPoint(root));
	}
	
	void setRoot(final int root) {
		this.root = root;
		this.perent.set(root, root);
	}
	
	public Tree<E> toTree() {
		if(isTree()) return this;
		return null;
	}
}
