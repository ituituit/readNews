package com.cheesemobile.domain;

import java.util.Date;
import java.util.List;

import com.cheesemobile.service.news.NewsController.NewsType;

public class NewsArticle extends BoundNewsObject {
	private int id;
	private int page;
	private int order;
	private NewsType type;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public NewsType getType() {
		return type;
	}

	public void setType(NewsType type) {
		this.type = type;
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
		return "NewsArticle [id=" + id + ", page=" + page + ", order=" + order
				+ ", type=" + type + ", sendDate=" + sendDate + ", content="
				+ content + ", author=" + author + ", department=" + department
				+ ", title=" + title + ", picsUrl=" + picsUrl + "]";
	}

}
