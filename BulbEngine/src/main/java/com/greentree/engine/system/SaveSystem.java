package com.greentree.engine.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.greentree.common.logger.Log;
import com.greentree.engine.core.builder.EditorData;
import com.greentree.engine.core.builder.Required;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;

public class SaveSystem extends MultiBehaviour {

	@Required
	@EditorData("file to save")
	private File file;

	private final Map<String, Object> map = new HashMap<>();

	@Override
	public void destroy() {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
			out.writeObject(map);
		}catch(IOException e) {
			Log.error(e);
		}
	}

	public Object load(String name) {
		return map.get(name);
	}




	public Object remove(String name){
		return map.remove(name);
	}
	public Object save(String name, Object object) {
		return map.put(name, object);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void start() {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
			map.putAll((Map<String, Object>) in.readObject());
		}catch(IOException | ClassNotFoundException e) {
			Log.error(e);
		}
	}

}
