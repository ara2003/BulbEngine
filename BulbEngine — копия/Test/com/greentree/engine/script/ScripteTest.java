package com.greentree.engine.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.greentree.engine.FileManager;
import com.greentree.loading.xmlTest;

/**
 * @author Arseny Latyshev
 *
 */
public class ScripteTest {
	
	@Test
	public void test() throws IOException {
		
		String code = "print(int a) void{\n}\ntest(int a)void{\nint a = 5;a = a + 3;\n}";
		
		ScripteCompiler comp = new ScripteCompiler(code);
		
		
		
//		String fileName = "Test\\" + getClass().getPackageName().replace(".", "\\") + "\\test.txt";
		
//		File file =FileManager.getFile(fileName);
		
//		FileOutputStream out = new FileOutputStream(file);
		
//		out.write(code.getBytes());
		
//		out.close();
		
		
	}
	
}
