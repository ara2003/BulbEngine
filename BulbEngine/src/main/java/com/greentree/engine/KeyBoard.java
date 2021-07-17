package com.greentree.engine;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import com.greentree.common.logger.Log;
import com.greentree.graphics.input.Key;

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
			ex.printStackTrace();
		}
		return result;
	}

	public static void init() {
		Windows.window.getKeyRelease().addListener(key -> {
			try {
				press[key] = false;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
		});

		Windows.window.getKeyPressOrRepeat().addListener(key -> {
			try {
				press[key] = true;
			}catch (ArrayIndexOutOfBoundsException e) {
				Log.warn(e);
			}
			if(key == Key.BACKSPACE.index()) list.forEach(a -> {
				if(a.length() > 0)a.delete(a.length()-1, a.length());
			});
			if(key == Key.V.index() && (isPress(Key.LEFT_CTRL) || isPress(Key.RIGHT_CTRL))) list.forEach(a -> {
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
