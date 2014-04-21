package com.cheesemobile.domain;

import java.util.Date;
import java.util.List;

import com.cheesemobile.service.news.NewsController.NewsType;
import com.cheesemobile.util._Log;

public class NewsArticle {
	private int id;
	private int page = -1;
	private int order;
	private NewsType type;
	private Date sendDate;
	private String content;
	private String author;
	private String department;
	private String title;
	private List<String> picsUrl;
	private int bracketsType = 1;

	public NewsArticle(int id, Date sendDate, String content, String pick,
			String department, String title, List<String> picsUrl,
			NewsType type, int page, int bracketsType) {
		super();
		this.id = id;
		this.sendDate = sendDate;
		this.content = content;
		this.author = pick;
		if (pick.length() < 1 && department.indexOf(" ") != -1) {
			this.author = department.substring(department.indexOf(" ") + 1);
			this.department = department.substring(0, department.indexOf(" "));
		} else {
			this.department = department;
		}
		if (pick.length() > 1) {
			this.department = "";
		}
		this.title = title;
		this.picsUrl = picsUrl;
		this.type = type;
		this.page = page;
		this.bracketsType = bracketsType;
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

	public static StringBuilder jsxOutputFormat(String str){
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		while (sb.lastIndexOf("\r") == sb.length() - 1
				|| sb.lastIndexOf("\n") == sb.length() - 1
				|| sb.lastIndexOf(" ") == sb.length() - 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}
	public static String jsxOutputFormat1(String str){
		return str.replaceAll("\r","\\\\r");
	}
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append(content);
		_Log.i("" + sb.length());
		if (sb.length() <= 1) {
			return null;
		}
		sb = jsxOutputFormat(sb.toString());
		if (bracketsType == 1) {
			sb.append("(");
			if (departmentExists()) {
				sb.append(getDepartment());
			}
			sb.append("");
			if (!getAuthor().equals("")) {
				if (departmentExists()) {
					sb.append("¡ª¡ª");
				}
				sb.append(getAuthor());
			}
			sb.append(")");
		}
		if (sb.lastIndexOf("()") == sb.length() - "()".length()) {
			return sb.substring(0, sb.length() - "()".length());
		}
		return jsxOutputFormat1(sb.toString());
	}

	private boolean departmentExists() {
		if (getDepartment().length() > 1) {
			return true;
		}
		return false;
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
		return id + " " + page + " " + order + ", type=" + type + ", sendDate="
				+ sendDate + ", content="
				+ content.substring(0, content.length() / 10) + "..."
				+ content.length() + ", author=" + author + ", department="
				+ department + ", title=" + title + ", pics " + picsUrl + "\n";
	}

}
