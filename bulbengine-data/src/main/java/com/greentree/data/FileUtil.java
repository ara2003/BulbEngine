package com.greentree.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.greentree.action.EventAction;
import com.greentree.common.logger.Log;
import com.greentree.data.loading.ResourceLoader;

public abstract class FileUtil {

	private final static EventAction<File> openFile = new EventAction<>();

	private static void addFileToZip0(File file, String name, ZipOutputStream zout) throws FileNotFoundException, IOException {
		try(FileInputStream fis = new FileInputStream(file)){
			ZipEntry entry1  =new ZipEntry(name);
			zout.putNextEntry(entry1);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			zout.write(buffer);
		}
	}

	public static void addUseFileListener(Consumer<File> c) {
		openFile.addListener(c);
	}

	public static void checkDirectory(File directory) {
		if(directory.exists()) {
			if(!directory.isDirectory())throw new IllegalArgumentException("is not directory " + directory);
		}else throw new IllegalArgumentException("not exists " + directory);
	}

	public static void checkFile(File file) {
		if(file.exists()) {
			if(!file.isFile())throw new IllegalArgumentException("is not file " + file);
		}else throw new IllegalArgumentException("not exists " + file);
	}
	public static void copy(File from, File to) throws IOException {
		Path srcPath = from.toPath();
		Path destPath = to.toPath();

		Files.walkFileTree(srcPath, new CopyDirVisitor(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING));

	}

	public static boolean delete(File file) {
		if(!file.exists())
			throw new IllegalArgumentException(file.toString());
		if(file.isFile())return file.delete();
		if(file.isDirectory()) {
			for(var f : file.listFiles())delete(f);
			return file.delete();
		}
		throw new IllegalArgumentException(file.toString());
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
	public static File getFile(String file) {
		try {
			return new File(ResourceLoader.getResource(file).toURI().getPath());
		}catch(URISyntaxException e) {
			Log.warn(e);
		}
		return null;
	}

	public static String getName(File file) {
		return getName(file.getName());
	}

	public static String getName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if(index == -1)return fileName;
		return fileName.substring(0, index);
	}
	public static String getType(File file) {
		return getType(file.getName());
	}

	public static String getType(String fileName) {
		int index = fileName.lastIndexOf('.');
		if(index == -1)return "";
		return fileName.substring(index+1);
	}

	public static boolean isEmpty(File file) {
		if(file.isDirectory())return file.list().length == 0;
		throw new IllegalArgumentException(file.toString());
	}

	@Deprecated
	public static void openExploer(File file) {
		//		if(System.getenv())
		System.out.println(System.getProperty("os.name"));
	}

	public static FileInputStream openStream(File file) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
		openFile.action(file);
		return stream;

	}

	public static String readFile(File file) throws FileNotFoundException {
		return InputStreamUtil.readString(new FileInputStream(file));
	}


	public static void write(File to, InputStream in) throws FileNotFoundException, IOException {
		InputStreamUtil.copy(in, new FileOutputStream(to));
	}


	public static void write(File to, String text) throws FileNotFoundException, IOException {
		write(to, new ByteArrayInputStream(text.getBytes()));
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

	private static class CopyDirVisitor extends SimpleFileVisitor<Path>
	{
		private final Path fromPath;
		private final Path toPath;
		private final CopyOption copyOption;

		public CopyDirVisitor(Path fromPath, Path toPath, CopyOption copyOption)
		{
			this.fromPath = fromPath;
			this.toPath = toPath;
			this.copyOption = copyOption;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
		{
			Path targetPath = toPath.resolve(fromPath.relativize(dir));
			if( !Files.exists(targetPath) ) Files.createDirectory(targetPath);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
		{
			Files.copy(file, toPath.resolve(fromPath.relativize(file)), copyOption);
			return FileVisitResult.CONTINUE;
		}
	}

}
