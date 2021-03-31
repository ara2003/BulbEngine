package com.greentree.engine.core.component;

import com.greentree.common.Updating;
import com.greentree.engine.core.GameComponent;
import com.greentree.engine.core.system.NecessarilySystems;
import com.greentree.engine.core.system.UpdatingComponentSystem;


/**
 * @author Arseny Latyshev
 *
 */
@NecessarilySystems(UpdatingComponentSystem.class)
public abstract class UpdatingGameComponent extends GameComponent implements Updating {


	public abstract void update();

}
