package com.cheesemobile.domain;
import java.util.Properties;

public class EventBean {
	private int id;
	private String srcCode;
	private String logger;
	private String Timestamp = "";
	private String level;
	private String thread;	
	private String domain = "";
	private String username;
	private String message = "";
	PropertiesBean properties;
	
	public EventBean(String hostname, String timestamp, String domain,
			String area, String message, String pID, String thread,
			String component,String level,String username,String utcOffset) {
		super();
		properties = new PropertiesBean(pID,hostname,utcOffset);
		this.Timestamp = timestamp;
		this.domain = domain;
		this.message = message;
		this.thread = thread; 
		this.level = level;
		this.username = username;
	}

	
	@Override
	public String toString() {
		return "EventBean [logger=" + logger + ", Timestamp=" + Timestamp
				+ ", level=" + level + ", thread=" + thread + ", domain="
				+ domain + ", username=" + username + ", message=" + message
				+ ", properties=" + properties + "]";
	}


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PropertiesBean getProperties() {
		return properties;
	}

	public void setProperties(PropertiesBean properties) {
		this.properties = properties;
	}
	
}
