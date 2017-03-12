package com.alieckxie.comparePom.action;

import java.util.Map;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomVersionExplorer;

public class DoUpdate {

	public Map<DependencyBean, String> updateFromRepo(Map<DependencyBean, String> dependencyBeanMap) {
		return doUpdate(dependencyBeanMap);
	}

	private Map<DependencyBean, String> doUpdate(Map<DependencyBean, String> dependencyBeanMap) {
		for (DependencyBean dependencyBean : dependencyBeanMap.keySet()) {
			String latestVersionFromRepo = PomVersionExplorer.getLatestVersionFromRepo(dependencyBean.getGroupId(),
					dependencyBean.getArtifactId());
			if (!"".equals(latestVersionFromRepo) && !dependencyBean.getVersion().equals(latestVersionFromRepo)) {
				System.out.println(dependencyBean.toString() + "将要更新，最新版本为：" + latestVersionFromRepo);
				dependencyBean.setVersion(latestVersionFromRepo);
				dependencyBeanMap.put(dependencyBean, latestVersionFromRepo);
			}
		}
		return dependencyBeanMap;
	}

}
