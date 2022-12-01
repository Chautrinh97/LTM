package com.ctv.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties get(String filePath){
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + filePath;

		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));
			return appProps;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
