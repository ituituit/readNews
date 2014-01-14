package com.cheesemobile.app;

import com.cheesemobile.service.LogController;
import com.cheesemobile.service.TestInfos;
import com.cheesemobile.service.news.NewsController;
import com.cheesemobile.util.StringUtil;

public class ReadLog {
	private static int numberParametersLast = 3;

	public static void main(String[] args) {
		NewsController nc = new NewsController();
		// if(args.length == 0){
		// System.out.println("tips: -p \"configPath\" //change daily password");
		// System.out.println("tips: -d \"url\" \"fullPathSaveTo\" //download a file");
		// System.out.println("tips: -c \"paths\" \"inipath\" //compare images");
		// System.out.println("tips: -cc TableView C:\\tmp.bmp OpCenter.ini");
		// return;
		// }
		// if (args[0].equalsIgnoreCase("-p")) {
		// DailyPasswordController.getInstance().write(args[1]);
		// }else if (args[0].equalsIgnoreCase("-d")){
		// String fileName = Math.random() * 10000 + "";
		// String saveToPath = args[2].substring(0,args[2].lastIndexOf("\\"));
		// boolean dc = DownloadController.getInstance().download(args[1],
		// saveToPath +"\\"+ fileName);
		// if(dc){
		// DownloadController.getInstance().write(args[2]);
		// }
		// System.out.println("download result:" + dc);
		// }else if (args[0].equalsIgnoreCase("-c")){
		// String[] imgPaths = args[1].split(",");
		// String iniPath = args[2];
		// try {
		// BitmapCompareController.getInstance().compareImages(imgPaths);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// BitmapCompareController.getInstance().write(iniPath);
		// }else if (args[0].equalsIgnoreCase("-cc")){
		// try {
		// Method method =
		// CustomCtrl.getInstance().getClass().getMethod(args[1],String[].class);
		// method.invoke(CustomCtrl.getInstance(), (Object)args[2].split(","));
		// CustomCtrl.getInstance().write(args[3]);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }else{
		// readlog(args);
		// }
	}

	public static void readlog(String[] args) {
		int indexOfPath = args.length - numberParametersLast;
		int indexOfCaseNames = args.length - 2;
		int indexOfErrorStrings = args.length - 1;
		LogController controller = new LogController();
		if (args.length != 0) {
			for (int i = 0; i < args.length - numberParametersLast; i++) {
				String[] str = args[i].split(",");
				TestInfos.startDate.add(StringUtil.StringToDate(str[0] + ","
						+ str[1] + "," + str[2], StringUtil.FROM_AUTO_IT));
				TestInfos.endDate.add(StringUtil.StringToDate(str[3] + ","
						+ str[4] + "," + str[5], StringUtil.FROM_AUTO_IT));
			}
			TestInfos.logPath = args[indexOfPath];
			TestInfos.caseNames = args[indexOfCaseNames].split(",");
			int caseNums = TestInfos.caseNames.length;
			String paramErrors[] = args[indexOfErrorStrings].split(",");
			if (paramErrors.length < caseNums) {
				String[] tmpStrs = new String[caseNums];
				for (int i = 0; i < tmpStrs.length; i++) {
					tmpStrs[i] = "";
					if (i < paramErrors.length) {
						tmpStrs[i] = paramErrors[i];
					}
				}
				paramErrors = tmpStrs;
			}
			System.out.println("len:" + paramErrors.length);
			TestInfos.auitErrors = paramErrors;
		} else {
			TestInfos.startDate.add(StringUtil.StringToDate(
					"21:17:10,1/24/2013,-480", StringUtil.FROM_AUTO_IT));
			TestInfos.endDate.add(StringUtil.StringToDate(
					"21:17:13,1/26/2013,-480", StringUtil.FROM_AUTO_IT));

			TestInfos.startDate.add(StringUtil.StringToDate(
					"20:56:29,1/21/2013,480", StringUtil.FROM_AUTO_IT));
			TestInfos.endDate.add(StringUtil.StringToDate(
					"20:56:29,1/23/2013,480", StringUtil.FROM_AUTO_IT));
		}
		controller.run();
	}
}