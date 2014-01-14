package com.cheesemobile.domain;

public class PropertiesBean {
	private String pid;
	private String hostname;
	private String utcoffset;
 	private String Component = "";
 	private String Area;
	public PropertiesBean(String pid, String hostname, String utcoffset) {
		super();
		this.pid = pid;
		this.hostname = hostname;
		this.utcoffset = utcoffset;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getUtcoffset() {
		return utcoffset;
	}
	public void setUtcoffset(String utcoffset) {
		this.utcoffset = utcoffset;
	}
	@Override
	public String toString() {
		return "PropertiesBean [pid=" + pid + ", hostname=" + hostname
				+ ", utcoffset=" + utcoffset + "]";
	}
	
	
}
