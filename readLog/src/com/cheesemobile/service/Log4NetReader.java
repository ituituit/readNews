package com.cheesemobile.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cheesemobile.domain.EventBean;
import com.cheesemobile.util.Constants;
import com.cheesemobile.util.FileUtil;
import com.cheesemobile.util.Log4NetEventUtils;
import com.cheesemobile.util._Log;

public class Log4NetReader {

	private String str;
	private LogFilters logFilters;

	public Log4NetReader() {
	}

	public Log4NetReader readFile(String path) {
		str = FileUtil.readToString(path);
		return this;
	}

	public List<String> readEventSource(int level) {
		Document doc = Jsoup.parse(str);
		List<String> leb = new ArrayList<String>();
		Elements e = doc.getElementsByTag("log4net:event");
		for (int i = 0; i < e.size(); i++) {
			if ("ERROR".equals(e.get(i).attr("level"))) {
				leb.add(e.get(i).toString());
			}
		}
		return leb;
	}

	public List<EventBean> readEvent(int ind, String autoitError) {
		logFilters = LogFilters.getInstance(ind);

		Document doc = Jsoup.parse(str);
		List<EventBean> leb = new ArrayList<EventBean>();
		Elements e = doc.getElementsByTag("log4net:event");
		for (int i = 0; i < e.size(); i++) {
			Element data = e.get(i);
			EventBean eb = null;
			try {
				if(filterAttrs(data)){
					leb.add(Log4NetEventUtils.encapsulateElementToEventBean(data));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (autoitError != "") {
			_Log.i("autoitError:" + autoitError);
			leb.add(Log4NetEventUtils.newErrorEventBean(autoitError));
		}
		if (logFilters.logMatched() != null) {
			_Log.i("logMatched Failed");
			leb.add(Log4NetEventUtils.newErrorEventBean(logFilters.logMatched()));
		}
		return leb;
	}

	private boolean filterAttrs(Element data) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		logFilters.setData(data);
		for (String filterMethod : Constants.filterMethods) {
			Method method = logFilters.getClass().getMethod(filterMethod);
			boolean passed = (Boolean) method.invoke(logFilters);
			if(!passed){
				return false;
			}
		}
		return true;
	}
}
