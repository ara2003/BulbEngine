package com.greentree.engine;

import com.greentree.common.concurent.MultyTask;
import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.RootFiles;
import com.greentree.engine.core.SceneLoader;
import com.greentree.engine.core.object.GameScene;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Game3D extends GameCore {

	public static void exit() {
		Windows.getWindow().shouldClose();
	}

	public static void start(final String folder, final String[] args) {
		RootFiles.start(folder);
		GameCore.setBuilder(new BasicXMlBuilder());
		
		bootstrap("bootstrap-scene");
		
		Windows.getWindow().makeCurrent();
		Graphics.clearColor(.6f, .6f, .6f);
		Graphics.setClearDepth(1.0);
		
		Mouse.getMouseX();//static constructor
		
		KeyBoard.init();
//		SceneLoader.loadScene(Properties.getProperty("scene.first").notNull().get());
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().swapBuffer();
			Graphics.glClearAll();
			Windows.getWindow().updateEvents();
			GameCore.gameLoop();
		}
		MultyTask.shutdown();
		BulbGL.terminate();
	}

	private static void bootstrap(String file) {
		GameScene scene = SceneLoader.loadScene(file);
		while(scene.isCurrent()) {
			GameCore.gameLoop();
		}
	}

	public static void start(final String[] args) {
		Game3D.start("Game", args);
	}

	protected Game3D() {
	}
}
