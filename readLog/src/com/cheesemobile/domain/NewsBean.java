package com.cheesemobile.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NewsBean {
	private String id;
	private Date date;
	private List<NewsArticle> articles;
	private int releaseNumber;
	public NewsBean(String id, Date date, List<NewsArticle> articles,
			int releaseNumber) {
		super();
		this.id = id;
		this.date = date;
		this.articles = articles;
		this.releaseNumber = releaseNumber;
	}
	public void pushArticles(List<NewsArticle> list,int page){
		int reorderStart = 0;
		for(NewsArticle a : articles){
			if(a.getPage() == page){
				reorderStart++;
			}
		}
		for(NewsArticle a : list){
			a.setPage(page);
			a.setOrder(reorderStart++);
		}
		articles.addAll(list);
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
	public List<NewsArticle> getArticles(int page) {
		List<NewsArticle> list = new ArrayList<NewsArticle>();
		for(NewsArticle a: articles ){
			if(a.getPage() == page){
				list.add(a);
			}
		}
		return list;
	}
	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}
	public int getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	@Override
	public String toString() {
		return "NewsBean [id=" + id + ", date=" + date + ", articles="
				+ articles + ", releaseNumber=" + releaseNumber + "]";
	}
	
}
