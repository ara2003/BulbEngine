package com.greentree.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<XMLElement> children;
	private transient final Element dom;
	private final String name;
	
	XMLElement(final Element xmlElement) {
		dom = xmlElement;
		name = dom.getTagName();
	}
	
	public XMLElement(final File root, final String file) throws IOException {
		this(XMLParser.parse(root, file));
	}
	
	public XMLElement(final String file) throws IOException {
		this(XMLParser.parse(file));
	}
	
	XMLElement(final XMLElement xmlElement) {
		dom = xmlElement.dom;
		name = xmlElement.name;
	}
	
	public XMLElement(InputStream data) throws IOException {
		this(XMLParser.parse(data));
		
	}

	public String getAttribute(final String name) {
		return dom.getAttribute(name);
	}
	
	public String getAttribute(final String name, final String def) {
		final String value = dom.getAttribute(name);
		if((value == null) || (value.length() == 0)) return def;
		return value;
	}
	
	public String[] getAttributeNames() {
		final NamedNodeMap map = dom.getAttributes();
		final String[] names = new String[map.getLength()];
		for(int i = 0; i < names.length; ++i) names[i] = map.item(i).getNodeName();
		return names;
	}
	
	public Map<String, String> getAttributes() {
		final String[] n = getAttributeNames();
		final Map<String, String> map = new HashMap<>(n.length);
		for(final String str : n) map.put(str, getAttribute(str));
		return map;
	}
	
	public boolean getBooleanAttribute(final String name) throws XMLException {
		final String value = this.getAttribute(name);
		if(value.equalsIgnoreCase("true")) return true;
		if(value.equalsIgnoreCase("false")) return false;
		throw new XMLException("Value read: '" + this.getAttribute(name) + "' is not a boolean");
	}
	
	public boolean getBooleanAttribute(final String name, final boolean def) throws XMLException {
		final String value = this.getAttribute(name, "" + def);
		if(value.equalsIgnoreCase("true")) return true;
		if(value.equalsIgnoreCase("false")) return false;
		throw new XMLException("Value read: '" + this.getAttribute(name, "" + def) + "' is not a boolean");
	}
	
	public List<XMLElement> getChildren() {
		if(children != null) return children;
		final NodeList list = dom.getChildNodes();
		children = new ArrayList<>();
		for(int i = 0; i < list.getLength(); ++i)
			if(list.item(i) instanceof Element) children.add(new XMLElement((Element) list.item(i)));
		return children;
	}
	
	public XMLElement getChildren(final String name) {
		final List<XMLElement> c = getChildrens(name);
		if(c.isEmpty()) return null;
		return c.get(0);
	}
	
	public List<XMLElement> getChildrens(final String name) {
		final List<XMLElement> selected = new ArrayList<>();
		final List<XMLElement> children = getChildren();
		for(int i = 0; i < children.size(); ++i)
			if(children.get(i).getName().equals(name)) selected.add(children.get(i));
		return selected;
	}
	
	public String getContent() {
		String content = "";
		final NodeList list = dom.getChildNodes();
		for(int i = 0; i < list.getLength(); ++i)
			if(list.item(i) instanceof Text) content += list.item(i).getNodeValue();
		return content.replace("\n", "");
	}
	
	public double getDoubleAttribute(final String name) throws XMLException {
		try {
			return Double.parseDouble(this.getAttribute(name));
		}catch(final NumberFormatException e) {
			throw new XMLException("Value read: '" + this.getAttribute(name) + "' is not a double", e);
		}
	}
	
	public double getDoubleAttribute(final String name, final double def) throws XMLException {
		try {
			return Double.parseDouble(this.getAttribute(name, "" + def));
		}catch(final NumberFormatException e) {
			throw new XMLException("Value read: '" + this.getAttribute(name, "" + def) + "' is not a double", e);
		}
	}
	
	public int getIntAttribute(final String name) throws XMLException {
		try {
			return Integer.parseInt(this.getAttribute(name));
		}catch(final NumberFormatException e) {
			throw new XMLException("Value read: '" + this.getAttribute(name) + "' is not an integer", e);
		}
	}
	
	public int getIntAttribute(final String name, final int def) throws XMLException {
		try {
			return Integer.parseInt(this.getAttribute(name, "" + def));
		}catch(final NumberFormatException e) {
			throw new XMLException("Value read: '" + this.getAttribute(name, "" + def) + "' is not an integer", e);
		}
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		String res = "<" + name;
		final String[] na = getAttributeNames();
		for(final String n : na) res += " " + n + "=\"" + getAttribute(n) + "\"";
		if(getChildren().isEmpty() && getContent().equals("")) res += ">" + getContent() + "</" + name + ">\n";
		else {
			res += ">" + getContent();
			if(!getChildren().isEmpty()) {
				res += "\n\t";
				for(final XMLElement e : getChildren()) res += e.toString().replaceAll("\n", "\n\t");
				res = res.substring(0, res.lastIndexOf("\t"));
			}
			res += "</" + name + ">\n";
		}
		return res;
	}

	public InputStream getIputStream() {
		return new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+toString()).getBytes());
	}
}
