package com.cheesemobile.domain;

import java.util.List;

public class VoBean {

	private String function;
	private String names;
	private String values;
	private enum Status{ERROR,FINISHED};
	private Status status = Status.FINISHED;
	public VoBean(String PreferenceStr) {
		PreferenceStr = PreferenceStr
				.substring(PreferenceStr.indexOf("\r") + 2);
		function = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1,
				PreferenceStr.indexOf("\r"));
		PreferenceStr = PreferenceStr
				.substring(PreferenceStr.indexOf("\r") + 1);
		names = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1,
				PreferenceStr.indexOf("\r"));
		PreferenceStr = PreferenceStr
				.substring(PreferenceStr.indexOf("\r") + 1);
		values = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1,
				PreferenceStr.indexOf("\r"));
		if(values.indexOf("error") != -1){
			status = Status.ERROR;
		}
	}

	public VoBean(String function, String names, String values) {
		super();
		this.function = function;
		this.names = names;
		this.values = values;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getNames() {
		if(status == Status.ERROR){
			return null;
		}
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getValues() {
		if(status == Status.ERROR){
			return null;
		}
		return values;
	}

	public String[] getValuesList() {
		if(status == Status.ERROR){
			return null;
		}
		String[] strs = values.split(",");
		// for (int i = 0; i < strs.length; i++) {
		// String str = strs[i];
		// str = str.replace("pt", "");
		// strs[i] = str;
		// }
		return strs;
	}

	public void setValues(String values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "VoBean [function=" + function + ", names=" + names
				+ ", values=" + values + "]";
	}
	public VoBean(){
		this.function = "null";
		this.names = "[null]";
		this.values = "[null]";
	}
}
