package com.greentree.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class XMLParser {
	
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private final static LoadingCache<InputStream, XMLElement> getKesh = CacheBuilder.newBuilder().softValues()
		.build(new CacheLoader<>() {
			@Override
			public XMLElement load(final InputStream in) throws IOException {
				return parse0(in);
			}
		});
	
	private XMLParser() {
	}
	
	public static XMLElement parse(final File file) throws IOException {
		try {
			return XMLParser.parse(new FileInputStream(file));
		}catch(final IOException e) {
			throw new IOException("file " + file + "not found", e);
		}
	}
	
	public static XMLElement parse(final File root, final String string) throws IOException {
		return XMLParser.parse(new File(root, string));
	}
	
	public static XMLElement parse(final File root, final String file, final String string) throws IOException {
		return XMLParser.parse(root, file + "." + string);
	}
	
	public static XMLElement parse(final InputStream in) throws IOException {
		try {
			return getKesh.get(in);
		}catch(ExecutionException e) {
			throw new IOException("file " + in + " not found", e);
		}
	}
	
	public static XMLElement parse0(final InputStream in) throws IOException {
		try {
			XMLElement element;
			final DocumentBuilder builder = XMLParser.factory.newDocumentBuilder();
			final Document doc = builder.parse(in);
			element = new XMLElement(doc.getDocumentElement());
			return element;
		}catch(ParserConfigurationException | SAXException | IOException e) {
			throw new IOException("file " + in + " not found", e);
		}
	}
	
	public static XMLElement parse(final String file) throws IOException {
		return XMLParser.parse(new File(file));
	}
}
