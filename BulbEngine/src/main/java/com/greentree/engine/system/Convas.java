package com.greentree.engine.system;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.greentree.bulbgl.BulbGL;
import com.greentree.engine.Windows;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.ui.UIComponent;
import com.greentree.engine.core.GameSystem;

/** @author Arseny Latyshev */
public class Convas extends GameSystem {
	
	@Override
	protected void update() {
		float w = Windows.getWindow().getWidth() / 2, h = Windows.getWindow().getHeight() / 2;
		BulbGL.getGraphics().translate(w, h);
		for(final UIComponent renderable : this.getAllComponents(UIComponent.class).parallelStream()
			.sorted(Comparator.comparing(a->a.getComponent(Transform.class).z()))
			.collect(Collectors.toList()))
			renderable.render();
		BulbGL.getGraphics().translate(-w, -h);
	}
	
}
