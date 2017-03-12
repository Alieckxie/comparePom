package com.alieckxie.comparePom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.bean.ElementBean;
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
		System.out.println("=======================");
		List<ElementBean> beforeDependencyBeans = parser.getBeforeDependencyBeans();
		for (ElementBean elementBean : beforeDependencyBeans) {
			System.out.println(elementBean);
		}
		System.out.println("=======================");
		List<ElementBean> afterDependencyBeans = parser.getAfterDependencyBeans();
		for (ElementBean elementBean : afterDependencyBeans) {
			System.out.println(elementBean);
		}
	}
	
	@Test
	public void testChangeKeySet(){
		Map<DependencyBean,String> map = new HashMap<DependencyBean, String>();
		DependencyBean dependencyBean1 = new DependencyBean();
		dependencyBean1.setGroupId("com.alieckxie.test");
		dependencyBean1.setArtifactId("asd");
		dependencyBean1.setVersion("0.1.0.0");
		DependencyBean dependencyBean2 = new DependencyBean();
		dependencyBean2.setGroupId("com.alieckxie.test");
		dependencyBean2.setArtifactId("fgh");
		dependencyBean2.setVersion("0.1.0.0");
		DependencyBean dependencyBean3 = new DependencyBean();
		dependencyBean3.setGroupId("com.alieckxie.test");
		dependencyBean3.setArtifactId("zxc");
		dependencyBean3.setVersion("0.1.0.0");
		DependencyBean dependencyBean4 = new DependencyBean();
		dependencyBean4.setGroupId("com.alieckxie.test");
		dependencyBean4.setArtifactId("qwe");
		dependencyBean4.setVersion("0.1.0.0");
		map.put(dependencyBean1, "111111");
		map.put(dependencyBean2, "222222");
		map.put(dependencyBean3, "333333");
		Set<DependencyBean> set = map.keySet();
		System.out.println(set);
		for (DependencyBean dependencyBean : set) {
			dependencyBean.setVersion("0.2.0.0");
		}
		System.out.println(set);
		System.out.println(map.keySet());
	}
}
