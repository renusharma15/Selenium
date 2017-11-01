package com.myaccess.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	String path = getPath();
	Properties prop;

	public Properties loadApplicationFile(String file) throws Exception {
		try {
			prop = new Properties();
			File f = new File(path + file);

			if (f.exists()) {
				prop.load(new FileInputStream(f));
			}
			return prop;
		} catch (Exception e) {
			System.out.println("Failed to read from " + file + " file.");
			ExecutionLog.log("Error: Failed to read from " + file);
			throw e;
		}
	}

	public String getProperty(String key) throws Exception {
		try {
			return prop.getProperty(key);
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed to read " + key);
			throw ex;
		}
	}

	private String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

}