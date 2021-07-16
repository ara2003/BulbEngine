package com.greentree.engine;

import java.io.File;

import com.greentree.common.concurent.MultyTask;
import com.greentree.common.time.Time;
import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.object.GameScene;
import com.greentree.engine.core.util.RootFiles;
import com.greentree.engine.core.util.SceneMananger;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Game extends GameCore {

	private static final String BOOTSTRAP_SCENE = "bootstrap-scene";

	public static void exit() {
		Windows.getWindow().shouldClose();
	}


	public static void gameLoop() {
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().swapBuffer();
			Graphics.glClearAll();
			Windows.getWindow().updateEvents();
			Time.updata();
			SceneMananger.getCurrentScene().update();
		}
	}

	public static void main(String[] args) {
		Game.start(args);
		Game.gameLoop();
		Game.terminate();
	}


	public static void start(final String[] args) {
		RootFiles.start(new File("Build"));
		GameCore.setBuilder(new BasicXMlBuilder());
		{
			GameScene scene = SceneMananger.loadScene(BOOTSTRAP_SCENE);
			if(SceneMananger.isCurrent(scene)) throw new UnsupportedOperationException("the boot-strap scene has not changed");
		}

		Mouse.init();
		KeyBoard.init();
	}

	public static void terminate() {
		SceneMananger.terminate();
		MultyTask.shutdown();
		BulbGL.terminate();
	}
}
