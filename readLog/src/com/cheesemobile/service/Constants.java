package com.cheesemobile.service;

public class Constants {
	public final static int NEWS_RULER_1 = 11;
	public final static int NEWS_RULER_2 = 11;
	public final static float CONTENT_HEIGHT_FIX_VALUE = 100f;
	public final static float CONTENT_HEIGHT = 999f;
	public final static float CONTENT_WIDTH_FIX_VALUE = 100f;
	public final static float CONTENT_WIDTH = 459.36f;

	public final static int ARTICLE_HEIGHT_DEFAULT = 300;
	public static String PREFERENCE_TEMP_PATH = "/Users/pwl/Documents/Adobe Scripts/prefs.txt";
	public static String XSL_SOURCE_PATH = "/Users/pwl/git/readNews_remote/readLog/lib.jsx";
	public static String XSL_DESTINATION_PATH = "/Users/pwl/Documents/Adobe Scripts/tmp.jsx";
	public static String IMG_DESTINATION_PATH = "/Volumes/peiwen/";
	private static String AUTOMATION_FOLDER = "/Users/pwl/Downloads/automation/";
	public static String PAGE_2_3_PATH = AUTOMATION_FOLDER + "news2.psd";
	public static String PAGE_1_4_PATH = AUTOMATION_FOLDER + "news1.psd";
	public static String PSD_LIBRARY_PATH = AUTOMATION_FOLDER + "lib.psd";
	public static String VISITORS_TRACK_LIBRARY_PATH = AUTOMATION_FOLDER + "visitors_track.txt";
	public static String SCENIC_BLOGS_LIBRARY_PATH = AUTOMATION_FOLDER + "scenic_blogs.txt";
	public static String SCENIC_NEWS_LIBRARY_PATH = AUTOMATION_FOLDER + "scenic_news.txt";
	public static String TRAVEL_LAWS_LIBRARY_PATH = AUTOMATION_FOLDER + "travel_laws.txt";
	public static String NEWS_LIBRARY_PATH = AUTOMATION_FOLDER + "sample.txt";
	public static String VISITORS_TRACK_PSD_GROUP = "文化研究";
	public static String SCENIC_BLOGS_PSD_GROUP = "博客珏山";
	public static String SCENIC_NEWS_PSD_GROUP = "景区快讯";
	public static String TRAVEL_LAWS_PSD_GROUP = "旅游法的学习";
	public static String TRAVEL_LINKS_PSD_GROUP = "旅游链接";
	public static String I_SPEAK_PSD_GROUP = "我来说两句";
	static{
		if (SystemTool.getOSName().indexOf("windows") != -1) {
			XSL_SOURCE_PATH = "C:\\Documents and Settings\\Administrator\\git\\readNews_remote\\readLog\\lib.jsx";
			XSL_DESTINATION_PATH = "D:\\我的文档\\Adobe Scripts\\tmp.jsx";
			PREFERENCE_TEMP_PATH = "D:/我的文档/Adobe Scripts/prefs.txt";
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