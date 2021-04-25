package com.greentree.engine.input;

import com.greentree.event.NecessarilyListenerManagers;
import com.greentree.graphics.input.listener.MouseListener;

/** @author Arseny Latyshev */
@NecessarilyListenerManagers({CameraMouseListenerManager.class})
public interface CameraMouseListener extends MouseListener {
}
