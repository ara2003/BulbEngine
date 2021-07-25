package com.greentree.engine3d.phisics;

import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.object.GameComponent;

@RequireSystems(GravitySystem.class)
@RequireComponent(Transform.class)
public class GravitationalComponent extends GameComponent {

}
