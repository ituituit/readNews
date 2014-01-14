package com.cheesemobile.domain;

import java.sql.Date;
import java.util.List;

public class NewsBean {
	private String id;
	private Date date; 
	private List<NewsArticle> articles;	
	private String releaseNumber;
	
	public NewsBean(String id, Date date, List<NewsArticle> articles,
			String releaseNumber) {
		super();
		this.id = id;
		this.date = date;
		this.articles = articles;
		this.releaseNumber = releaseNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<NewsArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	@Override
	public String toString() {
		return "NewsBean [id=" + id + ", date=" + date + ", articles="
				+ articles + ", releaseNumber=" + releaseNumber + "]";
	}
	
}
