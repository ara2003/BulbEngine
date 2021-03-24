package com.greentree.loading.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.greentree.common.loading.FileSystemLocation;
import com.greentree.common.loading.ResourceLoader;

/**
 * testing class FileSystemLocation
 * @author Arseny Latyshev
 *
 * @see FileSystemLocation
 */
public class FileSystemLocationTest {
	

	Path testDir;
	
	@BeforeEach
	void createTempDir() throws IOException {
		testDir = Files.createTempDirectory("junit");
		ResourceLoader.addResourceLocation(new FileSystemLocation(testDir));
	}
	
	@AfterEach
	void deleteTempDir() throws IOException {
		delete(testDir.toFile());
	}

	private void delete(File file){
		if(file == null)return;
		if(file.listFiles() == null)return;
		for(File c : file.listFiles()) {
			delete(c);
		}
		file.delete();
	}
	
	
	
	@ParameterizedTest
	@ValueSource(strings = {"test.txt", "Hello"})
	void foundSomething(String text) throws IOException {
		final String file = "test"+text+".txt";
		Path path = testDir.resolve(file);
		try(FileWriter w = new FileWriter(path.toFile())){
			w.write(text);
		}catch (Exception e) {
			throw e;
		}
		path.toFile().createNewFile();
		URL url = ResourceLoader.getResource(file);
		InputStream inputStream = ResourceLoader.getResourceAsStream(file);
		assertNotNull(url);
		assertNotNull(inputStream);
		assertEquals(text, new String(inputStream.readAllBytes()));
		assertEquals(text, new String(url.openStream().readAllBytes()));
		
	}
}
