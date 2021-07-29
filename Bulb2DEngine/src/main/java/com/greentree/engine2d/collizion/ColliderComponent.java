package com.greentree.engine2d.collizion;

import java.util.List;
import java.util.function.Consumer;

import com.greentree.action.EventAction;
import com.greentree.common.Sized;
import com.greentree.engine.component.StartUpdatingGameComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.builder.RequireComponent;
import com.greentree.engine.core.builder.RequireSystems;
import com.greentree.engine.core.node.GameObject;
import com.greentree.engine.geom2d.Point2D;
import com.greentree.engine.geom2d.Shape2D;

@RequireSystems({ColliderSystem.class})
@RequireComponent({Transform.class})
public abstract class ColliderComponent extends StartUpdatingGameComponent implements Sized {


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

	public float getPenetrationDepth(final ColliderComponent b) {
		return shape.getPenetrationDepth(b.shape);
	}

	public List<Point2D> getPoints() {
		return shape.getPoints();
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
	public final void start() {
		shape = generateShape();//не перемещать в конструктор
		this.getComponent(Transform.class).position.addListener(t-> {
			setPosition(t.x() + getDeltaX(), t.y() + getDeltaY());
		});
		this.getComponent(Transform.class).position.tryAction();
	}

	@Override
	public final void update() {
	}

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
			if(isDestroy() || component.isDestroy()) return;
			enterActon.action(component);
		}

		public final void collizionExit(final ColliderComponent component) {
			if(isDestroy() || component.isDestroy()) return;
			exitActon.action(component);
		}

		public final void collizionStay(final ColliderComponent component) {
			if(isDestroy() || component.isDestroy()) return;
			stayActon.action(component);
		}
	}
}
