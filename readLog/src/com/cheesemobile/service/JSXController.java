package com.cheesemobile.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.cheesemobile.domain.VoBean;
import com.cheesemobile.parser.LayersInfoParser;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util._Log;

public class JSXController {

	private static int timeOut = 50000;
	private static int timePassed = 0;
	private static int timedelay = 1;
	private static String _lastPreferenceStr = "";
	private static int _randomValue = 0;
	private static JSXController jsxController = null;
	private StringBuilder stringBuilder;
	private String destDir = Constants.XSL_DESTINATION_PATH;
	String srcDir = Constants.XSL_SOURCE_PATH;

	public enum JSXFunction {
		HELLO_WORLD, BOUNDS, DUMPLICATE, DUMPLICATEEXTENDPSD, MOVELAYERINTO, CHANGETEXT, CHANGENAME, SETBOUNDS, NAMESINLAYER, ADDIMAGE;
		public String toString() {
			String str = "";
			switch (this) {
			case HELLO_WORLD:
				str = "helloWorld";
				break;
			case BOUNDS:
				str = "bounds";
				break;
			case DUMPLICATE:
				str = "dumplicate";
				break;
			case DUMPLICATEEXTENDPSD:
				str = "dumplicateExtendPsd";
				break;
			case MOVELAYERINTO:
				str = "moveLayerInto";
				break;
			case CHANGETEXT:
				str = "changeText";
				break;
			case CHANGENAME:
				str = "changeName";
				break;
			case SETBOUNDS:
				str = "setBounds";
				break;
			case NAMESINLAYER:
				str = "namesInLayer";
				break;
			case ADDIMAGE:
				str = "addImage";
				break;
			default:
				break;
			}
			return str;
		}

		public static JSXFunction[] functionsNeedsReturn() {
			JSXFunction strs[] = { JSXFunction.BOUNDS, JSXFunction.NAMESINLAYER };
			return strs;
		}

		public static boolean functionNeedsReturn(String funName) {
			JSXFunction[] functionsNeedsReturn = functionsNeedsReturn();
			for (JSXFunction functionName : functionsNeedsReturn) {
				if (functionName.toString().equals(funName)) {
					return true;
				}
			}
			return false;
		}
	}

	private JSXController() {
	}

	public static JSXController getInstance() {
		if (jsxController == null) {
			jsxController = new JSXController();
		}
		return jsxController;
	}

	private void alert(StringBuilder sb) {
		sb.append("alert(\"123321\")");
		sb.append("\r");
	}

	private String JSXArray(List<String> list) {
		if(list.size() == 0){
			return null;
		}
		StringBuilder stringOfArray = new StringBuilder();
		stringOfArray.append("[");
		for (String str : list) {
			stringOfArray.append("\"\"\"" + str + "\"\"\",");
		}
		stringOfArray.deleteCharAt(stringOfArray.length() - 1);
		stringOfArray.append("]");
		return stringOfArray.toString();
	}

//	public List<String> invoke(String functionName, double... params) {
//		String[] paramss = new String[params.length];
//		for (int i = 0; i < paramss.length; i++) {
//			paramss[i] = "" + params[i];
//		}
//		return invoke(functionName, paramss);
//	}
//
//	public List<String> invoke(String functionName, String layerName,
//			double... params) {
//		String[] paramss = new String[params.length + 1];
//		paramss[0] = layerName;
//		for (int i = 1; i < paramss.length; i++) {
//			paramss[i] = "" + params[i - 1];
//		}
//		return invoke(functionName, paramss);
//	}

	public void invoke(String functionName, String... params) {
		if (JSXFunction.functionNeedsReturn(functionName)) {
//			LayersInfoParser.getInstance();
			_Log.i("call deleted invoke functions");
		} else {
			execute(functionName, JSXArray(Arrays.asList(params)));
		}
	}
	
//	public List<String> invoke(String functionName, String... params) {
//		if (JSXFunction.functionNeedsReturn(functionName)) {
//			execute(functionName, JSXArray(Arrays.asList(params)));
//			flush();
//			VoBean invoke = null;
//			if (_lastPreferenceStr.indexOf(functionName) != -1) {
//				invoke = new VoBean(_lastPreferenceStr);
//			}
//			if (invoke.getValuesList() == null) {
//				return null;
//			}
//			_Log.i("jsxController " + functionName + " invoked:"
//					+ Arrays.asList(invoke.getValuesList()));
//			return Arrays.asList(invoke.getValuesList());
//		} else {
//			execute(functionName, JSXArray(Arrays.asList(params)));
//		}
//		return null;
//	}

//	private VoBean invoke(String funName, List params) {
//		VoBean returnVal = null;
//		execute(funName, JSXArray(params));
//		
//		return returnVal;
//	}

	private void getPreferences() throws InterruptedException {
		final CountDownLatch runningThreadNum = new CountDownLatch(1);
		Thread thread = new Thread() {
			public void run() {
				timePassed = 0;
				try {
					while (!preferenceFileExists()) {
						Thread.sleep(timedelay);
						timePassed += timedelay;
						System.out.print(".");
						if (timePassed > timeOut) {
							break;
						}
					}
					if (preferenceFileExists()) {
						String str = FileUtil.readToString(
								Constants.PREFERENCE_TEMP_PATH, "utf-8");
						_lastPreferenceStr = str.substring(str.indexOf(""
								+ _randomValue));

//						delPreferenceFile();
					}
					runningThreadNum.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.run();
		runningThreadNum.await();
		// Thread.sleep(1000);
	}

//	private void delPreferenceFile() {
//		_randomValue = (int) (Math.random() * 1000000);
//		// File file = new File(Constants.PREFERENCE_TEMP_PATH);
//		// if (file.exists()) {
//		// file.delete();
//		// }
//	}

	private boolean preferenceFileExists() {
		 File file = new File(Constants.PREFERENCE_TEMP_PATH);
		 if(!file.exists()){
			 return false;
		 }
		String str = FileUtil.readToString(Constants.PREFERENCE_TEMP_PATH);
		if (str.indexOf(_randomValue + "") != -1) {
			return true;
		}
		return false;
	}

	// private static void execute(String functionName,String params) {
	// try {
	// delPreferenceFile();
	// String destDir = Constants.XSL_DESTINATION_PATH;
	// String srcDir = Constants.XSL_SOURCE_PATH;
	// String str = FileUtil.readToString(srcDir,true);
	// str = str.substring(str.indexOf("\r"));
	// StringBuilder sb = new StringBuilder();
	// sb.append("#target photoshop");
	// sb.append("\r");
	// sb.append(str);
	// sb.append("\r");
	// sb.append("try{  _returntojavavalues = " + functionName +"(" + params +
	// ")" + " }catch(e){_returntojavavalues = "+ "\"" +
	// Constants.XSL_DEBUG_ERROR_RETURN+ "\"" +" + e} \r");
	// sb.append("\r");
	// _Log.i("" + _randomValue);
	// String write =
	// "var prefs = new File(\""+Constants.PREFERENCE_TEMP_PATH+"\");\r prefs.open(\"w\"); \r prefs.encoding = \"utf-8\" \r prefs.writeln(\""
	// + _randomValue +
	// " \\n function:"+functionName+"\\n names:undefinied\\n values:\"+_returntojavavalues)  \r  prefs.close(); \r ";
	// // alert(sb);
	// sb.append(write);
	// // _Log.i(sb.toString());
	// FileUtil.write(destDir, sb.toString());
	// Process p;
	//
	// p = SystemTool.runCommand(destDir);
	//
	// InputStream is = p.getInputStream();
	// // BufferedReader reader = new BufferedReader(new
	// // InputStreamReader(is));
	// // String line;
	// // while ((line = reader.readLine()) != null) {
	// // System.out.println(line);
	// // }
	// p.waitFor();
	// is.close();
	// // reader.close();
	// p.destroy();
	// getPreferences();
	// } catch (IOException | InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	private void initStringBuilder() {
//		delPreferenceFile();
		stringBuilder = new StringBuilder();
		stringBuilder.append("#target photoshop");
		stringBuilder.append("\r");
		stringBuilder.append("#include \"" + srcDir + "\"");
		stringBuilder.append("\r");

	}

	private void execute(String functionName, String params) {
		if (stringBuilder == null || stringBuilder.length() < 1) {
			initStringBuilder();
		}
		_randomValue = (int) (Math.random() * 1000000);
//		stringBuilder.append("try{  _returntojavavalues = " + functionName
//				+ "(" + params + ")" + " }catch(e){_returntojavavalues = "
//				+ "\"" + Constants.XSL_DEBUG_ERROR_RETURN + "\"" + " + e} \r");
		stringBuilder.append(" _returntojavavalues = " + functionName
				+ "(" + params + ")" + "\r");
		stringBuilder.append("\r");
//		_Log.i("" + _randomValue);
//		String write = "var prefs = new File(\""
//				+ Constants.PREFERENCE_TEMP_PATH
//				+ "\");\r prefs.open(\"a\"); \r prefs.encoding = \"utf-8\" \r prefs.writeln(\""
//				+ _randomValue
//				+ " \\n function:"
//				+ functionName
//				+ "\\n names:undefinied\\n values:\"+_returntojavavalues)  \r  prefs.close(); \r ";
//		stringBuilder.append(write);

	}

	public void flush() {
		if(stringBuilder == null){
			return;
		}
		try {
			FileUtil.write(destDir, stringBuilder.toString());
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
//			getPreferences();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		stringBuilder = null;
	}

	private void execute() throws IOException, InterruptedException {
		delPreferenceFile();
		String destDir = Constants.XSL_DESTINATION_PATH;
		String srcDir = Constants.XSL_SOURCE_PATH;
		String str = FileUtil.readToString(srcDir, true);
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