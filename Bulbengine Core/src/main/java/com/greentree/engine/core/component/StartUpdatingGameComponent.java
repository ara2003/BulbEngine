package com.greentree.engine.core.component;

import com.greentree.common.Starting;
import com.greentree.common.Updating;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.core.system.StartGameSystem;
import com.greentree.engine.core.system.UpdatingComponentSystem;

@RequireSystems({StartGameSystem.class, UpdatingComponentSystem.class})
public abstract class StartUpdatingGameComponent extends GameComponent implements Updating, Starting {
}
