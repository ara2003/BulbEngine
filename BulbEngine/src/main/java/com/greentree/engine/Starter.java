package com.greentree.engine;

import java.io.File;

import com.greentree.bulbgl.input.Input;
import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.render.SpriteRendener;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.core.Events;
import com.greentree.engine.core.Game;
import com.greentree.engine.core.Properties;
import com.greentree.engine.core.RootFiles;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.system.RenderSystem;

/** @author Arseny Latyshev */
public class Starter {
	
	private Starter() {
	}
	
	public static void start(final String folder, final String[] args) {
		Properties.loadArguments(args);
		
		RootFiles.start(folder);
		Properties.loadProperty(new File(RootFiles.getRoot(), "config.game"));
		Windows.getWindow().setEventSystem(Events.getEventsystem());
		Input.setEventSystem(Events.getEventsystem());
		
		Game.setBuilder(new BasicXMlBuilder(Transform.class.getPackageName(), CircleColliderComponent.class.getPackageName(), Button.class.getPackageName(),
			RenderSystem.class.getPackageName(), SpriteRendener.class.getPackageName()));
		Game.loadScene(Properties.getProperty("scene.first"));
		
		while(!Windows.getWindow().isShouldClose()) {
			Windows.getWindow().startRender();
			Game.gameLoop();
			Windows.getWindow().finishRender();
		}
	}
	
	public static void start(final String[] args) {
		Starter.start("Game", args);
	}
	
}
