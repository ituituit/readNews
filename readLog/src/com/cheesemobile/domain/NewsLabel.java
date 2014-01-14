package com.cheesemobile.domain;

//2013年12月1日    第三十九期    总第279期  
//本版编辑：栗培文  E-mail：1513053183@qq.com
public class NewsLabel {
	private String date;
	private String versionNumber;
	private String versionSum;
	private String editor = "本版编辑：栗培文";
	private String email = "E-mail：1513053183@qq.com";
	public NewsLabel(int versionNumber,String dateString) {
		super();
		// TODO Auto-generated constructor stub
		setDate(dateString);
		setVersionNumber(versionNumber);
	} 
	public void setDate(String date) {
		this.date = date;
	} 
	public void setVersionNumber(int versionNum) {
		
		this.versionNumber = "第一期";
		int sum = versionNum + 278;
		this.versionSum = "总第" + sum + "期";
	}
	public String toString(){
		return date + "    " + versionNumber + "    " + versionSum + "\\r" + editor + "  " + email;
	}
}
