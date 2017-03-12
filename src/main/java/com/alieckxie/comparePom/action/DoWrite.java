package com.alieckxie.comparePom.action;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomParser;
import com.alieckxie.comparePom.util.XMLWriter;

public class DoWrite {
	
	public static void doWrite(String fileName, PomParser pomParser)
			throws SAXException, IOException, TransformerException {
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
		System.out.println("合并完毕！");
	}

}
