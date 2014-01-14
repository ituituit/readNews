package com.cheesemobile.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cheesemobile.domain.EventBean;
import com.cheesemobile.domain.TableDetailData;
import com.cheesemobile.domain.TableInfoBean;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.StringUtil;

public class LogController {
	// C:\Users\peiwen\AppData\Local\Temp\LogViewer\10.30.4.105
	// public static String TestInfos.logPath = "C:\\Users\\peiwen\\Desktop\\readLog";
	private static List<List<EventBean>> listErrorEvents = new ArrayList<List<EventBean>>();
	public LogController() {
	}

	public void run() {
		// FileUtil.replacePlusSign();
		// String userDir = System.getProperty("user.dir");
		// String path = userDir + File.separator + "xsl.xsl";
		// FileUtil.copyFile(path, TestInfos.logPath + File.separator + "xsl.xsl");
		InputStream in = this.getClass().getResourceAsStream("/xsl.xsl");
		if (null != in) {
			try {
				in.available();
				System.out.println("copying xsl.xsl to temp folder...");
				FileUtil.copyFile(in, TestInfos.logPath + File.separator + "xsl.xsl");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.getAllLog();
		TableInfoBean tlb = this.toTableInfoBean();
		Log4NetWriter logwriter = new Log4NetWriter(TestInfos.logPath);
		logwriter.writeCaseXml(tlb);
	}

	public void getAllLog() {
		FileUtil.unGzipAll(TestInfos.logPath);
		FileUtil.deleteFile(new File(TestInfos.logPath + "\\errors.xml"));
		for (int i = 0; i < TestInfos.startDate.size(); i++) {
			this.getCaseLog(i);
		}
	}

	private boolean getCaseLog(int ind) {
		int testCase  = ind+1;
		List<String> paths = FileUtil.listFolderFiles(TestInfos.logPath, ".xml");
		List<EventBean> errorEvents = new ArrayList<EventBean>();
		StringBuilder errorPaths = new StringBuilder();
		for (int i = 0; i < paths.size(); i++) {
			String path = paths.get(i);
			// System.out.println("readFile" + path);
			Log4NetReader l = new Log4NetReader().readFile(path);
			String autoItError = "";
			if (TestInfos.auitErrors.length > 0 && null != TestInfos.auitErrors[testCase - 1]
					&& TestInfos.auitErrors[testCase - 1].length() > 0) {
				System.out.println("autoit Error:" + TestInfos.auitErrors[testCase - 1]);
				autoItError = TestInfos.auitErrors[testCase - 1];
			}
			List<EventBean> findedEvents = l.readEvent(ind,autoItError);
			for (int j = 0; j < findedEvents.size(); j++) {
				errorEvents.add(findedEvents.get(j));
			}

			if (errorEvents.size() != 0) {
				errorPaths.append(paths.get(i));
			}
		}

		if (null != errorEvents) {
			listErrorEvents.add(errorEvents);
		} else {
			listErrorEvents.add(null);
		}
		return true;
	}



	public TableInfoBean toTableInfoBean() {
		List<TableDetailData> details = new ArrayList<TableDetailData>();
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}

			TableDetailData data = new TableDetailData(i + 1,
					errorEvents.size(), errorEvents, "", TestInfos.caseNames[i]);
			details.add(data);
		}
		TableInfoBean tib = new TableInfoBean(getTotalCase(), getPassedCase(),
				getWarningCase()[0], getWarningCase()[1], getWarningCase()[2],
				getWarningCase()[3], getErrorNumber(), getWarningNumber(),
				getFatalNumber(), getDebugInfNumber(), getAuditNumber(),getAbortedNumber(),
				details);
		return tib;
	}

	private int getPassedCase() {
		int numPassed = 0;
		// for (int i = 0; i < listErrorEvents.size(); i++) {
		// List<EventBean> errorEvents = listErrorEvents.get(i);
		// if (null == errorEvents || errorEvents.size() == 0) {
		// numPassed++;
		// continue;
		// }
		// }

		return getTotalCase() - getWarningCase()[0];
	}

	private int getTotalCase() {
		return listErrorEvents.size();
	}

	private int getFailedCase() {
		return TestInfos.startDate.size() - getPassedCase();
	}

	private int[] getWarningCase() {
		List<int[]> ints = new ArrayList<int[]>();

		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);
				while (ints.size() <= i) {
					ints.add(new int[4]);
				}
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_ERROR])) {
					ints.get(i)[0] += 1;
				}
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_WARN])) {
					ints.get(i)[1] += 1;
				}
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_DEBUG])) {
					ints.get(i)[2] += 1;
				}
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_AUDIT])) {
					ints.get(i)[3] += 1;
				}
			}
			continue;
		}

		int[] errorWarnDebug = new int[4];
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < ints.size(); i++) {
				int[] caseErrorNum = ints.get(i);
				if (caseErrorNum[j] > 0) {
					errorWarnDebug[j] += 1;
				}
			}
		}

		System.out.println(errorWarnDebug[0] + " " + errorWarnDebug[1] + " "
				+ errorWarnDebug[2] + errorWarnDebug[3]);
		return errorWarnDebug;
	}

	private int getDebugInfoCase() {
		return TestInfos.startDate.size() - getPassedCase();
	}

	private int getErrorNumber() {
		int errorNum = 0;
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_ERROR])) {
					errorNum++;
				}
			}
		}
		return errorNum;
	}

	private int getWarningNumber() {
		int warningNum = 0;
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);

				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_WARN])) {
					warningNum++;
				}
			}
		}
		System.out.println("warningNum" + warningNum);

		return warningNum;
	}

	private int getFatalNumber() {
		int errorNum = 0;
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_FATAL])) {
					errorNum++;
				}
			}
		}
		return errorNum;
	}

	private int getDebugInfNumber() {
		int errorNum = 0;
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_DEBUG])) {
					errorNum++;
				}
			}
		}
		return errorNum;
	}

	private int getAuditNumber() {
		int errorNum = 0;
		for (int i = 0; i < listErrorEvents.size(); i++) {
			List<EventBean> errorEvents = listErrorEvents.get(i);
			if (null == errorEvents || errorEvents.size() == 0) {
				continue;
			}
			for (int j = 0; j < errorEvents.size(); j++) {
				EventBean errorEvent = errorEvents.get(j);
				if (errorEvent.getLevel().equals(
						Constants.LEVELS_DESC[Constants.LEVEL_AUDIT])) {
					errorNum++;
				}
			}
		}
		return errorNum;
	}
	
	private int getAbortedNumber(){
		int num = 0;
		for(int i = 0; i < TestInfos.auitErrors.length; i++){
			String errorStr = TestInfos.auitErrors[i];
			int ind = 0;
			while((ind = errorStr.indexOf(Constants.spreadSignOfAbortCase))!=-1){
				errorStr = errorStr.substring(ind + 1);
				num++;
			}
		}
		return num;
	}

}
