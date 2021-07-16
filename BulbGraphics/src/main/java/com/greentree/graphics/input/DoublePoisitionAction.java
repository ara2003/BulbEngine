package com.greentree.graphics.input;

import com.greentree.action.Action;
import com.greentree.graphics.input.DoublePoisitionAction.DoublePoisitionListener;

/**
 * @author Arseny Latyshev
 *
 */
public class DoublePoisitionAction extends Action<DoublePoisitionListener> {
	
	private static final long serialVersionUID = 1L;

	public void action(int x1, int y1, int x2, int y2) {
		action(l -> l.action(x1, y1, x2, y2));
	}

	@FunctionalInterface
	public interface DoublePoisitionListener {

		public void action(int x1, int y1, int x2, int y2);
		
	}
}
