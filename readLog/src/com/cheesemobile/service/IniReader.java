package com.cheesemobile.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.cheesemobile.domain.IniLine;
import com.cheesemobile.util.FileUtil;

public class IniReader {
	private String str;
	private String iniPath;

	public IniReader(String iniPath) {
		this.iniPath = iniPath;
		str = FileUtil.readToString(iniPath);
	}

	public boolean setKey(String key, String section, String value) {
		IniLine line = getStr(key, section);
		if (line != null) {
			String str = line.getKey() + " = \"" + value + "\"";
			return setKey(line.getLineNum(), str);
		}
		return false;
	}

	private boolean setKey(int lineNum, String replaced) {
		FileReader fr = null;
		LineNumberReader lnr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new FileReader(iniPath);
			lnr = new LineNumberReader(fr);
			String line = null;
			int currentLine = 0;
			while ((line = lnr.readLine()) != null) {
				currentLine++;
				if (currentLine == lineNum) {
					line = replaced;
				}
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			if (fr != null) {
				try {
					lnr.close();
					fr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		}
		FileUtil.write(iniPath, sb.toString());
		return true;
	}

	private IniLine getStr(String key, String section) {
		String content = null;
		int fromIndex = 0;
		int lineNum = 0;
		String currentScetion = "";
		IniLine returnLine = null;
		while (fromIndex != -1) {
			int index = 0;
			if (lineNum != 0) {
				index = str.indexOf("\n", fromIndex);
			}
			if (index < fromIndex) {
				break;
			}
			fromIndex = index;
			fromIndex++;
			int lineIndex = str.indexOf("\n", fromIndex);
			if (lineIndex < fromIndex) {
				break;
			}
			lineNum++;
			String lineStr = str.substring(fromIndex, lineIndex);
			String[] kv = lineStr.split("=");

			for (int i = 0; i < kv.length; i++) {
				String str = kv[i];
				str = str.replaceAll("\\s*", "");
				str = str.replace("\"", "");
				kv[i] = str;
			}
			if (kv.length <= 1) {
				if (kv[0].startsWith("[") && kv[0].endsWith("]")) {
					String str = kv[0];
					str = str.replace("]", "");
					str = str.replace("[", "");
					currentScetion = str;
				}
				continue;
			}

			String keys[] = kv[1].split(",");

			IniLine line = new IniLine(lineNum, currentScetion, kv[0], keys);
			if (line.getSection().equals(section) && line.getKey().equals(key)) {
				returnLine = line;
			}
		}
		return returnLine;
	}
}
