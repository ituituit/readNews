package com.cheesemobile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import com.cheesemobile.util.FileUtil;

public class JSXController {

	public static void execute() throws IOException, InterruptedException {
		
		String destDir = Constants.XSL_DESTINATION_PATH ;
		String srcDir = Constants.XSL_SOURCE_PATH ;
		String str = FileUtil.readToString(srcDir);
		str = str.substring(str.indexOf("\r"));
		StringBuilder sb = new StringBuilder();
		sb.append("#target photoshop");
		sb.append("\r");
//		sb.append(str);
		sb.append("\r");
		sb.append("alert(\"123321\")");
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

		final CountDownLatch runningThreadNum = new CountDownLatch(1);
		
		Thread thread = new Thread() {
			public void run() {
				try {
					runningThreadNum.countDown();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.run();
		runningThreadNum.await();
	}

	private void callback() {

	}

}