package com.alieckxie.comparePom;

import org.junit.Test;

import com.alieckxie.comparePom.action.WriteXML;

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
			WriteXML writeXML = new WriteXML(fileName, "project");
			writeXML.init();
			writeXML.startDocWrite();
			writeXML.writeWholeElement("alieckxie", "123", null);
			writeXML.endDocWrite();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
