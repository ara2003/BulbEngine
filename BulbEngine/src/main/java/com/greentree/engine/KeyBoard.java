package com.greentree.engine;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import com.greentree.engine.core.util.Events;
import com.greentree.graphics.input.Key;
import com.greentree.graphics.input.event.KeyPressedEvent;
import com.greentree.graphics.input.event.KeyRepeatedEvent;
import com.greentree.graphics.input.event.KeyRepleasedEvent;

/**
 * @author Arseny Latyshev
 *
 */
public abstract class KeyBoard {

	private static Collection<StringBuilder> list = new HashSet<>();

	private static boolean[] press = new boolean[3000];
	public static void addString(StringBuilder text) {
		list.add(text);
	}

	/**
	 * Get the String residing on the clipboard.
	 *
	 * @return any text found on the Clipboard; if none found, return an
	 * empty String.
	 */
	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText =
				contents != null &&
				contents.isDataFlavorSupported(DataFlavor.stringFlavor)
				;
		if (hasTransferableText) try {
			result = (String)contents.getTransferData(DataFlavor.stringFlavor);
		}
		catch (UnsupportedFlavorException | IOException ex){
			System.out.println(ex);
			ex.printStackTrace();
		}
		return result;
	}

	public static void init() {
		Windows.window.getKeyPress().addListener(e -> Events.event(KeyPressedEvent.getInstanse(Events.getEventsystem(), e)));
		Windows.window.getKeyRelease().addListener(e -> Events.event(KeyRepleasedEvent.getInstanse(Events.getEventsystem(), e)));
		Windows.window.getKeyRepeat().addListener(e -> Events.event(KeyRepeatedEvent.getInstanse(Events.getEventsystem(), e)));
		
		Windows.window.getKeyRelease().addListener(k -> press[k] = false);
				
		Windows.window.getKeyPressOrRepeat().addListener(e -> {
			press[e] = true;
			if(e == Key.BACKSPACE.index()) list.forEach(a -> {
				if(a.length() > 0)a.delete(a.length()-1, a.length());
			});
			if(e == Key.V.index() && (isPress(Key.LEFT_CTRL) || isPress(Key.RIGHT_CTRL))) list.forEach(a -> {
				a.append(getClipboardContents());
			});
		});
		Windows.window.getCharEnter().addListener(e -> {
			list.forEach(a -> a.append(e));
		});
	}

	public static boolean isPress(Key key) {
		return press[key.index()];
	}

	public static boolean removeString(StringBuilder text) {
		return list.remove(text);
	}

}
