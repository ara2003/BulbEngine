package com.greentree.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser {
	
	private static DocumentBuilderFactory factory;
	private static Map<InputStream, XMLElement> map = new HashMap<>();
	
	private XMLParser() {
	}
	
	public static XMLElement parse(final File file) throws IOException {
		try {
			return XMLParser.parse(new FileInputStream(file));
		}catch(IOException e) {
			throw new IOException("file " + file + "not found", e);
		}
	}
	
	public static void parse(final File file, final Object obj) {
	}
	
	public static XMLElement parse(final File root, final String string) throws IOException {
		return XMLParser.parse(new File(root, string));
	}
	
	public static XMLElement parse(final File root, final String file, final String string) throws IOException {
		return XMLParser.parse(root, file + "." + string);
	}
	
	public static XMLElement parse(final InputStream in) throws IOException {
		try {
			XMLElement element = map.get(in);
			if(element != null) return element;
			if(XMLParser.factory == null) XMLParser.factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = XMLParser.factory.newDocumentBuilder();
			final Document doc = builder.parse(in);
			element = new XMLElement(doc.getDocumentElement());
			map.put(in, element);
			return element;
		}catch(ParserConfigurationException | SAXException | IOException e) {
			throw new IOException("file " + in + "not found", e);
		}
	}
	
	public static XMLElement parse(final String file) throws IOException {
		return XMLParser.parse(new File(file));
	}
}
