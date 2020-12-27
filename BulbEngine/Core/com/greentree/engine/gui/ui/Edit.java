package com.greentree.engine.gui.ui;

import com.greentree.engine.Game;
import com.greentree.engine.Time;
import com.greentree.engine.component.Transform;
import com.greentree.engine.component.util.EditorData;
import com.greentree.engine.component.util.GameComponent;
import com.greentree.engine.gui.Color;
import com.greentree.engine.gui.Graphics;
import com.greentree.engine.input.Input;
import com.greentree.engine.input.KeyAdapter;
import com.greentree.engine.input.MouseAdapter;

public class Edit extends GameComponent {
	
	private static final long serialVersionUID = 1L;
	@EditorData(name = "text", reserve = "")
	String _text;
	@EditorData(reserve = "2")
	int border;
	private boolean focus, capsLock;
	private float height;
	Transform t;
	String text = "";
	@EditorData(reserve = "100")
	private float width;
	
	private boolean click0(final int button, final int x, final int y) {
		return (boolean) (button == 0
				? (x > (t.x - (width / 2) - border)) && (x < (t.x + (width / 2) + border)) && (y > (t.y - (height / 2) - border))
						&& (y < (t.y + (height / 2) + border))
						: 0);
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public void start() {
		height = Graphics.getFont().getHeight(_text);
		Game.addListener(new MouseAdapter() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void mousePressed(final int button, final int x, final int y) {
				focus = click0(button, x, y);
			}
		});
		Game.addListener(new KeyAdapter() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void keyReleased(final int key) {
				if(!focus) return;
				final String s = Input.getKeyName(key).toLowerCase();
				switch(key) {
					case Input.KEY_BACK:
						if(!text.isEmpty()) text = text.substring(0, text.length() - 1);
						break;
					case Input.KEY_CAPSLOCK:
						capsLock = !capsLock;
						break;
					case Input.KEY_SPACE:
						text += " ";
						break;
					case Input.KEY_ENTER:
						focus = false;
						break;
					default:
						if(s.length() == 1) text += capsLock ^ Input.isKeyDown(Input.KEY_LSHIFT) ? s.toUpperCase() : s;
						break;
				}
			}
		});
	}
	
	@Override
	public final void update() {
		Graphics.setColor(focus ? Color.white : Color.lightGray);
		Graphics.fillRect(t.x - (width / 2), t.y - (height / 2), width, height);
		if("".equals(text)) {
			if(!_text.isEmpty()) {
				Graphics.setColor(Color.gray);
				Graphics.drawString(_text, (t.x - (width / 2)) + 4, t.y - (height / 2));
			}
		}else {
			Graphics.setColor(Color.darkGray);
			Graphics.drawString(text, (t.x - (width / 2)) + 4, t.y - (height / 2));
			Graphics.setColor(Color.black);
		}
		if(focus && ((Time.getTime() % 400) > 200)) {
			final float x = (t.x - (width / 2)) + Graphics.getFont().getWidth(" " + text)
					+ (Graphics.getFont().getWidth(" ") / 3);
			Graphics.drawLine(x, t.y + (height / 3), x, t.y - (height / 3));
		}
		Graphics.setColor(Color.darkGray);
		Graphics.drawRect(t.x - (width / 2), t.y - (height / 2), width, height);
	}
}
