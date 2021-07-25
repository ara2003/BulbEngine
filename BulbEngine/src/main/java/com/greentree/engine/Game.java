package com.greentree.engine;

import java.io.File;

import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.SceneMananger;
import com.greentree.engine.util.Windows;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;
import com.greentree.graphics.window.SimpleWindow;

/** @author Arseny Latyshev */
public class Game extends GameCore {

	public static void startGameLoop(){
		while(running) {
			gameLoop();
		}
	}
	
	private static final String BOOTSTRAP_SCENE = "bootstrap-scene";
	
	public static void gameLoop() {
		Windows.getWindow().swapBuffer();
		Graphics.glClearAll();
		SimpleWindow.updateEvents();
		SceneMananger.getCurrentSceneNotNull().update();
	}

	public static void main(String[] args) {
		Game.start(args);
		Game.gameLoop();
		Game.terminate();
	}

	public static void start(final String[] args) {
		RootFiles.start(new File("Build"));
		GameCore.setBuilder(new BasicXMlBuilder());
		
		if(SceneMananger.isCurrent( SceneMananger.loadScene(BOOTSTRAP_SCENE))) throw new UnsupportedOperationException("the boot-strap scene has not changed");
	}

	public static void terminate() {
		SceneMananger.terminate();
		BulbGL.terminate();
	}
}
