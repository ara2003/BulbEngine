package com.greentree.engine.input;

import com.greentree.bulbgl.input.listener.MouseListener;
import com.greentree.engine.event.NecessarilyListenerManagers;


/**
 * @author Arseny Latyshev
 *
 */
@NecessarilyListenerManagers({CameraMouseListenerManager.class})
public interface CameraMouseListener extends MouseListener {
	
}
