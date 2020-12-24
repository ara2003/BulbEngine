package com.greentree.loading;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import com.greentree.serialize.FileParser;
import com.greentree.serialize.GsonFileParser;
import com.greentree.serialize.fileSufix;

/**
 * @author Arseny Latyshev
 */
public class xmlTest {
	
	@Test
	public void saveTest() throws FileNotFoundException {
		FileParser fp = new GsonFileParser();
		Person person = new Person("ara");
		
		String file = "Test\\" + xmlTest.class.getPackageName().replace(".", "\\") + "\\test";
		
		fp.save(file, person);
		
		assertEquals(person, fp.load(file, person.getClass()));
		
		File file1 = new File(file+".txt");
		
		file1.delete();
		
	}
	
}

@fileSufix("txt")
class Person {
	
	private String name;

	public Person(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Person)) return false;
		Person other = (Person) obj;
		if(name == null) {
			if(other.name != null) return false;
		}else if(!name.equals(other.name)) return false;
		return true;
	}

}