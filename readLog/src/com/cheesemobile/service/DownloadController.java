package com.cheesemobile.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cheesemobile.util.FileUtil;

public class DownloadController {
	private static DownloadController instance = null;
	private String temPath = "";

	private DownloadController() {
	}

	public static DownloadController getInstance() {
		if (instance == null) {
			instance = new DownloadController();
		}
		return instance;
	}

	public boolean download(String urlStr, String toFolder) {
		URL url;
		try {
			url = new URL(urlStr);
			// String content = AccessUtil.get(url);
			URLConnection conn = url.openConnection();
			InputStream l = conn.getInputStream();
			File outFile = new File(toFolder);
			if (!outFile.exists()){
//				outFile.mkdir();
				outFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = l.read(buffer,0,1024)) != -1){
				fos.write(buffer,0,len);
			}
			fos.close();
			l.close();
//			ObjectInputStream fis = (ObjectInputStream) conn.getInputStream();
//			String str = (String) fis.readObject();
//			FileUtil.write(toFolder, str);
			temPath = toFolder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
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

	public void write(String fullPath) {
		File destFile = new File(fullPath);
		if (destFile.exists()) {
			FileUtil.deleteFile(destFile);
		}
		File tmpFile = new File(temPath);
		tmpFile.renameTo(destFile);
	}
}
