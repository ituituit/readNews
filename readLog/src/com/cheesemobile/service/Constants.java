package com.cheesemobile.service;

public class Constants {
	public final static int NEWS_RULER_1 = 11;
	public final static int NEWS_RULER_2 = 11;
	public final static float CONTENT_HEIGHT_FIX_VALUE = 100f;
	public final static float CONTENT_HEIGHT = 999f;
	public final static float CONTENT_WIDTH_FIX_VALUE = 100f;
	public final static float CONTENT_WIDTH = 459.36f;

	public final static int ARTICLE_HEIGHT_DEFAULT = 300;
	public static String XSL_SOURCE_PATH = "/Users/pwl/Desktop/automation/sample.jsx";
	public static String XSL_DESTINATION_PATH = "/Users/pwl/Documents/Adobe Scripts/sourcex.jsx";
	public final static String IMG_DESTINATION_PATH = "/Volumes/peiwen/";
	public final static String TRAVEL_LAWS_LIBRARY_PATH = "C:/Documents and Settings/Administrator/桌面/旅游法学习.txt";
	public static String NEWS_LIBRARY_PATH = "/Users/pwl/Downloads/automation/sample.txt";
	static{
		if (SystemTool.getOSName().indexOf("windows") != -1) {
			XSL_SOURCE_PATH = "C:\\Documents and Settings\\Administrator\\git\\readNews_remote\\readLog\\sample.jsx";
			XSL_DESTINATION_PATH = "D:\\我的文档\\Adobe Scripts\\tmp.jsx";
			NEWS_LIBRARY_PATH = "F:/BaiduYunDownload/automation/sample.txt";
		}
	}

	public static boolean _SET_CONTENT_NOTE_TO_TITLE = true;

	public final static int LEVEL_FATAL = 0;
	public final static int LEVEL_ERROR = 1;
	public final static int LEVEL_WARN = 2;
	public final static int LEVEL_INFO = 3;
	public final static int LEVEL_DEBUG = 4;
	public final static int LEVEL_AUDIT = 5;
	public final static int LEVEL_STATISTICS = 6;
	public final static String[] LEVELS_DESC = { "FATAL", "ERROR", "WARN",
			"INFO", "DEBUG", "AUDIT", "STATISTICS" };
	public final static String[] LEVELS_USEING = { "FATAL", "ERROR", "WARN",
			"DEBUG" };

	static String[] filterMethods = { "filterHostName", "filterTime",
			"matchLogErrors", "filterLevels" };

	public final static String[] noDataFileNames = { "\\No.bmp",
			"\\Server.bmp", "\\Offline.bmp" };

	public final static String debugControllsPath = "C:\\Users\\peiwen\\Desktop\\controlls";
	public final static String defControllsPath = "\\controlls";
	public final static String patternWord = "(.){1,100}";
	public final static String patternEvent = "(.){1,100}";
	public final static String patternIp = "(.){1,100}";
	public final static String patternBracketIp = "(.){0,100}";
	public final static String patternMarksIp = "(.){0,100}";
	public final static String patternNumber = "(.){1,100}";
	public final static String addS = "(.){0,1}";
	public final static String ptnDate = "(.){1,100}";
	public final static String ptnTime = "(.){1,100}";
	public final static String fromToDate = Constants.ptnDate + " "
			+ Constants.ptnTime + " - " + Constants.ptnDate + " "
			+ Constants.ptnTime;
	public final static String spreadSignOfAbortCase = "&&";

	public static String patternWords() {
		// StringBuilder str= new StringBuilder();
		// for(int i = 0; i < 20; i++){
		// str.append(patternWord);
		// str.append(",");
		// }
		// return str.substring(0,str.length()-1);
		return "(.){0,2000}";
	}
}