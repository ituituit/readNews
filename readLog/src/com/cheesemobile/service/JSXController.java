package com.cheesemobile.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.cheesemobile.domain.VoBean;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util._Log;

public class JSXController {

	private static int timeOut = 5000;
	private static int timePassed = 0;
	private static int timedelay = 100;
	private static String _lastPreferenceStr;

	private static void alert(StringBuilder sb) {
		sb.append("alert(\"123321\")");
		sb.append("\r");
	}

	private static String JSXArray(List<String> list) {
		StringBuilder stringOfArray = new StringBuilder();
		stringOfArray.append("[");
		for (String str : list) {
			stringOfArray.append(str + ",");
		}
		stringOfArray.deleteCharAt(stringOfArray.length() - 1);
		stringOfArray.append("]");
		return stringOfArray.toString();
	}

	public static VoBean invoke(String funName, List<String> params) {
		VoBean returnVal = null;
		execute(funName,JSXArray(params));
		if (_lastPreferenceStr.indexOf(funName) != -1) {
			_Log.i(_lastPreferenceStr);
			returnVal = new VoBean(_lastPreferenceStr);
		}
		return returnVal;
	}

	private static void getPreferences() throws InterruptedException {
		final CountDownLatch runningThreadNum = new CountDownLatch(1);
		Thread thread = new Thread() {
			public void run() {
				timePassed = 0;
				try {
					while (!preferenceFileExists()) {
						Thread.sleep(timedelay);
						timePassed += timedelay;
						_Log.i("wait for preference");
						if (timePassed > timeOut) {
							break;
						}
					}
					if (preferenceFileExists()) {
						String str = FileUtil
								.readToString("D:/ÎÒµÄÎÄµµ/Adobe Scripts/prefs.txt");
						_lastPreferenceStr = str;
						delPreferenceFile();
					}
					runningThreadNum.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.run();
		runningThreadNum.await();
	}

	private static void delPreferenceFile() {
		File file = new File(Constants.PREFERENCE_TEMP_PATH);
		if (file.exists()) {
			file.delete();
		}
	}

	private static boolean preferenceFileExists() {
		File file = new File(Constants.PREFERENCE_TEMP_PATH);
		return file.exists();
	}

	private static void execute(String functionName,String params) {
		try {
			delPreferenceFile();
			String destDir = Constants.XSL_DESTINATION_PATH;
			String srcDir = Constants.XSL_SOURCE_PATH;
			String str = FileUtil.readToString(srcDir);
			str = str.substring(str.indexOf("\r"));
			StringBuilder sb = new StringBuilder();
			sb.append("#target photoshop");
			sb.append("\r");
			sb.append(str);
			sb.append("\r");
			sb.append("_returntojavavalues = " + functionName +"(" + params + ")");
			sb.append("\r");
			
			String write = "var prefs = new File(\""+Constants.PREFERENCE_TEMP_PATH+"\");\r prefs.open(\"w\"); \r prefs.writeln(\"function:"+functionName+"\\n names:undefinied\\n values:\"+_returntojavavalues)";
			// alert(sb);
			sb.append(write);
//			 _Log.i(sb.toString());
			FileUtil.write(destDir, sb.toString());
			Process p;

			p = SystemTool.runCommand(destDir);

			InputStream is = p.getInputStream();
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(is));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			// }
			p.waitFor();
			is.close();
			// reader.close();
			p.destroy();
			getPreferences();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void execute() throws IOException, InterruptedException {
		delPreferenceFile();
		String destDir = Constants.XSL_DESTINATION_PATH;
		String srcDir = Constants.XSL_SOURCE_PATH;
		String str = FileUtil.readToString(srcDir);
		str = str.substring(str.indexOf("\r"));
		StringBuilder sb = new StringBuilder();
		sb.append("#target photoshop");
		sb.append("\r");
		sb.append(str);
		sb.append("\r");
		// alert(sb);
		// _Log.i(sb.toString());
		FileUtil.write(destDir, sb.toString());
		Process p = SystemTool.runCommand(destDir);
		InputStream is = p.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		p.waitFor();
		is.close();
		reader.close();
		p.destroy();
		getPreferences();
	}

	private void callback() {

	}

}