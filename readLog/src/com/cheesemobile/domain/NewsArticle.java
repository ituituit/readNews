package com.cheesemobile.domain;

import java.util.Date;
import java.util.List;

public class NewsArticle extends BaseNewsObject {
	private int id;
	private Date sendDate;
	private String content;
	private String author;
	private String department;
	private String title;
	private List<String> picsUrl; 
	public NewsArticle(int id, Date sendDate, String content, String author,
			String department, String title, List<String> picsUrl) {
		super();
		this.id = id;
		this.sendDate = sendDate;
		this.content = content;
		this.author = author;
		this.department = department;
		this.title = title;
		this.picsUrl = picsUrl;
	}
	public List<String> getPicsUrl() {
		return picsUrl;
	}
	public void setPicsUrl(List<String> picsUrl) {
		this.picsUrl = picsUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "NewsArticle [id=" + id + ", sendDate=" + sendDate
				+ ", content=" + content + ", author=" + author
				+ ", department=" + department + ", title=" + title
				+ ", picsData=" + picsUrl + "]";
	}
	
}
