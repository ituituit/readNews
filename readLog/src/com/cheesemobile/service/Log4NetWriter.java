package com.cheesemobile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cheesemobile.domain.EventBean;
import com.cheesemobile.domain.TableDetailData;
import com.cheesemobile.domain.TableInfoBean;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Log4NetWriter {
	private String outputPath = null;

	public Log4NetWriter(String outputPath) {
		super();
		this.outputPath = outputPath;
	}

	public void writeCaseXml(TableInfoBean tib) {
		// List<String> paths = FileUtil.listFolderFiles(logPath, ".1");
		// List<EventBean> errorEvents = new ArrayList<EventBean>();
		// StringBuilder errorPaths = new StringBuilder();
		// for (int i = 0; i < paths.size(); i++) {
		// String path = paths.get(i);
		// System.out.println("readFile" + path);
		// Log4NetReader l = new Log4NetReader().readFile(path);
		// errorEvents = l
		// .readEvent(StringUtil.getHostName(), startDate,
		// endDate,Log4NetReader.LEVEL_ERROR,Log4NetReader.LEVEL_INFO);
		// if(errorEvents.size() != 0){
		// errorPaths.append(paths.get(i));
		// }
		// }

		// TableInfoBean tib = new TableInfoBean(testCase, testCase, testCase,
		// logPath, logPath, null);
		// TableDetailData tdd = new TableDetailData(testCase, testCase,
		// testCase);
		// ErrorLogsBean b = new ErrorLogsBean();
		// b.setLogFile(errorPaths.toString());
		// b.setErrorCount(errorEvents.size() + "");
		//
		// Date date = new Date();
		// date.setTime(System.currentTimeMillis());
		// b.setTestTime(date.toLocaleString());
		// b.setTestType(testCase + "");
		// b.setEvents(errorEvents);
		XStream xstream = new XStream(new DomDriver());
		String xml = xstream.toXML(tib);
		FileUtil.appendToFile(outputPath + "\\errors.xml", xml);
		
		FileUtil.appendMethodA(outputPath + "\\errors.xml","<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><?xml-stylesheet type=\"text/xsl\" href=\"xsl.xsl\"?>","");
		
	}
}
