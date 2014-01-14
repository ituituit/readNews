package com.cheesemobile.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DailyPasswordController {
	private static DailyPasswordController instance = null;

	private DailyPasswordController() {
	}

	public static DailyPasswordController getInstance() {
		if (instance == null) {
			instance = new DailyPasswordController();
		}
		return instance;
	}

	public String read(String path) {
		String str = "";
		Document doc = null;
		try {
			doc = Jsoup.connect(path).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(doc.toString());
		Element l = doc.getElementById("Image1");
		str = l.attr("alt");
		return str;
	}

	public void write(String iniPath) {
		IniReader reader = new IniReader(iniPath);
				//"C:\\Users\\peiwen\\Desktop\\readLog\\0221_OpCenter_Completed\\myini.ini");
		boolean success = reader.setKey("daily_password", "parameters",
				read("http://dailypassword/"));
		if (success) {
			System.out.println("success set daily pass");
		}
	}
}
