package com.greentree.engine.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.greentree.common.math.mathset.MathSetUtil;
import com.greentree.common.math.vector.AbstractVector;
import com.greentree.data.FileUtil;
import com.greentree.data.parse.Parser;
import com.greentree.event.Event;
import com.greentree.event.Listener;
import com.greentree.event.ListenerManager;

public abstract class BuildUtil {

	public static Collection<String> getAllClass(File file){
		if(file.isFile()) {
			Collection<String> list = new ArrayList<>();
			if("java".equals(FileUtil.getType(file))) if(!"module-info.java".equals(file.getName()))list.add(getName(file));
			return list;
		}
		if(file.isDirectory()) {
			Collection<String> list = new HashSet<>();
			for(var f : file.listFiles()) list.addAll(getAllClass(f));
			return list;
		}
		return new ArrayList<>();
	}

	private static String getName(File file) {
		return new JavaFile(file).getName();
	}

	public static void main(String[] args) {
		var file = new File("D:\\programing\\Java");
		var res = getAllClass(file);
		class ClassTree {
			private final Map<Class<?>, Collection<Class<?>>> classes = new HashMap<>();

			public void add(Class<?> c) {
				var sup = c.getSuperclass();
				if(sup != null) {
					put(sup, c);
					add(sup);
				}
				for(var in : c.getInterfaces()) {
					put(in, c);
					add(in);
				}
			}

			private void tryadd(Class<?> c) {
				if(classes.get(c) == null) {
					classes.put(c, new HashSet<Class<?>>());
				}
			}

			private void put(Class<?> sup, Class<?> c) {
				tryadd(c);
				tryadd(sup);
				classes.get(sup).add(c);
			}

			@Override
			public String toString() {
				return "size "+classes.keySet().size() +"\n"+ toString(Object.class);
			}

			public String toString(Class<?> root) {
				var r = classes.get(root);
				if(r != null) {
					StringBuilder b = new StringBuilder(root.toString() + " " + r.size());
					for(var c : r) {
						b.append('\n');
						b.append('\t');
						b.append(toString(c).replace("\n", "\n\t"));
						b.append(' ');
					}
					return b.toString();
				}
				return root.toString();
			}

			@SuppressWarnings("unchecked")
			public <T> Collection<Class<? extends T>> get(Class<T> class1) {
				Collection<Class<? extends T>> res = new CopyOnWriteArraySet<>();
				res.add(class1);
				for(var a : classes.get(class1))res.add((Class<? extends T>) a);
				int count;
				do {
					count = 0;
					for(var b : res)
    					for(var a : classes.get(b)) {
    						if(res.add((Class<? extends T>) a))count++;
    					}
				}while(count > 0);
				return res;
			}

			public Collection<?> get(Predicate<Class<?>> function) {
				return classes.keySet().parallelStream().filter(function).collect(Collectors.toList());
			}
		}
		ClassTree classTree = new ClassTree();
		for(var nc : res) try {
			var c = Class.forName(nc, false, BuildUtil.class.getClassLoader());
			classTree.add(c);
		}catch(ClassNotFoundException e) {
		}
		{
    		Collection<?> res0 = classTree.get(Serializable.class);
    		res0 = MathSetUtil.diff(res0, classTree.get(Event.class));
    		res0 = MathSetUtil.diff(res0, classTree.get(Listener.class));
    		res0 = MathSetUtil.diff(res0, classTree.get(ListenerManager.class));

    		res0 = MathSetUtil.diff(res0, classTree.get(AbstractVector.class));
    		res0 = MathSetUtil.diff(res0, classTree.get(Parser.class));
    		res0 = MathSetUtil.diff(res0, classTree.get(c -> c.getPackageName().startsWith("java")));
    		res0 = MathSetUtil.diff(res0, classTree.get(c -> c.getAnnotation(Deprecated.class) != null));
    		
    		System.out.println(res0);
    		System.out.println(res0.size());
		}
	}



}
