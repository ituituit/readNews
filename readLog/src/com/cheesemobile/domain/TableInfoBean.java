package com.cheesemobile.domain;

import java.util.List;

public class TableInfoBean {
	private int totalCases;
	private int passCases;
	private int failedCases;
	private int passedWithWarns;
	private int passedWithDebug;
	private int passedWithAudit;
	private int errorNumber;
	private int wraningNumber;
	private int debugInfNumber;
	private int fatalNumber;
	private int auditNumber;
	private int abortedNumber;
	private List<TableDetailData> details;

	public TableInfoBean(int totalCases, int passCases, int failedCases,
			int passedWithWarns, int passedWithDebug, int passedWithAudit,
			int errorNumber, int wraningNumber, int fatalNumber,
			int debugInfNumber, int auditNumber, int abortedNumber,
			List<TableDetailData> details) {
		super();
		this.totalCases = totalCases;
		this.passCases = passCases;
		this.failedCases = failedCases;
		this.errorNumber = errorNumber;
		this.wraningNumber = wraningNumber;
		this.fatalNumber = fatalNumber;
		this.debugInfNumber = debugInfNumber;
		this.details = details;
		this.passedWithWarns = passedWithWarns;
		this.passedWithDebug = passedWithDebug;
		this.passedWithAudit = passedWithAudit;
		this.auditNumber = auditNumber;
		this.abortedNumber = abortedNumber;
	}

	public int getAbortedNumber() {
		return abortedNumber;
	}

	public void setAbortedNumber(int abortedNumber) {
		this.abortedNumber = abortedNumber;
	}

	public int getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(int auditNumber) {
		this.auditNumber = auditNumber;
	}

	public int getPassedWithAudit() {
		return passedWithAudit;
	}

	public void setPassedWithAudit(int passedWithAudit) {
		this.passedWithAudit = passedWithAudit;
	}

	public int getPassedWithWarns() {
		return passedWithWarns;
	}

	public void setPassedWithWarns(int passedWithWarns) {
		this.passedWithWarns = passedWithWarns;
	}

	public int getPassedWithDebug() {
		return passedWithDebug;
	}

	public void setPassedWithDebug(int passedWithDebug) {
		this.passedWithDebug = passedWithDebug;
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

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public int getWraningNumber() {
		return wraningNumber;
	}

	public void setWraningNumber(int wraningNumber) {
		this.wraningNumber = wraningNumber;
	}

	public int getDebugInfNumber() {
		return debugInfNumber;
	}

	public void setDebugInfNumber(int debugInfNumber) {
		this.debugInfNumber = debugInfNumber;
	}

	public int getFatalNumber() {
		return fatalNumber;
	}

	public void setFatalNumber(int fatalNumber) {
		this.fatalNumber = fatalNumber;
	}

	public List<TableDetailData> getDetails() {
		return details;
	}

	public void setDetails(List<TableDetailData> details) {
		this.details = details;
	}

}