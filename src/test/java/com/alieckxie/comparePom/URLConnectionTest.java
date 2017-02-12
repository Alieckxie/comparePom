package com.alieckxie.comparePom;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class URLConnectionTest {

	@Test
	public void testSendGet(){
		try {
			URL url = new URL("http://200.31.154.183:8081/nexus/service/local/lucene/search?_dc=1486639245856&g=com.cfets.ts&a=ts-u-clearing&collapseresults=true");
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");
			connection.setRequestProperty("Accept", "application/json,application/vnd.siesta-error-v1+json,application/vnd.siesta-validation-errors-v1+json");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("X-Nexus-UI", "true");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			connection.setRequestProperty("Referer", "http://200.31.154.183:8081/nexus/");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.connect();
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			byte[] buf = new byte[4096];
			int len;StringBuffer jsonb = new StringBuffer();
			while((len = bis.read(buf)) != -1){
				jsonb.append(new String(buf, 0, len));
			}
			String json = jsonb.toString();
			
			String totalCount = getJsonValueByAttr(json, "totalCount");

			String latestRelease = getJsonValueByAttr(json, "latestRelease");
			
			
			System.out.println(totalCount);
			System.out.println("------------------");
			System.out.println(latestRelease);
			System.out.println("------------------");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getJsonValueByAttr(String json, String attr) {
		String attrWarp = new StringBuilder(attr).insert(0, "\"").append("\":").toString();
		int attrPosition = json.indexOf(attrWarp);
		int commaPosition = json.indexOf(",", attrPosition);
		int valuePosition = attrPosition + attrWarp.length();
		char[] charValue = new char[(commaPosition - valuePosition)];
		json.getChars(valuePosition, commaPosition, charValue, 0);
		String value = new String(charValue);
		if (value.startsWith("\"") && value.endsWith("\"")) {
			value = value.replace("\"", "");
		}
		return value;
	}
}
