package com.alieckxie.comparePom.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alieckxie.comparePom.bean.DependencyBean;

public class PomParser extends DefaultHandler {

	private String fileName;

	private DependencyBean dependencyBean = null;

	private String currentElement;

	private boolean isEnteredDependencyTag = false;
	
	private Map<DependencyBean, String> dependencyBeans;

	public void readPom(String path) {
		this.fileName = path;
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(new File(path), this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace(); // TODO 优化异常处理使在多线程读取比较时不中断，待查证修改
		}
	}

	public void readPom(File file) {
		this.fileName = file.getName();
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(file, this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace(); // TODO 优化异常处理使在多线程读取比较时不中断，待查证修改
		}
	}

	public Map<DependencyBean, String> getDependencyBeanMap() {
		return dependencyBeans;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始读取文档：" + fileName);
		dependencyBeans = new HashMap<DependencyBean, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		currentElement = qName;
		if ("dependency".equals(qName)) {
			dependencyBean = new DependencyBean();
			isEnteredDependencyTag = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		currentElement = null;
		if ("dependency".equals(qName)) {
			dependencyBeans.put(dependencyBean, dependencyBean.getVersion());
			isEnteredDependencyTag = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch, start, length).trim();
		if (isEnteredDependencyTag) {
			if ("groupId".equals(currentElement)) {
				dependencyBean.setGroupId(content);
			}
			if ("artifactId".equals(currentElement)) {
				dependencyBean.setArtifactId(content);
			}
			if ("version".equals(currentElement)) {
				dependencyBean.setVersion(content);
			}
			if ("scope".equals(currentElement)) {
				dependencyBean.setScope(content);
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println(fileName + "-> 文档读取完毕");
		dependencyBean = null;
	}
}
