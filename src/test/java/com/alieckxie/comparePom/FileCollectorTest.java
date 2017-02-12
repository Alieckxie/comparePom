package com.alieckxie.comparePom;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FileCollectorTest {
	
	public void findFiles(String basePath, String targetFileName, List<File> fileList) {
		File baseFile = new File(basePath);
		File[] listFiles = baseFile.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				findFiles(file.getAbsolutePath(), targetFileName, fileList);
			} else if (file.isFile()) {
				if (file.getName().equals(targetFileName)
						&& !file.getPath().contains(File.separator + "target" + File.separator)) {
					fileList.add(file);
				}
			}
		}
	}

	@Test
	public void testFindPom(){
		List<File> fileList = new ArrayList<File>();
		findFiles("C:\\Users\\Alieckxie\\git", "pom.xml", fileList);
		for (File file : fileList) {
			System.out.println(file);
		}
	}
	
	@Test
	public void testFileAndFileFilter() {
		String path = "C:\\Users\\Alieckxie\\git";
		File file = new File(path);
		System.out.println(file.isDirectory());
		System.out.println(file.isFile());
		String[] list2 = file.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				System.out.println("File dir-->"+dir.getName());
				if (name.equals("blog")) {
					return true;
				}
				return false;
			}
		});
		for (String string : list2) {
			System.out.println("---过滤---1");
			System.out.println(string);
			System.out.println("---过滤---2");
		}
		String[] list = file.list();
		for (String string : list) {
			System.out.println(string);
		}
	}

	@Test
	public void testPathDemo() {
		System.out.println(System.getProperty("user.dir"));

		try {
			System.out.println("-----默认相对路径：取得路径不同------");
			File file1 = new File("..\\src\\test1.txt");
			System.out.println(file1.getPath());
			System.out.println(file1.getAbsolutePath());
			System.out.println(file1.getCanonicalPath());
			System.out.println("-----默认相对路径：取得路径不同------");
			File file = new File(".\\test1.txt");
			System.out.println(file.getPath());
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getCanonicalPath());

			System.out.println("-----默认绝对路径:取得路径相同------");
			File file2 = new File("D:\\workspace\\test\\test1.txt");
			System.out.println(file2.getPath());
			System.out.println(file2.getAbsolutePath());
			System.out.println(file2.getCanonicalPath());
		} catch (IOException e) {
			// TODOAuto-generated catch block
			e.printStackTrace();
		}

	}
}
