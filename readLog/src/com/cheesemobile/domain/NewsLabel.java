package com.cheesemobile.domain;

//2013��12��1��    ����ʮ����    �ܵ�279��  
//����༭��������  E-mail��1513053183@qq.com
public class NewsLabel {
	private String date;
	private String versionNumber;
	private String versionSum;
	private String editor = "����༭��������";
	private String email = "E-mail��1513053183@qq.com";
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
		
		this.versionNumber = "��һ��";
		int sum = versionNum + 278;
		this.versionSum = "�ܵ�" + sum + "��";
	}
	public String toString(){
		return date + "    " + versionNumber + "    " + versionSum + "\\r" + editor + "  " + email;
	}
}
