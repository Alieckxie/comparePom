package com.alieckxie.comparePom.action;

import java.util.Set;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.PomVersionExplorer;

public class DoUpdate {
	
	public void updateFromRepo(Set<DependencyBean> dependencyBeans){
		doUpdate(dependencyBeans);
	}
	
	private Set<DependencyBean> doUpdate(Set<DependencyBean> dependencyBeans){
		for (DependencyBean dependencyBean : dependencyBeans) {
			String latestVersionFromRepo = PomVersionExplorer.getLatestVersionFromRepo(dependencyBean.getGroupId(),
					dependencyBean.getArtifactId());
			if (!dependencyBean.getVersion().equals(latestVersionFromRepo)) {
				System.out.println(dependencyBean.toString() + "将要更新，最新版本为：" + latestVersionFromRepo);
				dependencyBean.setVersion(latestVersionFromRepo);
			}
		}
		return dependencyBeans;
	}

}
