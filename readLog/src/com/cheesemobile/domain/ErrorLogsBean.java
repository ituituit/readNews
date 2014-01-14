package com.cheesemobile.domain;

import java.util.List;

public class ErrorLogsBean {
	private String LogFile;
	private String errorCount;
	private String testTime;
	private String testType;
	private List<EventBean> events;

	public String getLogFile() {
		return LogFile;
	}

	public void setLogFile(String logFile) {
		LogFile = logFile;
	}

	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public List<EventBean> getEvents() {
		return events;
	}

	public void setEvents(List<EventBean> events) {
		this.events = events;
	}
 

}
