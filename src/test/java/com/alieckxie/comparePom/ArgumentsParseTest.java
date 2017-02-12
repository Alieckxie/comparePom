package com.alieckxie.comparePom;

import org.junit.Test;

import com.alieckxie.comparePom.bean.ArgumentBean;

public class ArgumentsParseTest {

	@Test
	public void testArgumentsParse1() {
		String[] args = new String[] { "-c" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse2() {
		String[] args = new String[] { "-p" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse3() {
		String[] args = new String[] { "-s" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse4() {
		String[] args = new String[] { "-m" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse5() {
		String[] args = new String[] { "-u" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse6() {
		String[] args = new String[] { "-c", "-p" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse7() {
		String[] args = new String[] { "-m", "-s", "-p" };
		try {
			Main.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testArgumentsParse8() {
		String[] args = new String[] { "-p", "fdagfa", "-s", "asd", "-u" };
		long start = System.nanoTime();
		try {
			ArgumentBean parseArguments = Main.parseArguments(args);
			System.out.println(System.nanoTime() - start);
			System.out.println(parseArguments);
		} catch (Exception e) {
			System.out.println(System.nanoTime() - start);
			System.out.println(e.getMessage());
		}
	}

}
