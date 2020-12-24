package com.greentree.engine.input;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.greentree.engine.Game;
import com.greentree.geom.Point;

public class Input {
	
	public static final int ANY_CONTROLLER = -1;
	private static boolean displayActive = true;
	private static long doubleClickTimeout = 0L;
	private static int height;
	public static final int KEY_0 = 11;
	public static final int KEY_1 = 2;
	public static final int KEY_2 = 3;
	public static final int KEY_3 = 4;
	public static final int KEY_4 = 5;
	public static final int KEY_5 = 6;
	public static final int KEY_6 = 7;
	public static final int KEY_7 = 8;
	public static final int KEY_8 = 9;
	public static final int KEY_9 = 10;
	public static final int KEY_A = 30;
	public static final int KEY_ADD = 78;
	public static final int KEY_APOSTROPHE = 40;
	public static final int KEY_APPS = 221;
	public static final int KEY_AT = 145;
	public static final int KEY_AX = 150;
	public static final int KEY_B = 48;
	public static final int KEY_BACK = 14;
	public static final int KEY_BACKSLASH = 43;
	public static final int KEY_C = 46;
	public static final int KEY_CAPITAL = 58;
	public static final int KEY_CAPSLOCK = 58;
	public static final int KEY_CIRCUMFLEX = 144;
	public static final int KEY_COLON = 146;
	public static final int KEY_COMMA = 51;
	public static final int KEY_CONVERT = 121;
	public static final int KEY_D = 32;
	public static final int KEY_DECIMAL = 83;
	public static final int KEY_DELETE = 211;
	public static final int KEY_DIVIDE = 181;
	public static final int KEY_DOWN = 208;
	public static final int KEY_E = 18;
	public static final int KEY_END = 207;
	public static final int KEY_ENTER = 28;
	public static final int KEY_EQUALS = 13;
	public static final int KEY_ESCAPE = 1;
	public static final int KEY_F = 33;
	public static final int KEY_F1 = 59;
	public static final int KEY_F10 = 68;
	public static final int KEY_F11 = 87;
	public static final int KEY_F12 = 88;
	public static final int KEY_F13 = 100;
	public static final int KEY_F14 = 101;
	public static final int KEY_F15 = 102;
	public static final int KEY_F2 = 60;
	public static final int KEY_F3 = 61;
	public static final int KEY_F4 = 62;
	public static final int KEY_F5 = 63;
	public static final int KEY_F6 = 64;
	public static final int KEY_F7 = 65;
	public static final int KEY_F8 = 66;
	public static final int KEY_F9 = 67;
	public static final int KEY_G = 34;
	public static final int KEY_GRAVE = 41;
	public static final int KEY_H = 35;
	public static final int KEY_HOME = 199;
	public static final int KEY_I = 23;
	public static final int KEY_INSERT = 210;
	public static final int KEY_J = 36;
	public static final int KEY_K = 37;
	public static final int KEY_KANA = 112;
	public static final int KEY_KANJI = 148;
	public static final int KEY_L = 38;
	public static final int KEY_LALT = 56;
	public static final int KEY_LBRACKET = 26;
	public static final int KEY_LCONTROL = 29;
	public static final int KEY_LEFT = 203;
	public static final int KEY_LMENU = 56;
	public static final int KEY_LSHIFT = 42;
	public static final int KEY_LWIN = 219;
	public static final int KEY_M = 50;
	public static final int KEY_MINUS = 12;
	public static final int KEY_MULTIPLY = 55;
	public static final int KEY_N = 49;
	public static final int KEY_NEXT = 209;
	public static final int KEY_NOCONVERT = 123;
	public static final int KEY_NUMLOCK = 69;
	public static final int KEY_NUMPAD0 = 82;
	public static final int KEY_NUMPAD1 = 79;
	public static final int KEY_NUMPAD2 = 80;
	public static final int KEY_NUMPAD3 = 81;
	public static final int KEY_NUMPAD4 = 75;
	public static final int KEY_NUMPAD5 = 76;
	public static final int KEY_NUMPAD6 = 77;
	public static final int KEY_NUMPAD7 = 71;
	public static final int KEY_NUMPAD8 = 72;
	public static final int KEY_NUMPAD9 = 73;
	public static final int KEY_NUMPADCOMMA = 179;
	public static final int KEY_NUMPADENTER = 156;
	public static final int KEY_NUMPADEQUALS = 141;
	public static final int KEY_O = 24;
	public static final int KEY_P = 25;
	public static final int KEY_PAUSE = 197;
	public static final int KEY_PERIOD = 52;
	public static final int KEY_POWER = 222;
	public static final int KEY_PRIOR = 201;
	public static final int KEY_Q = 16;
	public static final int KEY_R = 19;
	public static final int KEY_RALT = 184;
	public static final int KEY_RBRACKET = 27;
	public static final int KEY_RCONTROL = 157;
	public static final int KEY_RETURN = 28;
	public static final int KEY_RIGHT = 205;
	public static final int KEY_RMENU = 184;
	public static final int KEY_RSHIFT = 54;
	public static final int KEY_RWIN = 220;
	public static final int KEY_S = 31;
	public static final int KEY_SCROLL = 70;
	public static final int KEY_SEMICOLON = 39;
	public static final int KEY_SLASH = 53;
	public static final int KEY_SLEEP = 223;
	public static final int KEY_SPACE = 57;
	public static final int KEY_STOP = 149;
	public static final int KEY_SUBTRACT = 74;
	public static final int KEY_SYSRQ = 183;
	public static final int KEY_T = 20;
	public static final int KEY_TAB = 15;
	public static final int KEY_U = 22;
	public static final int KEY_UNDERLINE = 147;
	public static final int KEY_UNLABELED = 151;
	public static final int KEY_UP = 200;
	public static final int KEY_V = 47;
	public static final int KEY_W = 17;
	public static final int KEY_X = 45;
	public static final int KEY_Y = 21;
	public static final int KEY_YEN = 125;
	public static final int KEY_Z = 44;
	private static int keyRepeatInitial;
	protected static char[] keys = new char[1024];
	private static int lastMouseX;
	private static int lastMouseY;
	public static final int MOUSE_LEFT_BUTTON = 0;
	public static final int MOUSE_MIDDLE_BUTTON = 2;
	public static final int MOUSE_RIGHT_BUTTON = 1;
	private static int mouseClickTolerance = 5;
	protected static boolean[] mousePressed = new boolean[10];
	protected static long[] nextRepeat = new long[1024];
	protected static boolean[] pressed = new boolean[1024];
	private static int pressedX = -1;
	private static int pressedY = -1;
	private static float scaleX = 1.0F;
	private static float scaleY = 1.0F;
	private static float xoffset = 0.0F;
	private static float yoffset = 0.0F;
	
	private Input() {
	}
	
	private static boolean anyMouseDown() {
		for(int i = 0; i < 3; ++i) if(Mouse.isButtonDown(i)) return true;
		return false;
	}
	
	public static void clearKeyPressedRecord() {
		Arrays.fill(Input.pressed, false);
	}
	
	public static void clearMousePressedRecord() {
		Arrays.fill(Input.mousePressed, false);
	}
	
	public static void disableKeyRepeat() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public static void enableKeyRepeat() {
		Keyboard.enableRepeatEvents(true);
	}
	
	public static void enableKeyRepeat(final int initial, final int interval) {
		Keyboard.enableRepeatEvents(true);
	}
	
	public static int getAbsoluteMouseX() {
		return Mouse.getX();
	}
	
	public static int getAbsoluteMouseY() {
		return Input.height - Mouse.getY();
	}
	
	public static String getKeyName(final int code) {
		return Keyboard.getKeyName(code);
	}
	
	public static Point getMousePoint() {
		return new Point(getMouseX(), getMouseY());
	}
	
	public static int getMouseX() {
		return (int) ((Mouse.getX() * Input.scaleX) + Input.xoffset);
	}
	
	public static int getMouseY() {
		return (int) (((Input.height - Mouse.getY()) * Input.scaleY) + Input.yoffset);
	}
	
	static void init(final int height) {
		Input.height = height;
		Input.lastMouseX = Input.getMouseX();
		Input.lastMouseY = Input.getMouseY();
	}
	
	public static boolean isKeyDown(final int code) {
		return Keyboard.isKeyDown(code);
	}
	
	public static boolean isKeyPressed(final int code) {
		if(Input.pressed[code]) {
			Input.pressed[code] = false;
			return true;
		}else return false;
	}
	
	public static boolean isKeyRepeatEnabled() {
		return Keyboard.areRepeatEventsEnabled();
	}
	
	public static boolean isMouseButtonDown(final int button) {
		return Mouse.isButtonDown(button);
	}
	
	public static boolean isMousePressed(final int button) {
		if(Input.mousePressed[button]) {
			Input.mousePressed[button] = false;
			return true;
		}else return false;
	}
	
	public static void poll(final int width, final int height) {
		if(!Display.isActive()) {
			Input.clearKeyPressedRecord();
			Input.clearMousePressedRecord();
		}
		if((Input.doubleClickTimeout != 0L) && (System.currentTimeMillis() > Input.doubleClickTimeout))
			Input.doubleClickTimeout = 0L;
		Input.height = height;
		while(true) {
			int i;
			int j;
			while(Keyboard.next()) if(Keyboard.getEventKeyState()) {
				i = Input.resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
				Input.keys[i] = Keyboard.getEventCharacter();
				Input.pressed[i] = true;
				Input.nextRepeat[i] = System.currentTimeMillis() + Input.keyRepeatInitial;
				Game.event(new KeyEvent(KeyEvent.EventType.keyPressed, i));
			}else {
				i = Input.resolveEventKey(Keyboard.getEventKey(), Keyboard.getEventCharacter());
				Input.nextRepeat[i] = 0L;
				Game.event(new KeyEvent(KeyEvent.EventType.keyReleased, i));
			}
			while(true) while(true) {
				while(Mouse.next()) if(Mouse.getEventButton() >= 0) {
					if(Mouse.getEventButtonState()) {
						Input.mousePressed[Mouse.getEventButton()] = true;
						Input.pressedX = (int) (Input.xoffset + (Mouse.getEventX() * Input.scaleX));
						Input.pressedY = (int) (Input.yoffset + ((height - Mouse.getEventY()) * Input.scaleY));
						Game.event(new MouseEvent(MouseEvent.EventType.mousePressed,
								Mouse.getEventButton(), Input.pressedX, Input.pressedY));
					}else {
						Input.mousePressed[Mouse.getEventButton()] = false;
						i = (int) (Input.xoffset + (Mouse.getEventX() * Input.scaleX));
						j = (int) (Input.yoffset + ((height - Mouse.getEventY()) * Input.scaleY));
						if((Input.pressedX != -1) && (Input.pressedY != -1)
								&& (Math.abs(Input.pressedX - i) < Input.mouseClickTolerance)
								&& (Math.abs(Input.pressedY - j) < Input.mouseClickTolerance))
							Input.pressedX = Input.pressedY = -1;
						Game.event(new MouseEvent(MouseEvent.EventType.mouseReleased,
								Mouse.getEventButton(), Input.pressedX, Input.pressedY));
					}
				}else
					if(Mouse.isGrabbed() && Input.displayActive && ((Mouse.getEventDX() != 0) || (Mouse.getEventDY() != 0)))
						if(Input.anyMouseDown())
							Game.event(new MouseEvent(MouseEvent.EventType.mouseDragged, 0, 0,
									Mouse.getEventDX(), -Mouse.getEventDY()));
						else Game.event(
								new MouseEvent(MouseEvent.EventType.mouseMoved, 0, 0, Mouse.getEventDX(), -Mouse.getEventDY()));
				if(Input.displayActive && !Mouse.isGrabbed()) {
					if((Input.lastMouseX != Input.getMouseX()) || (Input.lastMouseY != Input.getMouseY())) {
						if(Input.anyMouseDown())
							Game.event(new MouseEvent(MouseEvent.EventType.mouseDragged, 0, 0,
									Mouse.getEventDX(), -Mouse.getEventDY()));
						else Game.event(new MouseEvent(MouseEvent.EventType.mouseMoved, 0, 0,
								Mouse.getEventDX(), -Mouse.getEventDY()));
						Input.lastMouseX = Input.getMouseX();
						Input.lastMouseY = Input.getMouseY();
					}
				}else {
					Input.lastMouseX = Input.getMouseX();
					Input.lastMouseY = Input.getMouseY();
				}
				if(Display.isCreated()) Input.displayActive = Display.isActive();
				return;
			}
		}
	}
	
	public static void resetInputTransform() {
		Input.setOffset(0.0F, 0.0F);
		Input.setScale(1.0F, 1.0F);
	}
	
	private static int resolveEventKey(final int key, final char c) {
		return (c != '=') && (key != 0) ? key : 13;
	}
	
	public static void setMouseClickTolerance(final int mouseClickTolerance) {
		Input.mouseClickTolerance = mouseClickTolerance;
	}
	
	public static void setOffset(final float xoffset, final float yoffset) {
		Input.xoffset = xoffset;
		Input.yoffset = yoffset;
	}
	
	public static void setScale(final float scaleX, final float scaleY) {
		Input.scaleX = scaleX;
		Input.scaleY = scaleY;
	}
}
