package com.greentree.engine.component;

import com.greentree.engine.Game;
import com.greentree.engine.Time;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.component.util.XmlData;
import com.greentree.engine.input.Input;
import com.greentree.engine.input.KeyListener;

public class Control extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	@XmlData
	private String control;
	public int KEY_UP, KEY_DOWN, KEY_RIGHT, KEY_LEFT;
	@XmlData
	private float speed;
	private float speedx, speedy;
	public transient Transform t;
	public boolean up, down, right, left;
	public float up_down, right_left;
	
	public String getType() {
		return control;
	}
	
	@Override
	public void start() {
		speed /= 1000.;
		if(control.equals("wasd")) {
			KEY_UP = Input.KEY_W;
			KEY_DOWN = Input.KEY_S;
			KEY_RIGHT = Input.KEY_D;
			KEY_LEFT = Input.KEY_A;
		}
		if(control.equals("uldr")) {
			KEY_UP = Input.KEY_UP;
			KEY_DOWN = Input.KEY_DOWN;
			KEY_RIGHT = Input.KEY_RIGHT;
			KEY_LEFT = Input.KEY_LEFT;
		}
		Game.getEventSystem().addListener(new KeyListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void keyPressed(final int key) {
				if(key == KEY_UP) up = true;
				if(key == KEY_DOWN) down = true;
				if(key == KEY_LEFT) left = true;
				if(key == KEY_RIGHT) right = true;
				trimAxis();
			}

			@Override
			public void keyReleased(final int key) {
				if(key == KEY_UP) up = false;
				if(key == KEY_DOWN) down = false;
				if(key == KEY_LEFT) left = false;
				if(key == KEY_RIGHT) right = false;
				trimAxis();
			}

			private void trimAxis() {
				if(up && !down) up_down = -1;
				if(!up && down) up_down = 1;
				if(!up && !down) up_down = 0;
				if(!right && left) right_left = -1;
				if(right && !left) right_left = 1;
				if(!right && !left) right_left = 0;
				final float mod = (float) Math.sqrt(up_down * up_down + right_left * right_left);
				if(mod == 0) return;
				up_down /= mod;
				right_left /= mod;
			}
		});
	}
	
	@Override
	public void update() {
		speedx += speed * Time.getDelta() * right_left;
		speedy += speed * Time.getDelta() * up_down;
		speedx *= .64;
		speedy *= .64;
		t.x += speedx;
		t.y += speedy;
	}
}
