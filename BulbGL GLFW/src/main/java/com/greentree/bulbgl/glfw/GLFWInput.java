package com.greentree.bulbgl.glfw;

import org.lwjgl.glfw.GLFW;

import com.greentree.bulbgl.InputI;

/** @author Arseny Latyshev */
public class GLFWInput extends InputI {
	
	enum Keys {
		Q(GLFW.GLFW_KEY_Q);

		
		private final int glfwKey;

		Keys(int glfwKey) {
			this.glfwKey = glfwKey;
		}
	}
	
	@Override
	public int getIndexOf(final String name) {
		try{
			return Keys.valueOf(name.toUpperCase()).glfwKey;
		}catch (IllegalArgumentException e) {
		}
		return switch(name.toUpperCase()) {
			case "Q" -> GLFW.GLFW_KEY_Q;
			case "W" -> GLFW.GLFW_KEY_W;
			case "E" -> GLFW.GLFW_KEY_E;
			case "R" -> GLFW.GLFW_KEY_R;
			case "T" -> GLFW.GLFW_KEY_T;
			case "Y" -> GLFW.GLFW_KEY_Y;
			case "U" -> GLFW.GLFW_KEY_U;
			case "I" -> GLFW.GLFW_KEY_I;
			case "O" -> GLFW.GLFW_KEY_O;
			case "P" -> GLFW.GLFW_KEY_P;
			case "A" -> GLFW.GLFW_KEY_A;
			case "S" -> GLFW.GLFW_KEY_S;
			case "D" -> GLFW.GLFW_KEY_D;
			case "F" -> GLFW.GLFW_KEY_F;
			case "G" -> GLFW.GLFW_KEY_G;
			case "H" -> GLFW.GLFW_KEY_H;
			case "J" -> GLFW.GLFW_KEY_J;
			case "K" -> GLFW.GLFW_KEY_K;
			case "L" -> GLFW.GLFW_KEY_L;
			case "Z" -> GLFW.GLFW_KEY_Z;
			case "X" -> GLFW.GLFW_KEY_X;
			case "C" -> GLFW.GLFW_KEY_C;
			case "V" -> GLFW.GLFW_KEY_V;
			case "B" -> GLFW.GLFW_KEY_B;
			case "N" -> GLFW.GLFW_KEY_N;
			case "M" -> GLFW.GLFW_KEY_M;
			case "ESCAPE" -> GLFW.GLFW_KEY_ESCAPE;
			case "ESC" -> GLFW.GLFW_KEY_ESCAPE;
			case "SPACE" -> GLFW.GLFW_KEY_SPACE;
			case "UP" -> GLFW.GLFW_KEY_UP;
			case "DOWN" -> GLFW.GLFW_KEY_DOWN;
			case "LEFT" -> GLFW.GLFW_KEY_LEFT;
			case "RIGHT" -> GLFW.GLFW_KEY_RIGHT;
			default -> -1;
		};
	}
	
	@Override
	public String getKeyName(final int key) {
		return GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
	}
	
	
}
