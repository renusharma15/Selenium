package com.myaccess.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class LocatorReader {
	private Document doc;
	private String xmlName;

	public LocatorReader(String xmlname) throws Exception {
		xmlName = xmlname;
		SAXReader reader = new SAXReader();
		String path = getPath();
		try {
			doc = reader.read(path + xmlName);
		} catch (DocumentException e) {
			ExecutionLog.log("Error:" + path + xmlName + "invalid. ");
			ExecutionLog.logExceptionMessage(e);
			throw e;
		}
	}

	public LocatorReader(String xmlname, String rootpath)  throws Exception{
		xmlName = xmlname;
		SAXReader reader = new SAXReader();
		String path = getPath() + rootpath;
		try {
			doc = reader.read(path + xmlName);
		} catch (DocumentException e) {
			ExecutionLog.log("Error:" + path + xmlName + "invalid. ");
			ExecutionLog.logExceptionMessage(e);
			throw e;
		}
	}

	public static String getPath() {
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		return absolutePathOfFirstFile;
	}

	public String getLocator(String locator) throws Exception {
		try {
			return doc.selectSingleNode("//" + locator.replace('.', '/'))
					.getText();
		} catch (NullPointerException e) {
			ExecutionLog.log("Error:" + locator + " not found in " + xmlName);
			throw e;
		}
	}

	public List<Node> getNode(String query) throws Exception {
		try {
			String Xpath = "//" + query.replace('.', '/');
			return doc.selectNodes(Xpath);
		} catch (Exception e) {
			ExecutionLog.log("Error: " + query + " not found.");
			throw e;
		}
	}

}
