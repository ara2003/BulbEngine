package com.greentree.engine.component;

import com.greentree.common.Starting;
import com.greentree.common.Updating;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.object.GameComponent;
import com.greentree.engine.system.StartGameComponentSystem;
import com.greentree.engine.system.UpdatingGameComponentSystem;

@RequireSystems({StartGameComponentSystem.class, UpdatingGameComponentSystem.class})
public abstract class StartUpdatingGameComponent extends GameComponent implements Updating, Starting {
}
