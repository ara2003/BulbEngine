package com.greentree.engine.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.greentree.common.time.Time;
import com.greentree.engine.core.builder.Builder;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.system.GameSystem;
import com.greentree.engine.core.util.RootFiles;
import com.greentree.engine.core.util.SceneMananger;

public abstract class GameCore {

	protected static Builder builder;
	private static final ArgumentList arguments = new ArgumentList();

	public static void addArgumentConflict(String a, String b) {
		GameCore.arguments.addConflict(a, b);
	}

	public static void addArguments(String... arguments) {
		GameCore.arguments.add(arguments);
	}

	public static boolean addSystem(final GameSystem system) {
		return SceneMananger.getCurrentScene().addSystem(system);
	}
	public static GameObject createFromPrefab(final String prefab) {
		return GameCore.builder.createPrefab(null, prefab);
	}

	public static GameObject createFromPrefab(final String name, final String prefab) {
		return GameCore.builder.createPrefab(name, prefab);
	}

	public static Builder getBuilder() {
		return GameCore.builder;
	}

	public static GameScene getCurrentScene() {
		return SceneMananger.getCurrentScene();
	}

	public static boolean hasArguments(String arg) {
		return arguments.hasArguments(arg);
	}

	@Deprecated
	public static void loadScene(final String name) {
		SceneMananger.loadScene(name);
	}

	protected static void setBuilder(final Builder builder) {
		GameCore.builder = builder;
	}
	public static void start(final String file, final Builder builder, final String[] args) {
		addArguments(args);
		GameCore.builder = builder;
		RootFiles.start(file);
		while(true) {
			Time.updata();
			SceneMananger.getCurrentScene().update();
		}
	}
	private static class ArgumentList {

		private final HashSet<String> set = new HashSet<>();
		private final Map<String, String> conflict = new HashMap<>();

		public void add(String[] arguments) {
			var list = Arrays.asList(arguments);
			for(String s : list) if(list.contains(conflict.get(s))) throw new RuntimeException("conflict arguments \"" + s + "\" \"" + conflict.get(s) + "\"");
			set.addAll(list);
		}

		public void addConflict(String a, String b) {
			conflict.put(a, b);
			conflict.put(b, a);
		}

		public boolean hasArguments(String arg) {
			return set.contains(arg);
		}

	}

}
