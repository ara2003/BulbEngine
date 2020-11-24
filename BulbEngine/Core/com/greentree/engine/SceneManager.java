package com.greentree.engine;

import com.greentree.engine.object.Scene;
import com.greentree.util.xml.XMLElement;
import com.greentree.util.xml.XMLParser;

public final class SceneManager {

	private SceneManager() {
	}
	
	public static Scene getScene(final String name) {
		Log.info("Scene load : " + name);
		final XMLElement in = XMLParser.parse(Game.getRoot(), name + ".scene");
		return new Scene(in);
	}
	
	public static void loadScene(final String name) {
		new Thread(()-> {
			Log.info("Scene load : " + name);
			final XMLElement in = XMLParser.parse(Game.getRoot(), name + ".scene");
			final Scene scene = new Scene(in);
			synchronized(Game.getGlobalCock()) {
				Game.setScene(scene);
			}
			Runtime.getRuntime().runFinalization();
		}, "scene " + name + " is loading").start();
	}
}
