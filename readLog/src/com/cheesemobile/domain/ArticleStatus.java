package com.cheesemobile.domain;

public class ArticleStatus {
	private String departmentName;
	private int articleCount;
	private int acceptCount;
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public int getAcceptCount() {
		return acceptCount;
	}

	public void setAcceptCount(int acceptCount) {
		this.acceptCount = acceptCount;
	}

	@Override
	public String toString() {
		return departmentName + " 投稿" + articleCount  +"篇," + "采用" +acceptCount+ "篇;";
	}
}

