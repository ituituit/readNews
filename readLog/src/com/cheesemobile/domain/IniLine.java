package com.cheesemobile.domain;

import java.util.Arrays;

public class IniLine {
	private int lineNum;
	private String key;
	private String section;
	private String[] value;
	
	public IniLine(int lineNum,String section,String key, String[] value) {
		super();
		this.lineNum = lineNum;
		this.key = key;
		this.value = value;
		this.section = section;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String[] getValue() {
		return value;
	}
	public void setValue(String[] value) {
		this.value = value;
	}
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	@Override
	public String toString() {
		return "IniLine [lineNum=" + lineNum + ", key=" + key + ", section="
				+ section + ", value=" + Arrays.toString(value) + "]";
	}
	
	
}
