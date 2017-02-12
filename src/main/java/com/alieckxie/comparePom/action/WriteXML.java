package com.alieckxie.comparePom.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.alieckxie.comparePom.bean.DependencyBean;

public class WriteXML {

	private SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

	private TransformerHandler transformerHandler;

	private Transformer transformer;

	private String filePath;

	private AttributesImpl attributes;

	private String rootElement;

	private static int level = 0;

	public WriteXML(String filePath, String rootElement) {
		this.filePath = filePath;
		this.rootElement = rootElement;
	}

	public void init() throws IOException, TransformerException {
		transformerHandler = saxTransformerFactory.newTransformerHandler();
		transformer = transformerHandler.getTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		StreamResult streamResult = new StreamResult(new FileOutputStream(file));
		transformerHandler.setResult(streamResult);
		attributes = new AttributesImpl();
	}

	public void startDocWrite() throws SAXException {
		startDocWrite(this.rootElement);
	}

	public void startDocWrite(String rootElement) throws SAXException {
		startDocWrite(rootElement, null);
	}

	public void startDocWrite(String rootElement, Attributes attributes) throws SAXException {
		transformerHandler.startDocument();
		appendNewLineAndTab();
		transformerHandler.startElement("", "", rootElement, attributes);
	}

	public void endDocWrite() throws SAXException {
		writeEndElement(this.rootElement);
		transformerHandler.endDocument();
	}

	public void writeWholeElement(String elementName, String value, Attributes attributes) throws SAXException {
		writeStartElement(elementName);
		transformerHandler.characters(value.toCharArray(), 0, value.length());
		writeEndElement(elementName);
	}

	public void writeStartElement(String elementName) throws SAXException {
		level++;
		appendNewLineAndTab();
		transformerHandler.startElement("", "", elementName, attributes);
	}

	public void writeStartElement(String elementName, Attributes attributes) throws SAXException {
		level++;
		appendNewLineAndTab();
		transformerHandler.startElement("", "", elementName, attributes);
	}

	public void writeEndElement(String elementName) throws SAXException {
		level--;
		transformerHandler.endElement("", "", elementName);
	}

	public void writePadEndElement(String elementName) throws SAXException {
		appendNewLineAndTab();
		level--;
		transformerHandler.endElement("", "", elementName);
	}

	public void writePomHead() throws SAXException {
		attributes.clear();
		writeWholeElement("modelVersion", "4.0.0", attributes);
		appendNewLineAndTab();
		writeWholeElement("groupId", "com.alieckxie", attributes);
		writeWholeElement("artifactId", "comparePom", attributes);
		writeWholeElement("version", "0.0.1-SNAPSHOT", attributes);
		writeWholeElement("packaging", "jar", attributes);
		appendNewLineAndTab();
		writeWholeElement("name", "comparePom", attributes);
		writeWholeElement("url", "http://maven.apache.org", attributes);
		appendNewLineAndTab();
		writeStartElement("properties");
		writeWholeElement("project.build.sourceEncoding", "UTF-8", attributes);
		writePadEndElement("properties");
		appendNewLineAndTab();
	}

	public void writePomFoot() throws SAXException {
		attributes.clear();
		appendNewLineAndTab();
		writeStartElement("build");
		writeStartElement("plugins");
		writeStartElement("plugin");
		writeWholeElement("artifactId", "maven-compiler-plugin", attributes);
		writeWholeElement("version", "2.3.2", attributes);
		writeStartElement("configuration");
		writeWholeElement("target", "1.8", attributes);
		writeWholeElement("source", "1.8", attributes);
		writeWholeElement("encoding", "UTF-8", attributes);
		writePadEndElement("configuration");
		writePadEndElement("plugin");
		writePadEndElement("plugins");
		writePadEndElement("build");
	}
	
	public void writePom(Set<DependencyBean> dependencyBeanSet) {
		try {
			// 1、开始写文档前的准备工作
			init();
			// 2、文档开启
			attributes = new AttributesImpl();
			attributes.addAttribute("", "", "xmlns", "type", "http://maven.apache.org/POM/4.0.0");
			attributes.addAttribute("", "", "xmlns:xsi", String.class.getName(), "http://www.w3.org/2001/XMLSchema-instance");
			attributes.addAttribute("", "", "xsi:schemaLocation", String.class.getName(), "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
			startDocWrite("project", attributes);
			// 3、写Pom头
			writePomHead();
			// 4、写dependency
			attributes.clear();
			writeStartElement("dependencies");
			for (DependencyBean dependencyBean : dependencyBeanSet) {
				writeStartElement("dependency");
				writeWholeElement("groupId", dependencyBean.getGroupId(), attributes);
				writeWholeElement("artifactId", dependencyBean.getArtifactId(), attributes);
				writeWholeElement("version", dependencyBean.getVersion(), attributes);
				writeWholeElement("scope", dependencyBean.getScope(), attributes);
				writePadEndElement("dependency");
			}
			writePadEndElement("dependencies");
			// 5、写Pom脚
			writePomFoot();
			// 6、结束文档写入
			endDocWrite();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void appendNewLineAndTab() throws SAXException {
		String pad = "\n";
		for (int i = 0; i < level; i++) {
			pad += "\t";
		}
		transformerHandler.characters(pad.toCharArray(), 0, pad.length());
	}
}
