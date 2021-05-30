package com.greentree.common.graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.greentree.common.logger.Log;
import com.greentree.common.logger.Logger;

public class Graph<V> implements Collection<V> {

    static {
    	try {
			Log.createFileType("d");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	private final Map<V, Set<V>> joint;

	public Graph() {
		joint = new HashMap<>();
	}

	@Override
	public boolean add(V e) {
		if(joint.containsKey(e))return false;
		joint.put(e, new HashSet<>());
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		boolean flag = false;
		for(V e : c) if(add(e))flag = true;
		return flag;
	}

	public void addJoint(V a, V b){
		if(a.equals(b))throw new IllegalArgumentException("Joint a==b ("+a+")");
		if(!joint.containsKey(a))add(a);
		if(!joint.containsKey(b))add(b);
		joint.get(a).add(b);
		joint.get(b).add(a);
	}

	@Override
	public void clear() {
		joint.clear();
	}

	@Override
	public boolean contains(Object o) {
		return joint.containsKey(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return joint.keySet().containsAll(c);
	}

	public List<Cycle<V>> getCycle() {
		return new CycleFinder().get();
	}

	public Graph<V>.PathFinder getPathFinder(Function<Joint<V>, Double> w) {
		return new PathFinder(w);
	}

	public Collection<V> getVertex() {
		return joint.keySet();
	}

	@Override
	public boolean isEmpty() {
		return joint.isEmpty();
	}

	@Override
	public Iterator<V> iterator() {
		return joint.keySet().iterator();
	}

	@Override
	public boolean remove(Object o) {
		if(joint.remove(o) != null) {
			for(Set<V> l : joint.values()) {
				l.remove(o);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag = false;
		for(Object e : c) if(remove(e))flag = true;
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return joint.keySet().retainAll(c);
	}

	@Override
	public int size() {
		return joint.size();
	}

	@Override
	public Object[] toArray() {
		return joint.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return joint.keySet().toArray(a);
	}

	@Override
	public String toString() {
		return "Graph" + joint;
	}

	private class CycleFinder {
		Map<V, Boolean> color = new HashMap<>();
		private final Map<V, V> p = new HashMap<>();
		List<Cycle<V>> res = new ArrayList<>();

		public CycleFinder() {
			for(V v : joint.keySet()) color.put(v, false);
		}

		private Cycle<V> add(V end, V st) {
			List<V> cycle = new ArrayList<>();
			for(V v = end;; v = p.get(v)) {
				//				System.out.println();
				cycle.add(v);
				if(v.equals(st))break;
			}
			Cycle<V> c0 = new Cycle<>(cycle);
			if(!res.contains(c0))
				res.add(c0);
			return c0;
		}

		private void dfs(V v) {
			color.put(v, true);
			for(V to : joint.get(v)) if(!color.get(to)) {
				p.put(to, v);
				dfs(to);
			}else if(color.get(to)) add(v, to);
			color.put(v, false);
		}

		public List<Cycle<V>> get() {
			for(V v : joint.keySet()) dfs(v);
			res.removeIf(e -> (e.size() < 3));
			return res;
		}
	}

//	public static void main(String[] args) {
//		Graph<String> g = new Graph<>();
//
//		g.addJoint("A", "B");
//		g.addJoint("C", "B");
//		g.addJoint("C", "D");
//		g.addJoint("E", "D");
//		
//		System.out.println(g.getPathFinder(e -> 1D).get("A1", "E1"));
//	}
	
	/**
	 * <a href="https://e-maxx.ru/algo/floyd_warshall_algorithm">floyd-warshall algorithm</a>
	 *
	 */
	public class PathFinder {
		private final Map<V, Map<V, V>> p = new HashMap<>();
		private final Function<Joint<V>, Double> function;

		public PathFinder(Function<Joint<V>, Double> function) {
			this.function = function;
			final Map<V, Map<V, Double>> d = new HashMap<>();
			for (V a : Graph.this.joint.keySet()) {
				Map<V, Double> mapd = new HashMap<>();
				Map<V, V> mapp = new HashMap<>();
				d.put(a, mapd);
				p.put(a, mapp);
				for (V b : Graph.this.joint.get(a)) {
					mapp.put(b, a);
					mapd.put(b, function.apply(new Joint<>(a, b)));
				}
				for (V b : Graph.this.joint.keySet()) if(!mapd.containsKey(b)) {
					if(a.equals(b)) {
						mapd.put(b, 0d);
					}
				else
					mapd.put(b, Double.MAX_VALUE);
				}
			}
			for (V a : Graph.this.joint.keySet()) {
				Map<V, Double> map0 = d.get(a);
				for (V b : Graph.this.joint.keySet()) for (V k : Graph.this.joint.keySet()) {
					double new_d = map0.get(k)+d.get(b).get(k);
					if(map0.get(b) > new_d) {
						map0.put(b, new_d);
						p.get(a).put(b, k);
					}
				}
			}
			Log.print("d", "%s", d.toString());
			Log.print("d", "%s", p.toString());
		}
		
		
		public Path<V> get(V a, V b) {
			if(a == null)throw new NullPointerException("a is null");
			if(b == null)throw new NullPointerException("b is null");
			if(!joint.containsKey(b))throw new IllegalArgumentException("graph not contains " + a);
			if(!joint.containsKey(b))throw new IllegalArgumentException("graph not contains " + b);
			if(a.equals(b)) {
				List<V> res = new ArrayList<>();
				res.add(a);
				return new Path<>(res, function);
			}
			if(!p.get(a).containsKey(b)) return null;
			var res = get0(a, b);
			return new Path<>(res, function);
		}
		
		private List<V> get0(V a, V b) {
			if(p.get(a).get(b).equals(a)) {
				List<V> res = new ArrayList<>();
				res.add(a);
				res.add(b);
				return res;
			}
			var k = p.get(a).get(b);
			List<V> res1 = get0(a, k);
			List<V> res2 = get0(k, b);
			res2.remove(0);
			res1.addAll(res2);
			return res1;
		}

	}

	public Set<V> getJoints(V v) {
		return joint.get(v);
	}

}
