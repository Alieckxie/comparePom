package com.alieckxie.comparePom;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.alieckxie.comparePom.action.DoUpdate;
import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomParser;
import com.alieckxie.comparePom.util.XMLWriter;

public class WriteXMLTest {
	
	@Test
	public void testWrite(){
//		String fileName = "C:\\Users\\ht-xiexy\\git\\comparePom\\src\\test\\resources\\output.xml";
//		String fileName = WriteXML.class.getClassLoader().getResource("output.xml").getPath();
//		String fileName = "src/test/resources/output.xml";
//		WriteXML writeXML = new WriteXML(fileName);
//		writeXML.init();
//		writeXML.writeElement("project");
	}
	
	@Test
	public void testAppendTab(){
		try {
			String fileName = "src/test/resources/output.xml";
			XMLWriter writeXML = new XMLWriter(fileName, "project");
			writeXML.init();
			writeXML.startDocWrite();
			writeXML.writeWholeElement("alieckxie", "123", null);
			writeXML.endDocWrite();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAndUpdateAndWrite(){
		try {
			PomParser pomParser = new PomParser();
			pomParser.readPom("src/test/resources/standard-pom.xml");
			DoUpdate doUpdate = new DoUpdate();
			doUpdate.updateFromRepo(pomParser.getDependencyBeanMap());
			String fileName = "src/test/resources/output.xml";
			XMLWriter writeXML = new XMLWriter(fileName, "project");
			writeXML.init();
			writeXML.startDoc();
			writeXML.writeElementBeans(pomParser.getBeforeDependencyBeans());
			for (DependencyBean dependencyBean : pomParser.getDependencyBeanMap().keySet()) {
				writeXML.writePadStartElement("dependency");
				writeXML.writeWholeElement("groupId", dependencyBean.getGroupId(), null);
				writeXML.writeWholeElement("artifactId", dependencyBean.getArtifactId(), null);
				writeXML.writeWholeElement("version", dependencyBean.getVersion(), null);
				writeXML.writeWholeElement("scope", dependencyBean.getScope(), null);
				writeXML.writePadEndElement("dependency");
			}
			writeXML.writeElementBeans(pomParser.getAfterDependencyBeans());
			writeXML.endDoc();
		} catch (IOException | TransformerException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
