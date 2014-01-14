package com.cheesemobile.domain;

import java.util.List;

public class TableBean {
	private GeneralResults generalResults;
	private FailureTypes failureTypes;
	private Details details;
	
	public TableBean(GeneralResults generalResults, FailureTypes failureTypes,
			Details details) {
		super();
		this.generalResults = generalResults;
		this.failureTypes = failureTypes;
		this.details = details;
	}
	public GeneralResults getGeneralResults() {
		return generalResults;
	}
	public void setGeneralResults(GeneralResults generalResults) {
		this.generalResults = generalResults;
	}
	public FailureTypes getFailureTypes() {
		return failureTypes;
	}
	public void setFailureTypes(FailureTypes failureTypes) {
		this.failureTypes = failureTypes;
	}
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
}
//private String status;
class GeneralResults {
	private int totalCases;
	private int passCases;
	private int failedCases;
	
	
	public GeneralResults(int totalCases, int passCases, int failedCases) {
		super();
		this.totalCases = totalCases;
		this.passCases = passCases;
		this.failedCases = failedCases;
	}
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	public int getPassCases() {
		return passCases;
	}
	public void setPassCases(int passCases) {
		this.passCases = passCases;
	}
	public int getFailedCases() {
		return failedCases;
	}
	public void setFailedCases(int failedCases) {
		this.failedCases = failedCases;
	}
	

}

class FailureTypes {
	private String errorNumber;
	private String wraningNumber;
	
	
	public FailureTypes(String errorNumber, String wraningNumber) {
		super();
		this.errorNumber = errorNumber;
		this.wraningNumber = wraningNumber;
	}
	public String getErrorNumber() {
		return errorNumber;
	}
	public void setErrorNumber(String errorNumber) {
		this.errorNumber = errorNumber;
	}
	public String getWraningNumber() {
		return wraningNumber;
	}
	public void setWraningNumber(String wraningNumber) {
		this.wraningNumber = wraningNumber;
	}
	
}

class Details {
	private List<Detail> details;

	
	public Details(List<Detail> details) {
		super();
		this.details = details;
	}

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	
}

class Detail{
	private int testCase;
	private int errorNumber;
	private int errorMessage;
	
	public Detail(int testCase, int errorNumber, int errorMessage) {
		super();
		this.testCase = testCase;
		this.errorNumber = errorNumber;
		this.errorMessage = errorMessage;
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
	public int getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(int errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}