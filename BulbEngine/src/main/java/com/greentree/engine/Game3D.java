package com.greentree.engine;

import java.io.File;

import com.greentree.common.concurent.MultyTask;
import com.greentree.engine.builder.xml.BasicXMlBuilder;
import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.GameCore;
import com.greentree.engine.core.Properties;
import com.greentree.engine.core.RootFiles;
import com.greentree.engine.core.SceneLoader;
import com.greentree.engine.core.system.UpdatingComponentSystem;
import com.greentree.engine.navmap.NavMeshSystem;
import com.greentree.engine.render.CameraRenderSystem;
import com.greentree.engine.render.ui.Button;
import com.greentree.engine.system.ESCExtitSystem;
import com.greentree.graphics.BulbGL;
import com.greentree.graphics.Graphics;

/** @author Arseny Latyshev */
public class Game3D extends GameCore {

	public static void exit() {
		Windows.getWindow().shouldClose();
	}

	public static void start(final String folder, final String[] args) {
		BulbGL.init();
		Properties.loadArguments(args);
		RootFiles.start(folder);
		Properties.loadProperty(new File(RootFiles.getRoot(), "game.properties"));
		GameCore.setBuilder(new BasicXMlBuilder(Transform.class.getPackageName(),
				CircleColliderComponent.class.getPackageName(), Button.class.getPackageName(),
				CameraRenderSystem.class.getPackageName(), ESCExtitSystem.class.getPackageName(), UpdatingComponentSystem.class.getPackageName(), NavMeshSystem.class.getPackageName()));
		Windows.getWindow().makeCurrent();
		Graphics.clearColor(.6f, .6f, .6f);
		Graphics.setClearDepth(1.0);
		Mouse.setMousePos(0, 0);
		KeyBoard.init();
		SceneLoader.loadScene(Properties.getProperty("scene.first").notNull().get());
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().swapBuffer();
			Graphics.glClearAll();
			Windows.getWindow().updateEvents();
			GameCore.gameLoop();
		}
		MultyTask.shutdown();
		BulbGL.terminate();
	}

	public static void start(final String[] args) {
		Game3D.start("Game", args);
	}

	protected Game3D() {
	}
}
