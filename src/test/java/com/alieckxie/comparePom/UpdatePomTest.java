package com.alieckxie.comparePom;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomParser;
import com.alieckxie.comparePom.util.PomVersionExplorer;
import com.alieckxie.comparePom.util.XMLWriter;

public class UpdatePomTest {

	@Test
	public void testUpdateStandardPom() {
		PomParser pomParser = new PomParser();
		pomParser.readPom("src/test/resources/standard-pom.xml");
		Map<DependencyBean, String> beanMap = pomParser.getDependencyBeanMap();
		Set<DependencyBean> keySet = beanMap.keySet();
		for (DependencyBean dependencyBean : keySet) {
			String latestVersionFromRepo = PomVersionExplorer.getLatestVersionFromRepo(dependencyBean.getGroupId(),
					dependencyBean.getArtifactId());
			if (!dependencyBean.getVersion().equals(latestVersionFromRepo)) {
				System.out.println(dependencyBean.toString() + "将要更新，最新版本为：" + latestVersionFromRepo);
				dependencyBean.setVersion(latestVersionFromRepo);
			}
		}
		String fileName = "src/test/resources/output.xml";
		XMLWriter writeXML = new XMLWriter(fileName, "project");
		writeXML.writePom(keySet);
	}

}
