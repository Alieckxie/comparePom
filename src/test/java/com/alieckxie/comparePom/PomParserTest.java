package com.alieckxie.comparePom;

import java.util.Map;

import org.junit.Test;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomParser;

public class PomParserTest {

	PomParser parser = new PomParser();

	@Test
	public void testReadPom(){
		parser.readPom("src/test/resources/standard-pom.xml");
		Map<DependencyBean, String> dependencyBeanMap = parser.getDependencyBeanMap();
		for (Map.Entry<DependencyBean, String> dependencyBeanEntry : dependencyBeanMap.entrySet()) {
			System.out.println(dependencyBeanEntry);
		}
	}
}
