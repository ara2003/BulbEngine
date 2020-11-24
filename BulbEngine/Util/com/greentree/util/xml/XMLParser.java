package com.greentree.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.greentree.engine.Log;

public class XMLParser {

	private static DocumentBuilderFactory factory;

	private XMLParser() {
	}

	public static XMLElement parse(final File file) {
		try {
			return XMLParser.parse(new FileInputStream(file));
		}catch(ParserConfigurationException | SAXException | IOException e) {
			Log.error("file " + file + "not found");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void parse(final File file, final Object obj) {
	}

	public static XMLElement parse(final File root, final String string) {
		return XMLParser.parse(new File(root, string));
	}

	public static XMLElement parse(final File root, final String file, final String string) {
		return XMLParser.parse(root, file + "." + string);
	}
	
	public static XMLElement parse(final InputStream in)
			throws ParserConfigurationException, SAXException, IOException {
		if(XMLParser.factory == null) XMLParser.factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = XMLParser.factory.newDocumentBuilder();
		final Document doc = builder.parse(in);
		return new XMLElement(doc.getDocumentElement());
	}

	public static XMLElement parse(final String file) {
		return XMLParser.parse(new File(file));
	}
}
