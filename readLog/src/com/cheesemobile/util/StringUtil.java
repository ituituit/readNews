package com.cheesemobile.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringUtil {
	public static final int FROM_AUTO_IT = 0;
	public static final int FROM_EVENT_BEAN = 1;
	public static final int FROM_LIVE = 2;
	
	public static List<String> arrayToList(String[] strs){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < strs.length; i++){
			list.add(strs[i]);
		}
		return list;
	}
	
	public static float floatInString(String str){
		List<Integer> inds = new ArrayList<>();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (org.jsoup.helper.StringUtil.isNumeric(s) || s.equals(".") || s.equals("-")) {
				inds.add(i);
			} else {
				continue;
			}
		}
		if (inds.size() == 1) {
			return Integer
					.parseInt(str.substring(inds.get(0), inds.get(0) + 1));
		} else if (inds.size() == 0) {
			return 0;
		} else {
			int end = inds.get(0);
			for (int i = 0; i < inds.size() - 1; i++) {
				int current = inds.get(i);
				int next = inds.get(i + 1);
				boolean close = current == next - 1 ? true : false;
				end = next;
				if (!close) {
					break;
				}

			}
			String str1 = str.substring(inds.get(0), end + 1);
			return Float.parseFloat(str1);
		}
	}
	
	public static int intInString(String str) {
		List<Integer> inds = new ArrayList<>();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (org.jsoup.helper.StringUtil.isNumeric(s)) {
				inds.add(i);
			} else {
				continue;
			}
		}
		if (inds.size() == 1) {
			return Integer
					.parseInt(str.substring(inds.get(0), inds.get(0) + 1));
		} else if (inds.size() == 0) {
			return 0;
		} else {
			int end = inds.get(0);
			for (int i = 0; i < inds.size() - 1; i++) {
				int current = inds.get(i);
				int next = inds.get(i + 1);
				boolean close = current == next - 1 ? true : false;
				end = next;
				if (!close) {
					break;
				}

			}
			return Integer.parseInt(str.substring(inds.get(0), end));
		}
	}

	
	public static Date StringToDate(String str, int type) {
		Date date = new Date();
		SimpleDateFormat sdf = null;
		String[] strs;
		switch (type) {
		case FROM_AUTO_IT:
			// 15:45:10,1/22/2013
			strs = str.split(",");
			sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			try {
				date = sdf.parse(strs[1] + " " + strs[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case FROM_EVENT_BEAN:
			// 2013-01-20T17:49:42.9687885-08:00,+8:00 
			strs = new String[2];
			strs[0] = str.substring(0, str.indexOf("T"));
			strs[1] = str.substring(str.indexOf("T") + 1,str.indexOf("T") + 9);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			TimeZone timeZoneNY = TimeZone.getTimeZone("GMT"
//					+ str.substring(str.indexOf(".") + 8));
//			TimeZone timeZoneNY = TimeZone.getTimeZone("GMT"
//					+ str.substring(str.indexOf(",")));
//			sdf.setTimeZone(timeZoneNY);
			try {
				date = sdf.parse(strs[0] + " " + strs[1]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case FROM_LIVE:
			// 3/13/2013 1:25:25 AM,+8:00 
			sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
			strs = str.split(",");
			try {
				date = sdf.parse(strs[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		}
		return date;
	}
	public static String getHostName(){
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		return ia.getHostName();//获取计算机主机名
	}
	
	public static boolean strInStrs(String[] comparesStr,String comparedStr){
		for (int j = 0; j < comparesStr.length; j++) {
			if (comparesStr[j].equals(comparedStr)) {
				return true;
			}
		}
		return false;
	}

}
