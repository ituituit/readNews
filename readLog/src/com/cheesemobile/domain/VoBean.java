package com.cheesemobile.domain;

import java.util.List;

public class VoBean {

	private String function;
	private String names;
	private String values;

	public VoBean(String PreferenceStr) {
		function = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1,
				PreferenceStr.indexOf("\n"));
		PreferenceStr = PreferenceStr
				.substring(PreferenceStr.indexOf("\n") + 1);
		names = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1,
				PreferenceStr.indexOf("\n"));
		PreferenceStr = PreferenceStr
				.substring(PreferenceStr.indexOf("\n") + 1);
		values = PreferenceStr.substring(PreferenceStr.indexOf(":") + 1);
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
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getValues() {
		return values;
	}

	public String[] getValuesList() {
		String[] strs = values.split(",");
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			str = str.replace("pt", "");
			strs[i] = str;
		}
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

}
