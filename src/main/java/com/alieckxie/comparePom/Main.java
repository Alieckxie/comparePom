package com.alieckxie.comparePom;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.alieckxie.comparePom.action.DoCompare;
import com.alieckxie.comparePom.action.DoUpdate;
import com.alieckxie.comparePom.action.WriteXML;
import com.alieckxie.comparePom.bean.ArgumentBean;
import com.alieckxie.comparePom.bean.DependencyBean;
import com.alieckxie.comparePom.util.FileCollector;
import com.alieckxie.comparePom.util.PomParser;

public class Main {

	private static String defaultStandardPomPath = Main.class.getClassLoader().getResource("standard-pom.xml")
			.getPath();

	public static void main(String[] args) {
		try {
			ArgumentBean argumentBean = parseArguments(args);
			if (!argumentBean.isStandardSpecified()) {
				argumentBean.setStandardPomPath(defaultStandardPomPath);
			}
			doAction(argumentBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArgumentBean parseArguments(String[] args) throws Exception {
		/**
		 * 参数规划：
		 * 			 -u			表示更新
		 * 			 -s XXX 	表示指定标准pom的路径，如：C:\asd\desktop\standard-pom.xml
		 * 			 -p XXX 	表示指定受检pom的路径，如：C:\asd\desktop\pom.xml
		 * 			 -m 		表示进行合并比较结果至受检pom
		 * 示例：
		 * 		java -jar comparePom.jar -p XXX 				将指定pom与内置标准pom进行比较
		 * 		java -jar comparePom.jar -p XXX -s XXX 		将指定pom与指定标准pom进行比较
		 * 		java -jar comparePom.jar -u 					更新内置pom
		 * 		java -jar comparePom.jar -u -s XXX 				更新指定的标准pom
		 * 		java -jar comparePom.jar -u -s XXX -m -p XXX 	更新指定的标准pom，然后比较受检pom并将比较结果合并至受检pom
		 * 		java -jar comparePom.jar -u -m -p XXX 			更新内置的标准pom，然后比较受检pom并将比较结果合并至受检pom
		 * 		java -jar comparePom.jar -m -p XXX 				比较受检pom和内置的标准pom，并将比较结果合并至受检pom
		 * 		java -jar comparePom.jar -m -p XXX -s XXX		比较受检pom和指定的标准pom，并将比较结果合并至受检pom
		 * 		java -jar comparePom.jar -m -p XXX 			效果同上
		 */
		if (args.length == 0) {
			throw new Exception("需要输入参数");
		}
		ArgumentBean argumentBean = new ArgumentBean();
		for (int i = 0; i < args.length; i++) {
			if ("-u".equals(args[i])) {
				if (argumentBean.isNeedUpdate()) {
					throw new Exception("重复参数：" + args[i]);
				}
				argumentBean.setNeedUpdate(true);
				continue;
			}
			if ("-m".equals(args[i])) {
				if (argumentBean.isNeedMerge()) {
					throw new Exception("重复参数：" + args[i]);
				}
				argumentBean.setNeedMerge(true);
				continue;
			}
			if ("-p".equals(args[i])) {
				if (argumentBean.isCheckedSpecified()) {
					throw new Exception("重复参数：" + args[i]);
				}
				if ((i + 1) > args.length - 1) {
					throw new Exception("参数异常：" + args[i] + "-->" + "无内容");
				} else if ("-u".equals(args[i + 1]) || "-c".equals(args[i + 1]) || "-m".equals(args[i + 1])
						|| "-s".equals(args[i + 1]) || "-p".equals(args[i + 1])) {
					throw new Exception("参数异常：" + args[i] + "-->" + args[i + 1]);
				}
				argumentBean.setCheckedSpecified(true);
				argumentBean.setCheckedPomPath(args[i + 1]);
				i++;
				continue;
			}
			if ("-s".equals(args[i])) {
				if (argumentBean.isStandardSpecified()) {
					throw new Exception("重复参数：" + args[i]);
				}
				if ((i + 1) > args.length - 1) {
					throw new Exception("参数异常：" + args[i] + "-->" + "无内容");
				} else if ("-u".equals(args[i + 1]) || "-c".equals(args[i + 1]) || "-m".equals(args[i + 1])
						|| "-s".equals(args[i + 1]) || "-p".equals(args[i + 1])) {
					throw new Exception("参数异常：" + args[i] + "-->" + args[i + 1]);
				}
				argumentBean.setStandardSpecified(true);
				argumentBean.setStandardPomPath(args[i + 1]);
				i++;
				continue;
			}
			throw new Exception("未知参数：" + args[i]);
		}
		if ((argumentBean.isCheckedSpecified() && argumentBean.isNeedMerge())) {
			System.out.println("至少进行比较");
		} else if (argumentBean.isNeedUpdate()) {
			System.out.println("至少需要更新");
		} else {
			throw new Exception("异常操作参数");
		}
		return argumentBean;
	}
	
	public static void doAction(ArgumentBean argumentBean) throws Exception {
		// 判断标准pom是否是文件
		if (!new File(argumentBean.getStandardPomPath()).isFile()) {
			throw new Exception("这不是一种标准pom！");
		}
		// 创建读取器读取标准pom文件
		PomParser parser = new PomParser();
		parser.readPom(argumentBean.getStandardPomPath());
		Map<DependencyBean, String> pomBeStandardMap = parser.getDependencyBeanMap();
		// 判断是否需要更新
		if (argumentBean.isNeedUpdate()) {
			DoUpdate doUpdate = new DoUpdate();
			doUpdate.updateFromRepo(pomBeStandardMap.keySet());
		}

		// 收集受检pom路径下的所有pom文件
		List<File> pomList = FileCollector.collectPom(argumentBean.getCheckedPomPath());

		// 判断是否需要将差异合并至受检pom
		if (argumentBean.isNeedMerge()) {
			for (File pom : pomList) {
				// 读取受检pom
				parser.readPom(pom);
				Map<DependencyBean, String> pomBeCheckedMap = parser.getDependencyBeanMap();
				// 进行比较，生成报告，合并结果，写出到文件
				DoCompare.compareToStandardAndMerge(pomBeCheckedMap, pomBeStandardMap);
				WriteXML writeXML = new WriteXML(argumentBean.getCheckedPomPath(), "project");
				writeXML.writePom(pomBeCheckedMap.keySet());
			}
		} else {
			for (File pom : pomList) {
				// 读取受检pom
				parser.readPom(pom);
				Map<DependencyBean, String> pomBeCheckedMap = parser.getDependencyBeanMap();
				// 进行比较并只生成报告
				DoCompare.compareToStandard(pomBeCheckedMap, pomBeStandardMap);
			}
		}
	}

}
