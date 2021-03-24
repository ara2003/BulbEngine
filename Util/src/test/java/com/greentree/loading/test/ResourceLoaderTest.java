package com.greentree.loading.test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.greentree.common.loading.FileSystemLocation;
import com.greentree.common.loading.ResourceLoader;

/**
 * class of testing  ResourceLoader
 * 
 * @see ResourceLoader
 * 
 * @author Arseny Latyshev
 */
public class ResourceLoaderTest {
	
	
	
	@Test
	void testNullGetResource() {
		assertThrows(NullPointerException.class, () -> {
	    	ResourceLoader.getResource(null);
	    });
	}
	
	@Test
	void testNullGetResourceAsStream() {
		assertThrows(NullPointerException.class, () -> {
			ResourceLoader.getResourceAsStream(null);
	    });
	}
	@Test
	void testNullAddResourceLocation() {
		assertThrows(NullPointerException.class, () -> {
	    	ResourceLoader.addResourceLocation(null);
	    });
	}
	@Test
	void testNotNullAddResourceLocation() {
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(".")));
	}
	
}
