package com.greentree.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.greentree.action.EventAction;
import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;

public abstract class FileUtil {

	private final static EventAction<File> openFile = new EventAction<>();

	public static void addUseFileListener(Consumer<File> c) {
		openFile.addListener(c);
	}
	
	private static void addFileToZip0(File file, String name, ZipOutputStream zout) throws FileNotFoundException, IOException {
		try(FileInputStream fis = new FileInputStream(file)){
			ZipEntry entry1  =new ZipEntry(name);
			zout.putNextEntry(entry1);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			zout.write(buffer);
		}
	}

	public static void dirToZip(File dir, File zipFile) throws FileNotFoundException, IOException {
		int len = 0;
		while(dir.getAbsolutePath().charAt(len) == zipFile.getAbsolutePath().charAt(len))len++;

		try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));){
			zout.setLevel(9);
			for(File file : getAllFile(dir)) addFileToZip0(file, file.getAbsolutePath().substring(len), zout);
		}
	}

	public static Collection<File> getAllFile(File file) {
		Collection<File> res = new ArrayList<>();
		Queue<File> dir = new LinkedList<>();
		if(file.isDirectory())dir.add(file);
		if(file.isFile())res.add(file);
		while(!dir.isEmpty()) {
			File d = dir.remove();
			for(File f : d.listFiles()) {
				if(f.isDirectory())dir.add(f);
				if(f.isFile())res.add(f);
			}
		}
		return res;
	}
	public static String getName(File f) {
		int index = f.getName().lastIndexOf('.');
		if(index == -1)return f.getName();
		return f.getName().substring(0, index);
	}

	public static String getType(File f) {
		int index = f.getName().lastIndexOf('.');
		if(index == -1)return "";
		return f.getName().substring(index+1);
	}

	public static FileInputStream openStream(File file) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		openFile.action(file);
		return stream;

	}
	public static String readFile(File file) throws FileNotFoundException {
		StringBuilder b = new StringBuilder();
		try(Scanner sc = new Scanner(file)){
			while(sc.hasNextLine()) b.append(sc.nextLine());
		}
		return b.toString();
	}

	public static void zipToDir(File zip, File dir) throws FileNotFoundException, IOException {
		try(ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));){
			ZipEntry entry;
			while((entry=zin.getNextEntry())!=null) {
				File file = new File(dir + "\\" + entry.getName());

				if(!file.exists()) {
					file.getParentFile().mkdir();
					file.createNewFile();
				}
				FileOutputStream fout = new FileOutputStream(file);
				for (int c = zin.read(); c != -1; c = zin.read()) fout.write(c);

				fout.flush();
				zin.closeEntry();
				fout.close();
			}
		}
	}

	public static void isFile(File file) {
		if(file.exists()) {
			if(!file.isFile())throw new IllegalArgumentException("is not file " + file);
		}else throw new IllegalArgumentException("not exists " + file);
	}
	public static void isDirectory(File directory) {
		if(directory.exists()) {
			if(!directory.isDirectory())throw new IllegalArgumentException("is not directory " + directory);
		}else throw new IllegalArgumentException("not exists " + directory);
	}

	public static File getFile(String file) {
		try {
			return new File(ResourceLoader.getResource(file).toURI().getPath());
		}catch(URISyntaxException e) {
			Log.warn(e);
		}
		return null;
	}

}
