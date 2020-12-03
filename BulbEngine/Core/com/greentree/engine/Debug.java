package com.greentree.engine;

import java.io.FileWriter;
import java.io.IOException;

@Deprecated
public final class Debug {

	private static FileWriter file, event;
	static {
		FileManager.getDirectory("Debug");
		try {
			file = new FileWriter(FileManager.getFile("Debug\\time.txt"));
		}catch(final IOException e) {
			file = null;
			Log.error(e);
		}
		try {
			event = new FileWriter(FileManager.getFile("Debug\\event.txt"));
		}catch(final IOException e) {
			event = null;
			Log.error(e);
		}
	}
	
	private Debug() {
	}
	
	public static void addEvent(final String name, final String data) {
		try {
			event.write(Time.getTime() + "~" + name + "~" + data + "\n");
		}catch(final IOException e) {
			Log.error(e);
		}
	}

	public static void addTime(final String name, final String data) {
		try {
			file.write(Time.getTime() + "~" + name + "~" + data + "\n");
		}catch(final IOException e) {
			Log.error(e);
		}
	}
}
