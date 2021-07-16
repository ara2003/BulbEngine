package com.greentree.engine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.greentree.common.logger.Log;
import com.greentree.data.FileUtil;

public class JavaFile {

	private final String name;

	public JavaFile(File file) {
		name = getName(file);
	}
	public String getName() {
		return name;
	}

	public String getName(File file) {
		try(Scanner sc = new Scanner(file)) {
			while(sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if(line.startsWith("package"))
					return line.substring(8, line.length()-1) +"."+ FileUtil.getName(file);
			}
		}catch(FileNotFoundException e) {
			Log.warn(e);
		}
		return FileUtil.getName(file);
	}

}
