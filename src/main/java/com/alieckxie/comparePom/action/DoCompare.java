package com.alieckxie.comparePom.action;

import java.util.HashMap;
import java.util.Map;

import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.CompareUtil;

public class DoCompare {

	private static void doCompare(Map<DependencyBean, String> pomBeCheckedMap,
			Map<DependencyBean, String> pomBeStandardMap, boolean isNeedMerge) {
		Map<String, String> resultMap = new HashMap<>();
		for (Map.Entry<DependencyBean, String> pomBeChecked : pomBeCheckedMap.entrySet()) {
			DependencyBean beCheckedDependencyBean = pomBeChecked.getKey();
			String standardVersion = pomBeStandardMap.get(beCheckedDependencyBean);
			if (standardVersion == null) {
				resultMap.put(beCheckedDependencyBean.toString(), "标准pom中没有该构件");
				continue;
			}
			String result;
			try {
				result = CompareUtil.compareVersion(standardVersion, pomBeChecked.getValue());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("该构件的版本号比较出错：" + beCheckedDependencyBean);
				continue;
			}
			if (result != null) {
				resultMap.put(beCheckedDependencyBean.toString(), result);
				if(isNeedMerge){
					beCheckedDependencyBean.setVersion(result);
				}
			}
		}
		if (resultMap.size() != 0) {
			for (Map.Entry<String, String> aResult : resultMap.entrySet()) {
				System.out.println(aResult);
			}
		} else {
			System.out.println("所有构件的版本都是最新");
		}
	}

	public static void compareToStandard(Map<DependencyBean, String> pomBeCheckedMap,
			Map<DependencyBean, String> pomBeStandardMap) {
		doCompare(pomBeCheckedMap, pomBeStandardMap, false);
	}

	public static void compareToStandardAndMerge(Map<DependencyBean, String> pomBeCheckedMap,
			Map<DependencyBean, String> pomBeStandardMap) {
		doCompare(pomBeCheckedMap, pomBeStandardMap, true);
	}

}
