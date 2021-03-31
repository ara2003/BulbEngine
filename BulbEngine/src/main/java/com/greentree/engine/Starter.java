package com.greentree.engine;

import com.greentree.engine.collizion.collider.CircleColliderComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.ui.Button;
import com.greentree.engine.core.Game;
import com.greentree.engine.editor.xml.BasicXMlBuilder;
import com.greentree.engine.system.RenderSystem;

/**
 * @author Arseny Latyshev
 *
 */
public class Starter {
	
	private Starter() {
	}
	
	public static void start(String folder) {
		Game.start(folder, new BasicXMlBuilder(Transform.class.getPackageName(), CircleColliderComponent.class.getPackageName(), Button.class.getPackageName(), RenderSystem.class.getPackageName()));
	}

	public static void start() {
		start("Game");
	}
	
}
