package com.greentree.graphics.input;

import com.greentree.action.Action;
import com.greentree.graphics.input.PoisitionAction.PoisitionListener;

/**
 * @author Arseny Latyshev
 *
 */
public class PoisitionAction extends Action<PoisitionListener> {
	private static final long serialVersionUID = 1L;

	public void action(int xpos, int ypos) {
		action(l -> l.action(xpos, ypos));
	}

	@FunctionalInterface
	public interface PoisitionListener {

		public void action(int xpos, int ypos);
		
	}
}
