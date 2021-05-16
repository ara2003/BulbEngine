package com.greentree.engine.collizion;

import java.util.function.Consumer;

import com.greentree.action.EventAction;
import com.greentree.common.Sized;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.component.RequireComponent;
import com.greentree.engine.core.component.UpdatingGameComponent;
import com.greentree.engine.core.object.GameObject;
import com.greentree.engine.core.system.RequireSystems;
import com.greentree.engine.geom2d.Shape2D;

@RequireSystems({ColliderSystem.class})
@RequireComponent({Transform.class})
public abstract class ColliderComponent extends UpdatingGameComponent implements Sized {

	/** @author Arseny Latyshev */
	public class CollisionAction {

		private final EventAction<ColliderComponent> enterActon, exitActon, stayActon;

		public CollisionAction() {
			enterActon = new EventAction<>();
			exitActon = new EventAction<>();
			stayActon = new EventAction<>();
		}

		public final void addEnterListener(final Consumer<ColliderComponent> c) {
			enterActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}

		public final void addEnterObjectListener(final Consumer<GameObject> c) {
			addEnterListener(e->c.accept(e.getObject()));
		}

		public final void addExitListener(final Consumer<ColliderComponent> c) {
			exitActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}

		public final void addExitObjectListener(final Consumer<GameObject> c) {
			addExitListener(e->c.accept(e.getObject()));
		}

		public final void addStayListener(final Consumer<ColliderComponent> c) {
			stayActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}

		public final void addStayObjectListener(final Consumer<GameObject> c) {
			addStayListener(e->c.accept(e.getObject()));
		}

		public final void collizionEnter(final ColliderComponent component) {
			if(isDestroy()) return;
			if(component.isDestroy()) return;
			enterActon.action(component);
		}

		public final void collizionExit(final ColliderComponent component) {
			if(isDestroy()) return;
			if(component.isDestroy()) return;
			exitActon.action(component);
		}

		public final void collizionStay(final ColliderComponent component) {
			if(isDestroy()) return;
			if(component.isDestroy()) return;
			stayActon.action(component);
		}
	}
	
	private Shape2D shape;
	private final CollisionAction action;
	private float x, y;

	public ColliderComponent() {
		action = new CollisionAction();
	}

	protected abstract Shape2D generateShape();

	public final CollisionAction getAction() {
		return action;
	}

	public float getDeltaX() {
		return 0;
	}

	public float getDeltaY() {
		return 0;
	}
	
	@Override
	public final float getHeight() {
		return shape.getAABB().getHeight();
	}

	@Override
	public float getWidth() {
		return shape.getAABB().getWidth();
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public boolean isIntersect(final ColliderComponent b) {
		return shape.isIntersect(b.shape);
	}

	public final void setPosition(final float x, final float y) {
		this.x = x + getDeltaX();
		this.y = y + getDeltaY();
		shape.moveTo(this.x, this.y);
	}

	public final void setSize(final float width, final float height) {
		shape.setSize(width, height);
	}

	@Override
	protected final void start() {
		shape = generateShape();//не перемещать в конструктор
		this.getComponent(Transform.class).getAction().addListener(t-> {
			setPosition(t.x() + getDeltaX(), t.y() + getDeltaY());
		});
	}

	@Override
	public final void update() {
	}
}
