package com.cheesemobile.domain;

import java.util.List;

public class TableDetailData {
	public int testCase;
	public String errorCode;
	public String errorName;
	public int errorNumber;
	public List<EventBean> errorMessages;
	
	
	public TableDetailData(int testCase, int errorNumber, List<EventBean> errorMessages,String errorCode,String errorName) {
		super();
		this.testCase = testCase;
		this.errorNumber = errorNumber;
		this.errorMessages = errorMessages;
		this.errorCode = errorCode;
		this.errorName = errorName;
	}
	
	public int getTestCase() {
		return testCase;
	}
	public void setTestCase(int testCase) {
		this.testCase = testCase;
	}
	public int getErrorNumber() {
		return errorNumber;
	}
	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public List<EventBean> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<EventBean> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	@Override
	public String toString() {
		return "TableDetailData [testCase=" + testCase + ", errorCode="
				+ errorCode + ", errorName=" + errorName + ", errorNumber="
				+ errorNumber + ", errorMessage=" + errorMessages + "]";
	}

}