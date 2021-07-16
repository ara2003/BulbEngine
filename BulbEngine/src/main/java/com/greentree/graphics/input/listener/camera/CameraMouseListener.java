package com.greentree.graphics.input.listener.camera;

import com.greentree.event.NecessarilyListenerManagers;
import com.greentree.graphics.input.listener.MouseListener;

@Deprecated
/** @author Arseny Latyshev */
@NecessarilyListenerManagers({CameraMouseListenerManager.class})
public interface CameraMouseListener extends MouseListener {
}
