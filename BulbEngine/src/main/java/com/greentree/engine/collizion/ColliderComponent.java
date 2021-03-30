package com.greentree.engine.collizion;

import java.util.function.Consumer;

import com.greentree.action.EventAction;
import com.greentree.common.Sized;
import com.greentree.engine.component.RequireComponent;
import com.greentree.engine.component.Transform;
import com.greentree.engine.geom2d.Shape2D;
import com.greentree.engine.object.GameComponent;
import com.greentree.engine.object.GameObject;
import com.greentree.engine.system.NecessarilySystems;

@NecessarilySystems({ColliderSystem.class})
@RequireComponent({Transform.class})
public abstract class ColliderComponent extends GameComponent implements Sized {
	
	private Shape2D shape;
	private final CollisionAction action;
	private float x, y;
	
	public ColliderComponent() {
		this.action = new CollisionAction();
	}
	
	@Override
	protected final void awake() {
		this.shape    = this.generateShape();//не перемещать в конструктор
		this.getComponent(Transform.class).getAction().addListener(t -> {
			setPosition(t.x() + getDeltaX(), t.y() + getDeltaY());
			
		});
	}
	
	protected abstract Shape2D generateShape();
	
	public final CollisionAction getAction() {
		return this.action;
	}
	
	@Override
	public final float getHeight() {
		return this.shape.getAABB().getHeight();
	}
	
	public final Shape2D getShape() {
		return this.shape;
	}
	
	@Override
	public float getWidth() {
		return this.shape.getAABB().getWidth();
	}

	public final float getY(){
		return y;
	}
	
	
	public final float getX(){
		return x;
	}
	
	public float getDeltaX() {
		return 0;
	}
	
	public float getDeltaY() {
		return 0;
	}
	
	public final void setPosition(final float x, final float y) {
		this.x = x + getDeltaX();
		this.y = y + getDeltaY();
		this.shape.moveTo(this.x, this.y);
	}
	
	public final void setSize(final float width, final float height) {
		this.shape.setSize(width, height);
	}
	
	@Override
	protected final void start() {
	}
	
	@Override
	public final void update() {
	}
	
	/** @author Arseny Latyshev */
	public class CollisionAction {
		
		private final EventAction<ColliderComponent> enterActon, exitActon, stayActon;
		
		public CollisionAction() {
			this.enterActon = new EventAction<>();
			this.exitActon  = new EventAction<>();
			this.stayActon  = new EventAction<>();
		}
		
		public final void addEnterListener(final Consumer<ColliderComponent> c) {
			this.enterActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}
		
		public final void addEnterObjectListener(final Consumer<GameObject> c) {
			this.addEnterListener(e->c.accept(e.getObject()));
		}
		
		public final void addExitListener(final Consumer<ColliderComponent> c) {
			this.exitActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}
		
		public final void addExitObjectListener(final Consumer<GameObject> c) {
			this.addExitListener(e->c.accept(e.getObject()));
		}
		
		public final void addStayListener(final Consumer<ColliderComponent> c) {
			this.stayActon.addListener(e-> {
				if(e.isDestroy()) return;
				c.accept(e);
			});
		}
		
		public final void addStayObjectListener(final Consumer<GameObject> c) {
			this.addStayListener(e->c.accept(e.getObject()));
		}
		
		public final void collizionEnter(final ColliderComponent component) {
			if(ColliderComponent.this.isDestroy()) return;
			if(component.isDestroy()) return;
			this.enterActon.action(component);
		}
		
		public final void collizionExit(final ColliderComponent component) {
			if(ColliderComponent.this.isDestroy()) return;
			if(component.isDestroy()) return;
			this.exitActon.action(component);
		}
		
		public final void collizionStay(final ColliderComponent component) {
			if(ColliderComponent.this.isDestroy()) return;
			if(component.isDestroy()) return;
			this.stayActon.action(component);
		}
	}
}
